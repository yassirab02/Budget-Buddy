package com.yassir.budgetbuddy.quotes;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface QuotesRepository extends JpaRepository<Quotes,Integer> {

    Optional<Quotes> findByDateOfDisplay(LocalDate dateOfDisplay);

}
