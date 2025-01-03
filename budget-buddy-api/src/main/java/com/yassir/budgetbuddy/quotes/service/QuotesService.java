package com.yassir.budgetbuddy.quotes.service;

import com.yassir.budgetbuddy.quotes.Quotes;
import com.yassir.budgetbuddy.quotes.controller.QuotesRequest;
import com.yassir.budgetbuddy.quotes.controller.QuotesResponse;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

public interface QuotesService {

    Integer addOrUpdateQuote(QuotesRequest request, Authentication connectedUser);

    void uploadQuotePicture(MultipartFile file, Authentication connectedUser, Integer quoteId);

    QuotesResponse getQuoteForToday();

    void deleteQuote(Integer quoteId, Authentication connectedUser);

}
