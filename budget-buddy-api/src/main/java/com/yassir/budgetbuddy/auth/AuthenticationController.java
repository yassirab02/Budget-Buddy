package com.yassir.budgetbuddy.auth;
import com.yassir.budgetbuddy.user.User;
import com.yassir.budgetbuddy.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthenticationController {

    private  final AuthenticationService service;
    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> register(
            @RequestBody @Valid RegistrationRequest request
    ) throws MessagingException {
        service.register(request);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate (
            @RequestBody @Valid AuthenticationRequest request
    ){
        return ResponseEntity.ok(service.authenticate(request));
    }

    @GetMapping("/activate-account")
    public void confirm(@RequestParam String token)throws MessagingException {
        service.activateAccount(token);
    }


    @GetMapping("/connected-user")
    public ResponseEntity<Optional<UserResponse>> getConnectedUser(Authentication connectedUser) {
        Optional<User> user = userService.getConnectedUser(connectedUser);
        if (user.isPresent()){
            UserResponse userResponse = UserResponse.builder()
                    .id(user.get().getId())
                    .email(user.get().getEmail())
                    .firstName(user.get().getFirstName())
                    .lastName(user.get().getLastName())
                    .role(user.get().getRoles().get(0).getName())
                    .build();
            return ResponseEntity.ok(Optional.ofNullable(userResponse));
        }
        return ResponseEntity.ok(Optional.empty());
    }

}
