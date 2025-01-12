package com.yassir.budgetbuddy.category.service.impl;

import com.yassir.budgetbuddy.category.Repository.IncomeSourceRepository;
import com.yassir.budgetbuddy.category.bean.IncomeSource;
import com.yassir.budgetbuddy.category.controller.mapper.IncomeSourceMapper;
import com.yassir.budgetbuddy.category.controller.response.IncomeSourceResponse;
import com.yassir.budgetbuddy.category.service.facade.IncomeSourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IncomeSourceServiceImpl implements IncomeSourceService {

    private final IncomeSourceRepository incomeSourceRepository;
    private final IncomeSourceMapper incomeSourceMapper; // Inject the mapper

    @Override
    public List<IncomeSourceResponse> findAll() {
        List<IncomeSource> incomeSources = incomeSourceRepository.findAll();
        if (incomeSources.isEmpty()) {
            throw new RuntimeException("No income source found");
        }

        // Map the list of IncomeSource entities to a list of IncomeSourceResponse DTOs
        return incomeSources.stream()
                .map(incomeSourceMapper::toIncomeSourceResponse)
                .collect(Collectors.toList());
    }
}
