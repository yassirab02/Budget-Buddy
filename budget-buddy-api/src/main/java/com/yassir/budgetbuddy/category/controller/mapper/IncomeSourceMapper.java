package com.yassir.budgetbuddy.category.controller.mapper;

import com.yassir.budgetbuddy.category.bean.IncomeSource;
import com.yassir.budgetbuddy.category.controller.request.IncomeSourceRequest;
import com.yassir.budgetbuddy.category.controller.response.IncomeSourceResponse;
import com.yassir.budgetbuddy.file.FileUtils;
import org.springframework.stereotype.Service;

@Service
public class IncomeSourceMapper {

    // Map the IncomeSourceRequest to IncomeSource entity
    public IncomeSource toIncomeSource(IncomeSourceRequest request) {
        return IncomeSource.builder()
                .id(request.id()) // Optional field, can be null for new income sources
                .name(request.name())
                .description(request.description())
                .build();
    }

    // Map IncomeSource entity to IncomeSourceResponse DTO
    public IncomeSourceResponse toIncomeSourceResponse(IncomeSource incomeSource) {
        return IncomeSourceResponse.builder()
                .id(incomeSource.getId())
                .name(incomeSource.getName())
                .description(incomeSource.getDescription())
                .iconUrl(FileUtils.readFileFromLocation(incomeSource.getIcon_url()))
                .build();
    }
}
