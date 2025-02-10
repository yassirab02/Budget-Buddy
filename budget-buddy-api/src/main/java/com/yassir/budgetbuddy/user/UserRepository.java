package com.yassir.budgetbuddy.user;


import com.yassir.budgetbuddy.user.controller.UserResponse;
import com.yassir.budgetbuddy.user.controller.UserTransferResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name <> 'ADMIN'")
    List<User> findAllUsers();

    @Query("SELECT u FROM User u WHERE u.id <> :id")
    List<User> findAllUsersTransfer(Integer id);
}
