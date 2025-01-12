package com.yassir.budgetbuddy.category.service.facade;

import com.yassir.budgetbuddy.category.bean.IncomeSource;
import com.yassir.budgetbuddy.category.controller.response.IncomeSourceResponse;

import java.util.List;

public interface IncomeSourceService {
    List<IncomeSourceResponse> findAll();
}
