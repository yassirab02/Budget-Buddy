package com.yassir.budgetbuddy.state.controller;

import com.yassir.budgetbuddy.state.ContactState;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ContactStateMapper {

    public ContactState toContactState (ContactStateRequest request){
        return ContactState.builder()
                .id(request.id())
                .code(request.code())
                .name(request.name())
                .description(request.description())
                .build();
    }

    public ContactStateResponse toContactStateResponse(ContactState contactState) {
        return ContactStateResponse.builder()
                .id(contactState.getId())
                .code(contactState.getCode())
                .name(contactState.getName())
                .description(contactState.getDescription())
                .build();
    }
}
