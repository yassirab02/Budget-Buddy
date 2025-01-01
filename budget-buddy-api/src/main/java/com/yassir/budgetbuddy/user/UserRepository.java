package com.yassir.budgetbuddy.user;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name <> 'ADMIN'")
    List<User> findAllUsers();
}
