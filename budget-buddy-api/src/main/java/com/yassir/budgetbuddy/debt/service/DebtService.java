package com.yassir.budgetbuddy.debt.service;

import com.yassir.budgetbuddy.common.PageResponse;
import com.yassir.budgetbuddy.debt.controller.DebtRequest;
import com.yassir.budgetbuddy.debt.controller.DebtResponse;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;

public interface DebtService {

    Integer addOrUpdateDebt(@Valid DebtRequest request, Authentication connectedUser);

    void deleteDebt(Integer debtId, Authentication connectedUser);

    DebtResponse findDebtById(Integer debtId);

    PageResponse<DebtResponse> findAllDebtsByOwner(int page, int size, Authentication connectedUser);

    PageResponse<DebtResponse> findDebtsByOwnerAndPaidStatus(int page, int size, Authentication connectedUser, boolean paidStatus);

}
