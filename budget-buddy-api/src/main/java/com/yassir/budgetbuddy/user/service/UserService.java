package com.yassir.budgetbuddy.user.service;

import com.yassir.budgetbuddy.user.controller.ChangePasswordRequest;

import java.security.Principal;

public interface UserService {

    void changePassword(ChangePasswordRequest request, Principal connectedUser);

}
