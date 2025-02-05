package com.yassir.budgetbuddy.user.controller;

import com.yassir.budgetbuddy.user.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserMapper {

        public UserResponse toUserResponse(User user) {
            return UserResponse.builder()
                    .id(user.getId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .fullName(user.fullName())
                    .totalBalance(user.getTotalBalance())
                    .createdAt(user.getCreatedAt())
                    .dateOfBirth(user.getDateOfBirth())
                    .placeOfBirth(user.getPlaceOfBirth())
                    .isNew(user.isNew())
                    .build();
        }
}
