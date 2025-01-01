package com.yassir.budgetbuddy.user.service;

import com.yassir.budgetbuddy.user.User;
import com.yassir.budgetbuddy.user.controller.ChangePasswordRequest;
import java.math.BigDecimal;
import java.security.Principal;

public interface UserService {

    void changePassword(ChangePasswordRequest request, Principal connectedUser);

    BigDecimal getTotalBalance(User connectedUser);

    void loginFailed(String email);

    void loginSucceeded(String email);

    boolean isLocked(String email);

    Long getRemainingLockoutTime(String email);
}
