package com.yassir.budgetbuddy.user.service;

import com.yassir.budgetbuddy.user.User;
import com.yassir.budgetbuddy.user.controller.ChangePasswordRequest;
import com.yassir.budgetbuddy.user.controller.UserResponse;
import com.yassir.budgetbuddy.user.controller.UserTransferResponse;
import org.springframework.security.core.Authentication;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface UserService {

    void changePassword(ChangePasswordRequest request, Principal connectedUser);

    BigDecimal getTotalBalance(User connectedUser);

    void loginFailed(String email);

    void loginSucceeded(String email);

    boolean isLocked(String email);

    Long getRemainingLockoutTime(String email);


    String getCurrentUsername();

    User findByEmail(String email);

    void addBalance(BigDecimal amount, Authentication connectedUser);

    List<UserTransferResponse> getUsersTransfer(Authentication connectedUser);
}
