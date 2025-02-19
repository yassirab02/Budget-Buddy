package com.yassir.budgetbuddy.contact.controller;

import com.yassir.budgetbuddy.contact.Contact;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ContactMapper {

    public Contact toContact (ContactRequest request){
        return Contact.builder()
                .id(request.id())
                .subject(request.subject())
                .message(request.message())
                .build();
    }

    public ContactResponse toContactResponse(Contact contact) {
        return ContactResponse.builder()
                .id(contact.getId())
                .subject(contact.getSubject())
                .message(contact.getMessage())
                .email(contact.getEmail())
                .sender(contact.getSender())
                .state(contact.getState().getName())
                .build();
    }
}
