package com.yassir.budgetbuddy.report.service;

import com.yassir.budgetbuddy.common.PageResponse;
import com.yassir.budgetbuddy.debt.Debt;
import com.yassir.budgetbuddy.debt.repository.DebtRepository;
import com.yassir.budgetbuddy.email.EmailService;
import com.yassir.budgetbuddy.email.EmailTemplateName;
import com.yassir.budgetbuddy.expenses.Expenses;
import com.yassir.budgetbuddy.expenses.repository.ExpensesRepository;
import com.yassir.budgetbuddy.goal.repository.GoalRepository;
import com.yassir.budgetbuddy.income.Income;
import com.yassir.budgetbuddy.income.repository.IncomeRepository;
import com.yassir.budgetbuddy.report.Report;
import com.yassir.budgetbuddy.report.ReportMessage;
import com.yassir.budgetbuddy.report.repository.ReportRepository;
import com.yassir.budgetbuddy.report.ReportType;
import com.yassir.budgetbuddy.report.controller.ReportMapper;
import com.yassir.budgetbuddy.report.controller.ReportResponse;
import com.yassir.budgetbuddy.report.repository.ReportSpecification;
import com.yassir.budgetbuddy.user.User;
import com.yassir.budgetbuddy.user.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository repository;
    private final UserRepository userRepository;
    private final IncomeRepository incomeRepository;
    private final ExpensesRepository expenseRepository;
    private final GoalRepository goalRepository;
    private final ReportMapper reportMapper;
    private final DebtRepository debtRepository;
    private final EmailService emailService;


    @Value("${application.security.mailing.frontend.redirect-url}")
    private String url;


    @Override
    public PageResponse<ReportResponse> findAllReportsByUser(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Report> reports = repository.findAll(ReportSpecification.withUserId(user.getId()), pageable);
        List<ReportResponse> reportResponse = reports.stream()
                .map(reportMapper::toReportResponse)
                .toList();
        return new PageResponse<>(
                reportResponse,
                reports.getNumber(),
                reports.getSize(),
                reports.getTotalElements(),
                reports.getTotalPages(),
                reports.isFirst(),
                reports.isLast()
        );
    }


    // Scheduled method to generate monthly reports (runs on the 1st day of each month at midnight)
    @Scheduled(cron = "0 0 0 1 * ?") // Run on the 1st day of each month at midnight
   // @Scheduled(cron = "0 * * * * ?") // For testing purposes, run every minute
    @Transactional
    @Override
    public void generateMonthlyReport() {

        LocalDate now = LocalDate.now();
        List<User> users = userRepository.findAll(); // Get all users

        for (User user : users) {
            Optional<Report> existingReport = repository.findByUserAndYearAndMonth(user, now.getYear(), now.getMonthValue());
            if (existingReport.isPresent()) {
                System.out.println("Monthly report already generated for user: " + user.fullName());
                continue;
            }

            // Generate a monthly report for each user
            Report monthlyReport = new Report();
            monthlyReport.setType(ReportType.MONTHLY);
            monthlyReport.setYear(now.getYear());
            monthlyReport.setMonth(now.getMonthValue());
            monthlyReport.setStartDate(now.withDayOfMonth(1));
            monthlyReport.setEndDate(now.withDayOfMonth(now.lengthOfMonth()));
            monthlyReport.setUser(user);

            // Custom methods for calculations
            BigDecimal totalIncome = calculateTotalIncome(user, now); // Calculate total income
            BigDecimal totalExpenses = calculateTotalExpenses(user, now); // Calculate total expenses

            // Custom methods for calculations
            monthlyReport.setTotalIncome(totalIncome);
            monthlyReport.setTotalExpenses(totalExpenses);
            monthlyReport.setTotalGoalsReached(calculateTotalReachedGoals(user, now)); // Calculate goals reached
            monthlyReport.setTotalGoals(calculateTotalGoals(user,now)); // Total goals set by the user

            // Calculate debts
            BigDecimal totalDebt = calculateTotalDebt(user, now); // Custom method to calculate total debt
            BigDecimal totalPaidDebt = calculateTotalPaidDebt(user, now); // Custom method to calculate paid debt
            BigDecimal totalUnpaidDebt = totalDebt.subtract(totalPaidDebt); // Unpaid debt

            monthlyReport.setTotalDebt(totalDebt); // Add total debt
            monthlyReport.setTotalPaidDebt(totalPaidDebt); // Add paid debt
            monthlyReport.setTotalUnpaidDebt(totalUnpaidDebt); // Add unpaid debt

            // Calculate the saving rate for the month
            BigDecimal savingRate = calculateMonthlySavingRate(totalIncome, totalExpenses);
            monthlyReport.setSavingRate(savingRate); // Set saving rate

            monthlyReport.setBalance(monthlyReport.getTotalIncome().subtract(monthlyReport.getTotalExpenses()));
            monthlyReport.setDetails(generateReportDetails(user, now)); // Generate report details
            monthlyReport.setCreatedBy(user.getId());

            try {
                repository.save(monthlyReport);
            } catch (DataIntegrityViolationException e) {
                System.err.println("Error saving report for user " + user.getId() + ": " + e.getMessage());
            }

            // Send email with the generated report
            try {
                emailService.sendEmailWithMonthlyReport(
                        List.of(user.getEmail()),
                        List.of(user.fullName()),
                        EmailTemplateName.MONTHLY_REPORT,
                        url,
                        "Monthly Report",
                        monthlyReport.getTotalExpenses(),
                        monthlyReport.getTotalIncome(),
                        now.getMonth().name(),
                        now.getYear()
                );
            } catch (MessagingException e) {
                System.err.println("Error sending email to user " + user.getId() + ": " + e.getMessage());
            }
        }
        System.out.println("Monthly reports generated successfully!");
    }

    private BigDecimal calculateMonthlySavingRate(BigDecimal totalIncome, BigDecimal totalExpenses) {
        if (totalIncome.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO; // Avoid division by zero
        }
        return totalIncome.subtract(totalExpenses)
                .divide(totalIncome, 2, RoundingMode.HALF_UP); // Round to 2 decimal places
    }

    private BigDecimal calculateTotalDebt(User user, LocalDate now) {
        return debtRepository.findByOwnerAndIssueDateBefore(user, now.plusDays(1))
                .stream()
                .map(Debt::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateTotalPaidDebt(User user, LocalDate now) {
        return debtRepository.findByOwnerAndIsPaidTrueAndIssueDateBefore(user, now.plusDays(1))
                .stream()
                .map(Debt::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    private BigDecimal calculateTotalIncome(User user, LocalDate now) {
        LocalDate startOfMonth = now.withDayOfMonth(1);
        LocalDate endOfMonth = now.withDayOfMonth(now.lengthOfMonth());
        return incomeRepository
                .findByUserAndDateBetween(user, startOfMonth, endOfMonth)
                .stream()
                .map(Income::getAmount) // Extract the income amount
                .reduce(BigDecimal.ZERO, BigDecimal::add); // Sum up all incomes
    }

    private BigDecimal calculateTotalExpenses(User user, LocalDate now) {
        LocalDate startOfMonth = now.withDayOfMonth(1);
        LocalDate endOfMonth = now.withDayOfMonth(now.lengthOfMonth());
        return expenseRepository
                .findByUserAndDateBetween(user, startOfMonth, endOfMonth)
                .stream()
                .map(Expenses::getAmount) // Extract the expense amount
                .reduce(BigDecimal.ZERO, BigDecimal::add); // Sum up all expenses
    }


    private int calculateTotalReachedGoals(User user, LocalDate now) {
        LocalDate startOfMonth = now.withDayOfMonth(1);
        LocalDate endOfMonth = now.withDayOfMonth(now.lengthOfMonth());

        return goalRepository
                .findByUserAndReachedDateBetween(user, startOfMonth, endOfMonth)
                .size(); // Count the number of goals reached
    }

    private int calculateTotalGoals(User user, LocalDate now) {
        LocalDate startOfMonth = now.withDayOfMonth(1);
        LocalDate endOfMonth = now.withDayOfMonth(now.lengthOfMonth());

        return goalRepository
                .findByUserAndMonthDateBetween(user, startOfMonth, endOfMonth)
                .size(); // Count the number of goals reached
    }

    private String generateReportDetails(User user, LocalDate now) {
        BigDecimal totalIncome = calculateTotalIncome(user, now);
        BigDecimal totalExpenses = calculateTotalExpenses(user, now);
        BigDecimal totalDebt = calculateTotalDebt(user, now); // New method for total debt
        BigDecimal totalPaidDebt = calculateTotalPaidDebt(user, now); // New method for paid debt
        BigDecimal totalUnpaidDebt = totalDebt.subtract(totalPaidDebt); // Calculate remaining unpaid debt
        int totalGoals = calculateTotalReachedGoals(user, now);
        BigDecimal balance = totalIncome.subtract(totalExpenses);

        // No income case
        if (totalIncome.compareTo(BigDecimal.ZERO) == 0) {
            return ReportMessage.NO_INCOME_MESSAGE.getMessage();
        }

        // No expenses case
        else if (totalExpenses.compareTo(BigDecimal.ZERO) == 0) {
            return ReportMessage.NO_EXPENSES_MESSAGE.getMessage();
        }

        // Negative balance case
        else if (balance.compareTo(BigDecimal.ZERO) < 0) {
            return String.format(
                    "%s\n%s%s",
                    ReportMessage.DEBT_ALERT_MESSAGE.getMessage(),
                    ReportMessage.MONTHLY_TOTAL_UNPAID_DEBT_MESSAGE.getMessage(), totalUnpaidDebt
            );
        }

        // No goals case
        else if (totalGoals == 0) {
            return ReportMessage.SAVINGS_GOAL_MESSAGE.getMessage();
        }

        // Positive balance with additional debt details
        else if (balance.compareTo(BigDecimal.ZERO) > 0) {
            return String.format(
                    "%s%s\n%s%s\n%s%s\n%s%s\n%s%s\n%s%s",
                    ReportMessage.MONTHLY_INCOME_MESSAGE.getMessage(), totalIncome,
                    ReportMessage.MONTHLY_EXPENSES_MESSAGE.getMessage(), totalExpenses,
                    ReportMessage.MONTHLY_BALANCE_MESSAGE.getMessage(), balance,
                    ReportMessage.MONTHLY_TOTAL_DEBT_MESSAGE.getMessage(), totalDebt,
                    ReportMessage.MONTHLY_TOTAL_PAID_DEBT_MESSAGE.getMessage(), totalPaidDebt,
                    ReportMessage.MONTHLY_TOTAL_UNPAID_DEBT_MESSAGE.getMessage(), totalUnpaidDebt
            );
        }
        // Default case (report generated)
        else {
            return ReportMessage.REPORT_GENERATED_MESSAGE.getMessage();
        }

    }


    // Scheduled method to generate yearly reports (runs on the 1st of January at midnight)
    @Scheduled(cron = "0 0 0 1 1 *")
   //@Scheduled(cron = "0 * * * * ?") // For testing purposes, run every minute
    @Transactional
    @Override
    public void generateYearlyReports() {
        LocalDate now = LocalDate.now();
        List<User> users = userRepository.findAll(); // Get all users

        for (User user : users) {
            Optional<Report> existingReport = repository.findByUserAndYearAndMonth(user, now.getYear(), 0);
            if (existingReport.isPresent()) {
                System.out.println("Yearly report already generated for user " + user.fullName());
                // If the report already exists, update it
                Report existing = existingReport.get();
                existing.setTotalIncome(calculateYearlyTotalIncome(user, now));
                existing.setTotalExpenses(calculateYearlyTotalExpenses(user, now));
                existing.setTotalDebt(calculateYearlyTotalDebt(user, now));
                existing.setTotalPaidDebt(calculateYearlyTotalPaidDebt(user, now));
                existing.setTotalUnpaidDebt(existing.getTotalDebt().subtract(existing.getTotalPaidDebt()));
                existing.setBalance(existing.getTotalIncome().subtract(existing.getTotalExpenses()));
                existing.setDetails(generateYearlyReportDetails(user, now));
                existing.setMostSpendingMonth(calculateMostSpendingMonth(user, now));
                existing.setSavingRate(calculateSavingRate(existing.getTotalIncome(), existing.getTotalExpenses()));
                existing.setTotalGoals(calculateYearlyGoals(user,now)); // Total goals set by the user
                existing.setTotalGoalsReached(calculateYearlyReachedGoals(user, now));

                // Save the updated report
                repository.save(existing);
            } else {
                // Create a new report for each user
                Report yearlyReport = new Report();
                yearlyReport.setType(ReportType.YEARLY);
                yearlyReport.setYear(now.getYear());
                yearlyReport.setMonth(0);
                yearlyReport.setStartDate(now.withMonth(Month.JANUARY.getValue()).withDayOfMonth(1));
                yearlyReport.setEndDate(now.withMonth(Month.DECEMBER.getValue()).withDayOfMonth(31));
                yearlyReport.setUser(user);

                // Custom methods for calculations
                BigDecimal totalIncome = calculateYearlyTotalIncome(user, now); // Calculate total income
                BigDecimal totalExpenses = calculateYearlyTotalExpenses(user, now); // Calculate total expenses

                yearlyReport.setTotalIncome(totalIncome);
                yearlyReport.setTotalExpenses(totalExpenses);
                yearlyReport.setTotalDebt(calculateYearlyTotalDebt(user, now));
                yearlyReport.setTotalPaidDebt(calculateYearlyTotalPaidDebt(user, now));
                yearlyReport.setTotalUnpaidDebt(yearlyReport.getTotalDebt().subtract(yearlyReport.getTotalPaidDebt()));
                yearlyReport.setBalance(yearlyReport.getTotalIncome().subtract(yearlyReport.getTotalExpenses()));
                yearlyReport.setTotalGoals(calculateYearlyGoals(user,now)); // Total goals set by the user
                yearlyReport.setTotalGoalsReached(calculateYearlyReachedGoals(user, now));

                // Calculate the saving rate for the month
                BigDecimal savingRate = calculateMonthlySavingRate(totalIncome, totalExpenses);
                yearlyReport.setSavingRate(savingRate); // Set saving rate

                yearlyReport.setDetails(generateYearlyReportDetails(user, now));
                yearlyReport.setCreatedBy(user.getId());

                yearlyReport.setMostSpendingMonth(calculateMostSpendingMonth(user, now));
                yearlyReport.setSavingRate(calculateSavingRate(yearlyReport.getTotalIncome(), yearlyReport.getTotalExpenses()));

                // Save the newly created report
                repository.save(yearlyReport);

                // Send email with the generated report
                try {
                    emailService.sendEmailWithYearlyReport(
                            List.of(user.getEmail()),
                            List.of(user.fullName()),
                            EmailTemplateName.YEARLY_REPORT,
                            url,
                            "Yearly Report",
                            yearlyReport.getTotalExpenses(),
                            yearlyReport.getTotalIncome(),
                            yearlyReport.getSavingRate(),
                            now.getYear()
                    );
                } catch (MessagingException e) {
                    System.err.println("Error sending email to user " + user.getId() + ": " + e.getMessage());
                }
            }
        }
        System.out.println("Yearly reports generated successfully!");
    }


    // Custom method to calculate total income for the entire year
    private BigDecimal calculateYearlyTotalIncome(User user, LocalDate now) {
        LocalDate startOfYear = now.withMonth(Month.JANUARY.getValue()).withDayOfMonth(1);
        LocalDate endOfYear = now.withMonth(Month.DECEMBER.getValue()).withDayOfMonth(31);

        // Logic to calculate total income for the entire year
        return incomeRepository.findByUserAndDateBetween(user, startOfYear, endOfYear).stream()
                .map(Income::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Custom method to calculate total expenses for the entire year
    private BigDecimal calculateYearlyTotalExpenses(User user, LocalDate now) {
        LocalDate startOfYear = now.withMonth(Month.JANUARY.getValue()).withDayOfMonth(1);
        LocalDate endOfYear = now.withMonth(Month.DECEMBER.getValue()).withDayOfMonth(31);

        // Logic to calculate total expenses for the entire year
        return expenseRepository.findByUserAndDateBetween(user, startOfYear, endOfYear).stream()
                .map(Expenses::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Custom method to calculate total goals reached for the entire year
    private int calculateYearlyReachedGoals(User user, LocalDate now) {
        LocalDate startOfYear = now.withMonth(Month.JANUARY.getValue()).withDayOfMonth(1);
        LocalDate endOfYear = now.withMonth(Month.DECEMBER.getValue()).withDayOfMonth(31);

        // Logic to calculate total goals reached for the entire year
        return goalRepository.findByUserAndReachedDateBetween(user, startOfYear, endOfYear).size();
    }

  // Custom method to calculate total goals reached for the entire year
    private int calculateYearlyGoals(User user, LocalDate now) {
        LocalDate startOfYear = now.withMonth(Month.JANUARY.getValue()).withDayOfMonth(1);
        LocalDate endOfYear = now.withMonth(Month.DECEMBER.getValue()).withDayOfMonth(31);

        // Logic to calculate total goals reached for the entire year
        return goalRepository.findByUserAndYearDateBetween(user, startOfYear, endOfYear).size();
    }

    // Custom method to generate yearly report details
    private String generateYearlyReportDetails(User user, LocalDate now) {
        BigDecimal totalIncome = calculateYearlyTotalIncome(user, now);
        BigDecimal totalExpenses = calculateYearlyTotalExpenses(user, now);
        BigDecimal totalDebt = calculateYearlyTotalDebt(user, now); // New method for total debt
        BigDecimal totalPaidDebt = calculateYearlyTotalPaidDebt(user, now); // New method for paid debt
        BigDecimal totalUnpaidDebt = totalDebt.subtract(totalPaidDebt); // Calculate remaining unpaid debt
        int totalGoals = calculateYearlyReachedGoals(user, now);
        BigDecimal balance = totalIncome.subtract(totalExpenses);

        // No income case
        if (totalIncome.compareTo(BigDecimal.ZERO) == 0) {
            return ReportMessage.NO_INCOME_MESSAGE.getMessage();
        }

        // No expenses case
        else if (totalExpenses.compareTo(BigDecimal.ZERO) == 0) {
            return ReportMessage.NO_EXPENSES_MESSAGE.getMessage();
        }

        // Negative balance case
        else if (balance.compareTo(BigDecimal.ZERO) < 0) {
            return String.format(
                    "%s\n%s%s",
                    ReportMessage.DEBT_ALERT_MESSAGE.getMessage(),
                    ReportMessage.YEARLY_TOTAL_UNPAID_DEBT_MESSAGE.getMessage(), totalUnpaidDebt
            );
        }

        // No goals case
        else if (totalGoals == 0) {
            return ReportMessage.SAVINGS_GOAL_MESSAGE.getMessage();
        }

        // Positive balance with additional debt details
        else if (balance.compareTo(BigDecimal.ZERO) > 0) {
            return String.format(
                    "%s%s\n%s%s\n%s%s\n%s%s\n%s%s\n%s%s",
                    ReportMessage.YEARLY_INCOME_MESSAGE.getMessage(), totalIncome,
                    ReportMessage.YEARLY_EXPENSES_MESSAGE.getMessage(), totalExpenses,
                    ReportMessage.YEARLY_BALANCE_MESSAGE.getMessage(), balance,
                    ReportMessage.YEARLY_TOTAL_DEBT_MESSAGE.getMessage(), totalDebt,
                    ReportMessage.YEARLY_TOTAL_PAID_DEBT_MESSAGE.getMessage(), totalPaidDebt,
                    ReportMessage.YEARLY_TOTAL_UNPAID_DEBT_MESSAGE.getMessage(), totalUnpaidDebt
            );
        }

        // Default case (report generated)
        else {
            return ReportMessage.REPORT_GENERATED_MESSAGE.getMessage();
        }
    }

    private Integer calculateMostSpendingMonth(User user, LocalDate now) {
        LocalDate startOfYear = now.withMonth(1).withDayOfMonth(1);
        LocalDate endOfYear = now.withMonth(12).withDayOfMonth(31);

        // Fetch all expenses for the year
        List<Expenses> yearlyExpenses = expenseRepository.findByUserAndDateBetween(user, startOfYear, endOfYear);

        // Group expenses by month and calculate total spending for each month
        Map<Integer, BigDecimal> spendingByMonth = yearlyExpenses.stream()
                .collect(Collectors.groupingBy(
                        expense -> expense.getDate().getMonthValue(),
                        Collectors.reducing(BigDecimal.ZERO, Expenses::getAmount, BigDecimal::add)
                ));

        // Find the month with the highest spending
        return spendingByMonth.entrySet().stream()
                .max(Map.Entry.comparingByValue()) // Compare by spending amount
                .map(Map.Entry::getKey) // Return the month
                .orElse(null); // Return null if no expenses are found
    }


    private BigDecimal calculateSavingRate(BigDecimal totalIncome, BigDecimal totalExpenses) {
        if (totalIncome.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO; // Avoid division by zero
        }
        return totalIncome.subtract(totalExpenses)
                .divide(totalIncome, 2, RoundingMode.HALF_UP); // Round to 2 decimal places
    }

    private BigDecimal calculateYearlyTotalDebt(User user, LocalDate now) {
        List<Debt> yearlyDebts = debtRepository.findByOwnerAndYear(user, now.getYear());
        return yearlyDebts.stream()
                .map(Debt::getAmount) // Get the amount of each debt
                .reduce(BigDecimal.ZERO, BigDecimal::add); // Sum up all debts
    }

    private BigDecimal calculateYearlyTotalPaidDebt(User user, LocalDate now) {
        List<Debt> paidDebts = debtRepository.findByOwnerAndYearAndIsPaid(user, now.getYear(), true);
        return paidDebts.stream()
                .map(Debt::getAmount) // Get the amount of each paid debt
                .reduce(BigDecimal.ZERO, BigDecimal::add); // Sum up all paid debts
    }


    @Override
    public List<ReportResponse> getMonthlyReport(Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        List<Report> reports = repository.findByUserIdAndTypeAndCurrentMonth(user.getId(), ReportType.MONTHLY);
        if (reports == null) {
            return null;
        }
        return reportMapper.toReportResponseList(reports);
    }

    @Override
    public List<ReportResponse> getAllMonthlyReport(Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        List<Report> reports = repository.findByUserIdAndAllMonthlyReports(user.getId(), ReportType.MONTHLY);
        if (reports == null) {
            return null;
        }
        return reportMapper.toReportResponseList(reports);
    }

    @Override
    public List<ReportResponse> getYearlyReports(Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        List<Report> reports = repository.findByUserIdAndTypeAndCurrentYear(user.getId(), ReportType.YEARLY);
        if (reports == null) {
            return null;
        }
        return reportMapper.toReportResponseList(reports);
    }

    @Override
    public List<ReportResponse> getAllYearlyReports(Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        List<Report> reports = repository.findByUserIdAndAllYearlyReports(user.getId(), ReportType.YEARLY);
        if (reports == null) {
            return null;
        }
        return reportMapper.toReportResponseList(reports);
    }

    @Override
    public ReportResponse getReportById(Authentication connectedUser, Integer id) {
        User user = ((User) connectedUser.getPrincipal());
        Report report = repository.findById(id).orElse(null);
        if (report == null || !report.getUser().getId().equals(user.getId())) {
            return null;
        }
        return reportMapper.toReportResponse(report);
    }

    @Override
    public List<ReportResponse> getReportsByYear(Authentication connectedUser,Integer year) {
        User user = ((User) connectedUser.getPrincipal());
        List<Report> reports = repository.findByUserIdAndYear(user.getId(),year);
        if (reports == null) {
            return null;
        }
        return reportMapper.toReportResponseList(reports);
    }
}
