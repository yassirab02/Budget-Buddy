package com.yassir.budgetbuddy.expenses.service;


import com.yassir.budgetbuddy.budget.Budget;
import com.yassir.budgetbuddy.budget.repository.BudgetSpecification;
import com.yassir.budgetbuddy.common.PageResponse;
import com.yassir.budgetbuddy.expenses.Expenses;
import com.yassir.budgetbuddy.expenses.repository.ExpensesRepository;
import com.yassir.budgetbuddy.expenses.controller.ExpensesMapper;
import com.yassir.budgetbuddy.expenses.controller.ExpensesRequest;
import com.yassir.budgetbuddy.expenses.controller.ExpensesResponse;
import com.yassir.budgetbuddy.expenses.repository.ExpensesSpecification;
import com.yassir.budgetbuddy.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpensesServiceImpl implements ExpensesService{

    private final ExpensesRepository repository;
    private final ExpensesMapper expensesMapper;

    @Override
    public Integer addOrUpdateExpense(ExpensesRequest request) {
        Expenses expenses = expensesMapper.toExpenses(request);
        return repository.save(expenses).getId();
    }


    @Override
    public PageResponse<ExpensesResponse> findAllExpenses(int page, int size, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();

        // Create a pageable object with sorting by createdDate in descending order
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());

        // Fetch expenses using the specification to filter by user ID
        Page<Expenses> expenses = repository.findAll(ExpensesSpecification.withUserId(user.getId()), pageable);

        List<ExpensesResponse> expensesResponse = expenses.stream()
                .map(expensesMapper::toExpensesResponse)
                .toList();
        return new PageResponse<>(
                expensesResponse,
                expenses.getNumber(),
                expenses.getSize(),
                expenses.getTotalElements(),
                expenses.getTotalPages(),
                expenses.isFirst(),
                expenses.isLast()
        );
    }


    @Override
    public ExpensesResponse findExpenseById(Integer expenseId) {
        Expenses expense = repository.findById(expenseId)
                .orElseThrow(() -> new EntityNotFoundException("No Expense found with the Id : " +expenseId));
        return expensesMapper.toExpensesResponse(expense);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public void deleteExpense(Integer expenseId) {
        boolean condition = (expenseId != null);
        if (condition) {
            repository.deleteById(expenseId);
        }
    }
}
