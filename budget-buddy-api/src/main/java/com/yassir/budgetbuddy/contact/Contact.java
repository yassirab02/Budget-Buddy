package com.yassir.budgetbuddy.contact;

import com.yassir.budgetbuddy.common.BaseEntity;
import com.yassir.budgetbuddy.state.ContactState;
import com.yassir.budgetbuddy.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Contact extends BaseEntity {

    private String email; // Optional: Allows non-registered users to send messages
    private String subject;
    @Lob
    private String message;
    private String sender;

    @ManyToOne
    @JoinColumn(name = "state_id", nullable = false)
    private ContactState state;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // Nullable to allow guest users to send messages
}
