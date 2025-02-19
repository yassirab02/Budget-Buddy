package com.yassir.budgetbuddy.contact.service;

import com.yassir.budgetbuddy.common.PageResponse;
import com.yassir.budgetbuddy.contact.Contact;
import com.yassir.budgetbuddy.contact.controller.ContactResponse;
import com.yassir.budgetbuddy.contact.repository.ContactRepository;
import com.yassir.budgetbuddy.contact.controller.ContactMapper;
import com.yassir.budgetbuddy.contact.controller.ContactRequest;
import com.yassir.budgetbuddy.contact.repository.ContactSpecification;
import com.yassir.budgetbuddy.role.Role;
import com.yassir.budgetbuddy.state.service.ContactStateService;
import com.yassir.budgetbuddy.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService{

    private final ContactRepository repository;
    private final ContactMapper contactMapper;
    private final ContactStateService contactStateService;

    @Override
    public void deleteContact(Integer contactId) {
        repository.deleteById(contactId);
    }

    @Override
    public void deleteAllContacts() {
        repository.deleteAll();
    }

    @Override
    public int createContact(ContactRequest request, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Contact contact = contactMapper.toContact(request);
        contact.setState(contactStateService.findByCode("New"));
        contact.setUser(user);
        return repository.save(contact).getId();
    }

    @Override
    public PageResponse<ContactResponse> findAllContactsMessages(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        if (user.getRoles().stream().map(Role::getName).noneMatch("ADMIN"::equals)) {
            throw new IllegalArgumentException("You are not allowed to view this page");
        }
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdDate").descending());
        Page<Contact> contacts = repository.findAll(ContactSpecification.withUserId(user.getId()), pageable);
        List<ContactResponse> contactResponse = contacts.stream()
                .map(contactMapper::toContactResponse)
                .toList();
        return new PageResponse<>(
                contactResponse,
                contacts.getNumber(),
                contacts.getSize(),
                contacts.getTotalElements(),
                contacts.getTotalPages(),
                contacts.isFirst(),
                contacts.isLast()
        );
    }

    @Override
    public int deleteContactMessage(Integer contactId, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        if (user.getRoles().stream().map(Role::getName).noneMatch("ADMIN"::equals)) {
            throw new IllegalArgumentException("You are not allowed to view this page");
        }
        if (repository.findById(contactId).isEmpty()) {
            throw new IllegalArgumentException("No contact found with the Id : " + contactId);
        }
        repository.deleteById(contactId);
        return contactId;
    }

    @Override
    public ContactResponse updateContactMessage(ContactRequest request, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        if (user.getRoles().stream().map(Role::getName).noneMatch("ADMIN"::equals)) {
            throw new IllegalArgumentException("You are not allowed to view this page");
        }
        Contact contact = repository.findById(request.id())
                .orElseThrow(() -> new IllegalArgumentException("No contact found with the Id : " + request.id()));
        return contactMapper.toContactResponse(contact);
    }

}
