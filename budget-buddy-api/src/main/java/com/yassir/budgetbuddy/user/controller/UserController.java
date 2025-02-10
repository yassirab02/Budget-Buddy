package com.yassir.budgetbuddy.user.controller;

import com.yassir.budgetbuddy.user.User;
import com.yassir.budgetbuddy.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
// did not add this controller to the authentication controller because we want this controller to be secured not public
// when the end point is secured, when can get the user without passing any id in the request, and we get it from the principal
public class UserController {

    private final UserService service;
    private final UserMapper userMapper;

    @PatchMapping
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedUser
    ) {
        service.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/current-user")
    public UserResponse getCurrentUser() {
        // Retrieve the current authenticated user from the security context
        String username = service.getCurrentUsername();
        User connectedUser = service.findByEmail(username);
        return userMapper.toUserResponse(connectedUser);
    }

    @PostMapping("/add-balance/{amount}")
    public ResponseEntity<?> addBalance(
            @PathVariable("amount") BigDecimal amount,
            Authentication connectedUser
    ) {
        service.addBalance(amount, connectedUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users-transfer")
    public ResponseEntity<List<UserTransferResponse>> getUsersTransfer(Authentication connectedUser) {
        return ResponseEntity.ok(service.getUsersTransfer(connectedUser));
    }
}
