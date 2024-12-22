package com.yassir.budgetbuddy.quotes;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuotesRepository extends JpaRepository<Quotes,Integer> {

    Optional<Quotes> findByAuthor(String author);

    Optional<Quotes> findByQuote(String quote);

}
