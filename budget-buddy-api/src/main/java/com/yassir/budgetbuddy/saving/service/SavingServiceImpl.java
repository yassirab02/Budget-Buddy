package com.yassir.budgetbuddy.saving.service;

import com.yassir.budgetbuddy.budget.Budget;
import com.yassir.budgetbuddy.budget.controller.BudgetResponse;
import com.yassir.budgetbuddy.budget.repository.BudgetSpecification;
import com.yassir.budgetbuddy.common.PageResponse;
import com.yassir.budgetbuddy.expenses.repository.ExpensesRepository;
import com.yassir.budgetbuddy.income.repository.IncomeRepository;
import com.yassir.budgetbuddy.saving.Saving;
import com.yassir.budgetbuddy.saving.controller.SavingMapper;
import com.yassir.budgetbuddy.saving.controller.SavingResponse;
import com.yassir.budgetbuddy.saving.repository.SavingRepository;
import com.yassir.budgetbuddy.saving.repository.SavingSpecification;
import com.yassir.budgetbuddy.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SavingServiceImpl implements SavingService{

    private final SavingRepository repository;
    private final IncomeRepository incomeRepository;
    private final ExpensesRepository expensesRepository;
    private final SavingMapper savingMapper;

    @Override
    public SavingResponse calculateAndSaveSavings(Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        LocalDate date = LocalDate.now();
        Integer month = date.getMonthValue();
        Integer year = date.getYear();
        // Query total income for the given user, month, and year
        Double totalIncome = incomeRepository.getTotalIncomeForUserAndMonth(user.getId(), month, year);

        // Query total expenses for the given user, month, and year
        Double totalExpenses = expensesRepository.getTotalExpensesForUserAndMonth(user.getId(), month, year);

        // Calculate savings
        Double savingsAmount = (totalIncome != null ? totalIncome : 0) - (totalExpenses != null ? totalExpenses : 0);

        // Save the calculated savings
        Saving saving = saveSaving(user, month, year, savingsAmount);

        if (saving == null) {
            return null;
        }
        return savingMapper.toSavingResponse(saving);
    }

    public Saving saveSaving(User user, Integer month, Integer year, Double amount) {
        // Check if a saving record already exists for the given user, month, and year
        Optional<Saving> existingSavingOptional = repository.findByMonthAndYearAndUser(month, year, user);
        Saving saving;
        if (existingSavingOptional.isPresent()) {
            // Update existing saving
            saving = existingSavingOptional.get();
            saving.setAmount(amount); // Update amount
        } else {
            // Create a new saving
            saving = new Saving();
            saving.setUser(user);
            saving.setMonth(month);
            saving.setYear(year);
            saving.setAmount(amount);
        }

        // Save to the database
        return repository.save(saving);
    }

    public PageResponse<SavingResponse> findAllSavingsByUser(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdDate").descending());
        Page<Saving> savings = repository.findAll(SavingSpecification.withUserId(user.getId()), pageable);
        List<SavingResponse> savingResponse = savings.stream()
                .map(savingMapper::toSavingResponse)
                .toList();
        return new PageResponse<>(
                savingResponse,
                savings.getNumber(),
                savings.getSize(),
                savings.getTotalElements(),
                savings.getTotalPages(),
                savings.isFirst(),
                savings.isLast()
        );
    }

    @Override
    public SavingResponse findSavingByMonth(Integer month,Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        return repository.findByMonthAndYearAndUser(month, LocalDate.now().getYear(), user)
                .map(savingMapper::toSavingResponse)
                .orElse(null);
    }

}
