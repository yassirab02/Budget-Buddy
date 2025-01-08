package com.yassir.budgetbuddy.user.service;

import com.yassir.budgetbuddy.user.User;
import com.yassir.budgetbuddy.user.UserRepository;
import com.yassir.budgetbuddy.user.controller.ChangePasswordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

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
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
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
        Optional<User> userOpt = userRepository.findByEmail(email); // This returns an Optional<User>
        if (userOpt.isPresent()) {
            User user = userOpt.get();  // Get the User object from Optional
            user.setLoginAttempts(0);
            user.setLockoutTime(null);
            userRepository.save(user);  // Save the User object
        }
    }

    @Override
    public boolean isLocked(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return user.getLockoutTime() != null &&
                    user.getLockoutTime().plusSeconds(LOCKOUT_DURATION).isAfter(LocalDateTime.now());
        }
        return false;
    }


    @Override
    public Long getRemainingLockoutTime(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getLockoutTime() != null) {
                LocalDateTime lockoutEndTime = user.getLockoutTime().plusSeconds(LOCKOUT_DURATION);
                LocalDateTime now = LocalDateTime.now();
                if (lockoutEndTime.isAfter(now)) {
                    return java.time.Duration.between(now, lockoutEndTime).getSeconds();
                }
            }
        }
        return 0L;  // Return 0 if user is not found or no lockout time
    }


    @Override
    public String getCurrentUsername() {
        // Get the username from the security context
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        return principal.toString();
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    @Override
    public void addBalance(BigDecimal amount, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        user.setTotalBalance(user.getTotalBalance().add(amount));
        userRepository.save(user);
    }
}
