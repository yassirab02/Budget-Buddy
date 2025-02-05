package com.yassir.budgetbuddy.budget.service;

import com.yassir.budgetbuddy.budget.Budget;
import com.yassir.budgetbuddy.budget.repository.BudgetRepository;
import com.yassir.budgetbuddy.budget.repository.BudgetSpecification;
import com.yassir.budgetbuddy.budget.controller.BudgetMapper;
import com.yassir.budgetbuddy.budget.controller.BudgetRequest;
import com.yassir.budgetbuddy.budget.controller.BudgetResponse;
import com.yassir.budgetbuddy.common.PageResponse;
import com.yassir.budgetbuddy.expenses.repository.ExpensesRepository;
import com.yassir.budgetbuddy.file.FileStorageService;
import com.yassir.budgetbuddy.income.repository.IncomeRepository;
import com.yassir.budgetbuddy.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository repository;
    private final BudgetMapper budgetMapper;
    private final FileStorageService fileStorageService;
    private final ExpensesRepository expensesRepository;
    private final IncomeRepository incomeRepository;


    @Override
    public Integer addOrUpdateBudget(BudgetRequest request, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Optional<Budget> existingBudget = repository.findByNameAndOwnerId(request.name(),user.getId());
        if (existingBudget.isPresent()) {
            throw new EntityNotFoundException("Budget with the name already exists");
        }
        Budget budget = budgetMapper.toBudget(request);
        budget.setOwner(user);
        return repository.save(budget).getId();
    }

    @Override
    public void deleteBudget(Integer budgetId, Authentication connectedUser) {
        Budget budget = repository.findById(budgetId)
                .orElseThrow(() -> new EntityNotFoundException("No Budget found with the Id : " + budgetId));
        User user = ((User) connectedUser.getPrincipal());
        if (!budget.getOwner().getId().equals(user.getId())) {
            throw new IllegalArgumentException("You are not allowed to delete this Budget");
        }
        repository.delete(budget);
    }

    @Override
    public PageResponse<BudgetResponse> findAllBudgetsByOwner(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdDate").descending());
        Page<Budget> budgets = repository.findAll(BudgetSpecification.withOwnerId(user.getId()), pageable);
        List<BudgetResponse> budgetResponse = budgets.stream()
                .map(budgetMapper::toBudgetResponse)
                .toList();
        return new PageResponse<>(
                budgetResponse,
                budgets.getNumber(),
                budgets.getSize(),
                budgets.getTotalElements(),
                budgets.getTotalPages(),
                budgets.isFirst(),
                budgets.isLast()
        );
    }

    @Override
    public BudgetResponse findBudgetById(Integer budgetId) {
        Budget budget = repository.findById(budgetId)
                .orElseThrow(() -> new EntityNotFoundException("No Budget found with the Id : " + budgetId));
        return budgetMapper.toBudgetResponse(budget);
    }


    @Override
    public BudgetResponse calculateMonthlyBudget(Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        LocalDate currentDate = LocalDate.now();
        int currentMonth = currentDate.getMonthValue(); // Current month as an integer
        int currentYear = currentDate.getYear(); // Current year

        // Fetch all budgets for the user in the current month
        List<Budget> budgets = repository.findBudgetsForCurrentMonth(user.getId(), currentMonth, currentYear);

        // Assuming the Budget entity has a createdDate or month and year field
        BigDecimal totalBudget = budgets.stream()
                .filter(budget -> budget.getCreatedDate().getMonthValue() == currentMonth && budget.getCreatedDate().getYear() == currentYear)
                .map(Budget::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add); // Sum up the budgets for the current month


        // Fetch all expenses for the user in the current month
        BigDecimal totalExpenses = expensesRepository.findTotalExpensesForCurrentMonth(user.getId(), currentMonth, currentYear);

        // Fetch all incomes for the user in the current month
        BigDecimal totalIncome = incomeRepository.findTotalIncomeForCurrentMonth(user.getId(), currentMonth, currentYear);

        // Calculate available balance
        BigDecimal availableBalance = totalIncome.subtract(totalExpenses);

        // Prepare the MonthlyBudgetResponse
        return budgetMapper.toMonthlyBudgetResponse(totalBudget, availableBalance);
    }

}
