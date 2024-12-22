package com.yassir.budgetbuddy.user.service;

import com.yassir.budgetbuddy.user.User;
import com.yassir.budgetbuddy.user.UserRepository;
import com.yassir.budgetbuddy.user.controller.ChangePasswordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

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
}
