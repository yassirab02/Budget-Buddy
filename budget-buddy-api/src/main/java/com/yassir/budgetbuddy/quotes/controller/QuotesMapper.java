package com.yassir.budgetbuddy.quotes.controller;

import com.yassir.budgetbuddy.file.FileUtils;
import com.yassir.budgetbuddy.quotes.Quotes;
import org.springframework.stereotype.Service;

@Service
public class QuotesMapper {


    public Quotes toQuote(QuotesRequest request) {
        return Quotes.builder()
                .id(request.id())
                .quote(request.quote())
                .author(request.author())
                .dateOfDisplay(request.dateOfDisplay())
                .build();
    }

    public QuotesResponse toQuoteResponse(Quotes quote) {
        return QuotesResponse.builder()
                .id(quote.getId())
                .quote(quote.getQuote())
                .author(quote.getQuotePhoto())
                .quotePhoto(FileUtils.readFileFromLocation(quote.getQuotePhoto()))
                .dateOfDisplay(quote.getDateOfDisplay())
                .build();
    }
}
