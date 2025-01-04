package com.yassir.budgetbuddy.user.service;

import com.yassir.budgetbuddy.user.User;
import com.yassir.budgetbuddy.user.controller.ChangePasswordRequest;
import org.springframework.security.core.Authentication;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Optional;

public interface UserService {

    void changePassword(ChangePasswordRequest request, Principal connectedUser);

    BigDecimal getTotalBalance(User connectedUser);

    void loginFailed(String email);

    void loginSucceeded(String email);

    boolean isLocked(String email);

    Long getRemainingLockoutTime(String email);

    Optional<User> getConnectedUser(Principal connectedUser);
}
