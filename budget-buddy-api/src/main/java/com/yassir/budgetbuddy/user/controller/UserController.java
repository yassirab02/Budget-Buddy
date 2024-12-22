package com.yassir.budgetbuddy.user.controller;

import com.yassir.budgetbuddy.user.service.UserService;
import com.yassir.budgetbuddy.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
// did not add this controller to the authentication controller because we want this controller to be secured not public
// when the end point is secured, when can get the user without passing any id in the request, and we get it from the principal
public class UserController {

    private final UserService service;

    @PatchMapping
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedUser
    ) {
        service.changePassword(request,connectedUser);
        return ResponseEntity.ok().build();
    }

}