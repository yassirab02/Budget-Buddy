package com.yassir.budgetbuddy.user.service;

import com.yassir.budgetbuddy.user.User;
import com.yassir.budgetbuddy.user.UserRepository;
import com.yassir.budgetbuddy.user.controller.ChangePasswordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final int MAX_ATTEMPTS = 3;
    private static final long LOCKOUT_DURATION = 120;

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal(); // we used UsernamePasswordAuthenticationToken because it is the token that is used in the authentication process
                                                                                                // and to get the principal from it then we cast it to the user

        // check if the current password is correct
        passwordEncoder.matches(request.getCurrentPassword(), user.getPassword()); // we pass the current password and the encoded password to the matches method

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        // check if the 2 passwords are the same
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Passwords do not match");
        }
        // setting the new password to the user
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        // saving the user
        userRepository.save(user);
    }

    @Override
    public BigDecimal getTotalBalance(User connectedUser) {
        return connectedUser.getTotalBalance();
    }

    @Override
    public void loginFailed(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            int attempts = user.getLoginAttempts() + 1;
            user.setLoginAttempts(attempts);
            if (attempts >= MAX_ATTEMPTS) {
                user.setLockoutTime(LocalDateTime.now());
            }
            userRepository.save(user);
        }
    }

    @Override
    public void loginSucceeded(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.setLoginAttempts(0);
            user.setLockoutTime(null);
            userRepository.save(user);
        }
    }

    @Override
    public boolean isLocked(String email) {
        User user = userRepository.findByEmail(email);
        return user != null && user.getLockoutTime() != null &&
                user.getLockoutTime().plusSeconds(LOCKOUT_DURATION).isAfter(LocalDateTime.now());
    }


    @Override
    public Long getRemainingLockoutTime(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getLockoutTime() != null) {
            LocalDateTime lockoutEndTime = user.getLockoutTime().plusSeconds(LOCKOUT_DURATION);
            LocalDateTime now = LocalDateTime.now();
            if (lockoutEndTime.isAfter(now)) {
                return java.time.Duration.between(now, lockoutEndTime).getSeconds();
            }
        }
        return 0L;
    }


}
