package com.yassir.budgetbuddy.state.controller;

import com.yassir.budgetbuddy.contact.controller.ContactRequest;
import com.yassir.budgetbuddy.state.service.ContactStateService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("contactState")
@RequiredArgsConstructor
@Tag(name = "ContactState")
public class ContactStateController {

    private final ContactStateService service;

    @PostMapping("add-contact-state")
    public ResponseEntity<Integer> createOrUpdateContactState(
            @RequestBody @Valid ContactStateRequest request,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.createOrUpdateContactState(request, connectedUser));
    }
}
