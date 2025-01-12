package com.yassir.budgetbuddy.saving.service;

import com.yassir.budgetbuddy.common.PageResponse;
import com.yassir.budgetbuddy.saving.controller.SavingResponse;
import org.springframework.security.core.Authentication;

public interface SavingService {

    SavingResponse calculateAndSaveSavings(Authentication connectedUser);

    PageResponse<SavingResponse> findAllSavingsByUser(int page, int size, Authentication connectedUser);

    SavingResponse findSavingByMonth(Integer month,Authentication connectedUser);
}
