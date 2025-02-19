package com.yassir.budgetbuddy.state.repository;

import com.yassir.budgetbuddy.state.ContactState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ContactStateRepository extends JpaRepository<ContactState, Integer> {
    ContactState findByCode(String code);
}
