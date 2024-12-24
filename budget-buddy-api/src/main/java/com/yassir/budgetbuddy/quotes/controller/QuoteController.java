package com.yassir.budgetbuddy.quotes.controller;

import com.yassir.budgetbuddy.quotes.service.QuotesService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("quote")
@RequiredArgsConstructor
@Tag(name = "Quote")
public class QuoteController {

    private final QuotesService service;

    @GetMapping("/today-quote")
    public ResponseEntity<QuotesResponse> getTodayQuote(
    ) {
        return ResponseEntity.ok(service.getQuoteForToday());
    }

}
