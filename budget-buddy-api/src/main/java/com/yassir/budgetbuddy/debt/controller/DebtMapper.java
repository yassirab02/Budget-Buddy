package com.yassir.budgetbuddy.debt.controller;

import com.yassir.budgetbuddy.debt.Debt;
import com.yassir.budgetbuddy.file.FileUtils;
import com.yassir.budgetbuddy.user.User;
import org.springframework.stereotype.Service;

@Service
public class DebtMapper {

    public Debt toDebt(DebtRequest request) {
        return Debt.builder()
                .id(request.id())
                .name(request.name())
                .description(request.description())
                .amount(request.amount())
                .dueDate(request.dueDate())
                .issueDate(request.issueDate())
                .type(request.type())
                .isPaid(request.isPaid())
                .build();
    }

    public DebtResponse toDebtResponse(Debt debt) {
        return DebtResponse.builder()
                .id(debt.getId())
                .name(debt.getName())
                .description(debt.getDescription())
                .amount(debt.getAmount())
                .dueDate(debt.getDueDate())
                .type(debt.getType().getType())
                .owner(debt.getOwner().fullName())
                .build();
    }

}
