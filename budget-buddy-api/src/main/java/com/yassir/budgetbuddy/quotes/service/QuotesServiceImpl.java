package com.yassir.budgetbuddy.quotes.service;

import com.yassir.budgetbuddy.quotes.Quotes;
import com.yassir.budgetbuddy.quotes.QuotesRepository;
import com.yassir.budgetbuddy.quotes.controller.QuotesMapper;
import com.yassir.budgetbuddy.quotes.controller.QuotesRequest;
import com.yassir.budgetbuddy.file.FileStorageService;
import com.yassir.budgetbuddy.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class QuotesServiceImpl implements QuotesService {

    private final QuotesRepository repository;
    private final FileStorageService fileStorageService;
    private final QuotesRepository quotesRepository;
    private final QuotesMapper quotesMapper;

    @Override
    public Integer addOrUpdateQuote(QuotesRequest request, Authentication connectedUser) {
        if (connectedUser.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new IllegalArgumentException("You are not allowed to update this Quote");
        }
        Quotes quote = quotesMapper.toQuote(request);
        return quotesRepository.save(quote).getId();
    }

    @Override
    public void uploadQuotePicture(MultipartFile file, Authentication connectedUser, Integer quoteId) {
        Quotes quote = quotesRepository.findById(quoteId)
                .orElseThrow(() -> new EntityNotFoundException("No Budget found with the Id : " + quoteId));
        User user = ((User) connectedUser.getPrincipal());
        if (connectedUser.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new IllegalArgumentException("You are not allowed to update this Quote");
        }
        var quotePhoto = fileStorageService.saveFile(file, user.getId());
        quote.setQuotePhoto(quotePhoto);
        quotesRepository.save(quote);
    }


}
