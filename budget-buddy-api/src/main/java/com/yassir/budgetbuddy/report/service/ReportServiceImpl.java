package com.yassir.budgetbuddy.report.service;

import com.yassir.budgetbuddy.common.PageResponse;
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
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Month;
import java.util.Comparator;
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

    @Override
    public ReportResponse getMonthlyReport(Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Integer userId = user.getId();
        Report report = repository.findByUserIdAndTypeAndCurrentMonth(userId, ReportType.MONTHLY);
        return reportMapper.toReportResponse(report);
    }


    @Override
    public ReportResponse getYearlyReports(Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Integer userId = user.getId();
        Report report = repository.findByUserIdAndTypeAndCurrentYear(userId, ReportType.YEARLY);
        return reportMapper.toReportResponse(report);
    }

    // Scheduled method to generate monthly reports (runs on the 1st day of each month at midnight)
    @Scheduled(cron = "0 0 0 1 * ?")
    @Override
    public void generateMonthlyReport() {

        LocalDate now = LocalDate.now();
        List<User> users = userRepository.findAll(); // Get all users

        for (User user : users) {

            Optional<Report> existingReport = repository.findByUserAndYearAndMonth(user, now.getYear(), now.getMonthValue());
            if (existingReport.isPresent()) {
                // Handle report update or skip, if needed
                System.out.println("Monthly report already generated! for the user " + user.fullName());
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
            monthlyReport.setTotalIncome(calculateTotalIncome(user, now)); // Custom method to calculate income
            monthlyReport.setTotalExpenses(calculateTotalExpenses(user, now)); // Custom method to calculate expenses
            monthlyReport.setTotalGoalsReached(calculateTotalGoals(user, now)); // Custom method to calculate goals reached
            monthlyReport.setBalance(monthlyReport.getTotalIncome().subtract(monthlyReport.getTotalExpenses()));
            monthlyReport.setDetails(generateReportDetails(user, now)); // Custom method to generate report details
            monthlyReport.setCreatedBy(user.getId()); // Assuming the User entity h

            try {
                repository.save(monthlyReport);
            } catch (DataIntegrityViolationException e) {
                // Log the error or handle it (e.g., skip user or update existing report)
                System.err.println("Error saving report for user " + user.getId() + ": " + e.getMessage());
            }
        }

        System.out.println("Monthly reports generated successfully!");
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


    private int calculateTotalGoals(User user, LocalDate now) {
        LocalDate startOfMonth = now.withDayOfMonth(1);
        LocalDate endOfMonth = now.withDayOfMonth(now.lengthOfMonth());

        return goalRepository
                .findByUserAndReachedDateBetween(user, startOfMonth, endOfMonth)
                .size(); // Count the number of goals reached
    }


    private String generateReportDetails(User user, LocalDate now) {
        BigDecimal totalIncome = calculateTotalIncome(user, now);
        BigDecimal totalExpenses = calculateTotalExpenses(user, now);
        int totalGoals = calculateTotalGoals(user, now);
        BigDecimal balance = totalIncome.subtract(totalExpenses);

        if (totalIncome.compareTo(BigDecimal.ZERO) == 0) {
            return ReportMessage.NO_INCOME_MESSAGE.getMessage();
        } else if (totalExpenses.compareTo(BigDecimal.ZERO) == 0) {
            return ReportMessage.NO_EXPENSES_MESSAGE.getMessage();
        } else if (balance.compareTo(BigDecimal.ZERO) < 0) {
            return ReportMessage.DEBT_ALERT_MESSAGE.getMessage();
        } else if (totalGoals == 0) {
            return ReportMessage.SAVINGS_GOAL_MESSAGE.getMessage();
        } else if (balance.compareTo(BigDecimal.ZERO) > 0) {
            return String.format(
                    "%s%s\n%s%s\n%s%s",
                    ReportMessage.MONTHLY_INCOME_MESSAGE.getMessage(), totalIncome,
                    ReportMessage.MONTHLY_EXPENSES_MESSAGE.getMessage(), totalExpenses,
                    ReportMessage.MONTHLY_BALANCE_MESSAGE.getMessage(), balance
            );
        } else {
            return ReportMessage.REPORT_GENERATED_MESSAGE.getMessage();
        }
    }


    // Scheduled method to generate yearly reports (runs on the 1st of January at midnight)
    @Scheduled(cron = "0 0 0 1 1 *")
    @Override
    public void generateYearlyReports() {
        LocalDate now = LocalDate.now();
        List<User> users = userRepository.findAll(); // Get all users

        for (User user : users) {
            Optional<Report> existingReport = repository.findByUserAndYearAndMonth(user, now.getYear(), 0);
            if (existingReport.isPresent()) {
                System.out.println("Yearly report already generated! for the user " + user.fullName());
                continue;
            }

            // Create a new report for each user
            BigDecimal totalIncome = calculateYearlyTotalIncome(user, now); // Custom method to calculate income
            BigDecimal totalExpenses = calculateYearlyTotalExpenses(user, now); // Custom method to calculate expenses

            Report yearlyReport = new Report();
            yearlyReport.setType(ReportType.YEARLY);
            yearlyReport.setYear(now.getYear()); // Set the year to the current year
            yearlyReport.setMonth(0); // 0 represents the entire year (no specific month)
            yearlyReport.setStartDate(now.withMonth(Month.JANUARY.getValue()).withDayOfMonth(1)); // Start date: January 1st
            yearlyReport.setEndDate(now.withMonth(Month.DECEMBER.getValue()).withDayOfMonth(31)); // End date: December 31st
            yearlyReport.setUser(user);
            yearlyReport.setTotalIncome(totalIncome);
            yearlyReport.setTotalExpenses(totalExpenses);
            yearlyReport.setTotalGoalsReached(calculateYearlyTotalGoals(user, now)); // Custom method to calculate goals reached
            yearlyReport.setBalance(totalIncome.subtract(totalExpenses));
            yearlyReport.setDetails(generateYearlyReportDetails(user, now)); // Custom method to generate report details

            // Calculate most spending month and saving rate
            yearlyReport.setMostSpendingMonth(calculateMostSpendingMonth(user, now)); // Most spending month
            yearlyReport.setSavingRate(calculateSavingRate(totalIncome, totalExpenses)); // Saving rate

            try {
                repository.save(yearlyReport);
            } catch (DataIntegrityViolationException e) {
                System.err.println("Error saving report for user " + user.getId() + ": " + e.getMessage());
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
    private int calculateYearlyTotalGoals(User user, LocalDate now) {
        LocalDate startOfYear = now.withMonth(Month.JANUARY.getValue()).withDayOfMonth(1);
        LocalDate endOfYear = now.withMonth(Month.DECEMBER.getValue()).withDayOfMonth(31);

        // Logic to calculate total goals reached for the entire year
        return goalRepository.findByUserAndReachedDateBetween(user, startOfYear, endOfYear).size();
    }

    // Custom method to generate yearly report details
    private String generateYearlyReportDetails(User user, LocalDate now) {
        BigDecimal totalIncome = calculateYearlyTotalIncome(user, now);
        BigDecimal totalExpenses = calculateYearlyTotalExpenses(user, now);
        int totalGoals = calculateYearlyTotalGoals(user, now);
        BigDecimal balance = totalIncome.subtract(totalExpenses);

        if (totalIncome.compareTo(BigDecimal.ZERO) == 0) {
            return ReportMessage.NO_INCOME_MESSAGE.getMessage();
        } else if (totalExpenses.compareTo(BigDecimal.ZERO) == 0) {
            return ReportMessage.NO_EXPENSES_MESSAGE.getMessage();
        } else if (balance.compareTo(BigDecimal.ZERO) < 0) {
            return ReportMessage.DEBT_ALERT_MESSAGE.getMessage();
        } else if (totalGoals == 0) {
            return ReportMessage.SAVINGS_GOAL_MESSAGE.getMessage();
        } else if (balance.compareTo(BigDecimal.ZERO) > 0) {
            return String.format(
                    "%s%s\n%s%s\n%s%s",
                    ReportMessage.YEARLY_INCOME_MESSAGE.getMessage(), totalIncome,
                    ReportMessage.YEARLY_EXPENSES_MESSAGE.getMessage(), totalExpenses,
                    ReportMessage.YEARLY_BALANCE_MESSAGE.getMessage(), balance
            );
        } else {
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
                .max(Comparator.comparing(Map.Entry::getValue)) // Compare by spending amount
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


}
