package com.yassir.budgetbuddy.state.service;

import com.yassir.budgetbuddy.state.ContactState;
import com.yassir.budgetbuddy.state.controller.ContactStateRequest;
import org.springframework.security.core.Authentication;

public interface ContactStateService {
    Integer createOrUpdateContactState(ContactStateRequest request, Authentication connectedUser);

    ContactState findByCode(String code);
}
