package com.yassir.budgetbuddy.contact.controller;

import com.yassir.budgetbuddy.budget.controller.BudgetRequest;
import com.yassir.budgetbuddy.contact.service.ContactService;
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
@RequestMapping("contact")
@RequiredArgsConstructor
@Tag(name = "Contact")
public class ContactController {

    private final ContactService service;

    @PostMapping("add-contact")
    public ResponseEntity<Integer> createContact(
            @RequestBody @Valid ContactRequest request,
            Authentication connectedUser) {
        return ResponseEntity.ok(service.createContact(request, connectedUser));
    }
}