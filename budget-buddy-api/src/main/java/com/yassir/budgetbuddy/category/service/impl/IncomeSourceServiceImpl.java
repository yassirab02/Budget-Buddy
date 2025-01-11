package com.yassir.budgetbuddy.category.service.impl;

import com.yassir.budgetbuddy.category.Repository.IncomeSourceRepository;
import com.yassir.budgetbuddy.category.bean.IncomeSource;
import com.yassir.budgetbuddy.category.service.facade.IncomeSourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IncomeSourceServiceImpl implements IncomeSourceService {

    private final IncomeSourceRepository incomeSourceRepository;

    @Override
    public List<IncomeSource> findAll() {
        List<IncomeSource> incomeSources = incomeSourceRepository.findAll();
        if (incomeSources.isEmpty()) {
            throw new RuntimeException("No income source found");
        }
        return incomeSources;
    }
}
