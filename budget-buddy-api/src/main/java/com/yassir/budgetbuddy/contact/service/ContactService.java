package com.yassir.budgetbuddy.contact.service;


import com.yassir.budgetbuddy.common.PageResponse;
import com.yassir.budgetbuddy.contact.controller.ContactRequest;
import com.yassir.budgetbuddy.contact.controller.ContactResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface ContactService {
    void deleteContact(Integer contactId);

    void deleteAllContacts();

    int createContact(ContactRequest request, Authentication connectedUser);

    PageResponse<ContactResponse> findAllContactsMessages(int page, int size, Authentication connectedUser);

    int deleteContactMessage(Integer contactId, Authentication connectedUser);

    ContactResponse updateContactMessage(ContactRequest request, Authentication connectedUser);
}
