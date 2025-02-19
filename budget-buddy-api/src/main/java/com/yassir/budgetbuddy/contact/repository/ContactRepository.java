package com.yassir.budgetbuddy.contact.repository;

import com.yassir.budgetbuddy.budget.Budget;
import com.yassir.budgetbuddy.contact.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> , JpaSpecificationExecutor<Contact> {

}
