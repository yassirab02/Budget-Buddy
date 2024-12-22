package com.yassir.budgetbuddy.income.service;

import com.yassir.budgetbuddy.common.PageResponse;
import com.yassir.budgetbuddy.income.Income;
import com.yassir.budgetbuddy.income.repository.IncomeRepository;
import com.yassir.budgetbuddy.income.controller.IncomeMapper;
import com.yassir.budgetbuddy.income.controller.IncomeRequest;
import com.yassir.budgetbuddy.income.controller.IncomeResponse;
import com.yassir.budgetbuddy.income.repository.IncomeSpecification;
import com.yassir.budgetbuddy.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class IncomeServiceImpl implements IncomeService {

    private final IncomeRepository repository;
    private final IncomeMapper incomeMapper;

    @Override
    public Integer addOrUpdateIncome(IncomeRequest request) {
        Income income = incomeMapper.toIncome(request);
        return repository.save(income).getId();
    }

    @Override
    public void deleteIncome(Integer incomeId) {
        boolean condition = (incomeId != null);
        if (condition) {
            repository.deleteById(incomeId);
        }
    }

    @Override
    public PageResponse<IncomeResponse> findAllIncomes(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Income> incomes = repository.findAll(IncomeSpecification.withUserId(user.getId()), pageable);
        List<IncomeResponse> incomeResponse = incomes.stream()
                .map(incomeMapper::toIncomeResponse)
                .toList();
        return new PageResponse<>(
                incomeResponse,
                incomes.getNumber(),
                incomes.getSize(),
                incomes.getTotalElements(),
                incomes.getTotalPages(),
                incomes.isFirst(),
                incomes.isLast()
        );
    }

}
