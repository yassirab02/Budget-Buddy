package com.yassir.budgetbuddy.state.service;


import com.yassir.budgetbuddy.role.Role;
import com.yassir.budgetbuddy.state.ContactState;
import com.yassir.budgetbuddy.state.controller.ContactStateMapper;
import com.yassir.budgetbuddy.state.controller.ContactStateRequest;
import com.yassir.budgetbuddy.state.repository.ContactStateRepository;
import com.yassir.budgetbuddy.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactStateServiceImpl implements ContactStateService{

    private final ContactStateRepository repository;
    private final ContactStateMapper contactStateMapper;

    @Override
    public Integer createOrUpdateContactState(ContactStateRequest request, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        if (user.getRoles().stream().map(Role::getName).noneMatch("ADMIN"::equals)) {
            throw new IllegalArgumentException("You are not allowed to view this page");
        }
        ContactState contactState = contactStateMapper.toContactState(request);
        return repository.save(contactState).getId();
    }

    @Override
    public ContactState findByCode(String code) {
        if (code == null) {
            return null;
        }
        return repository.findByCode(code);
    }
}
