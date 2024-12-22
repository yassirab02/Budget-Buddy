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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

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


    @Transactional
    @Override
    public Quotes getQuoteForToday() {
        LocalDate today = LocalDate.now();
        Quotes todayQuote = quotesRepository.findByDateOfDisplay(today)
                .orElseGet(() -> getRandomQuote());
        return todayQuote;
    }

    // Method to get a random quote if no quote for today
    private Quotes getRandomQuote() {
        // Get all quotes
        List<Quotes> allQuotes = quotesRepository.findAll();

        if (allQuotes.isEmpty()) {
            throw new EntityNotFoundException("No quotes available in the database");
        }

        // Pick a random quote
        Random random = new Random();
        return allQuotes.get(random.nextInt(allQuotes.size()));
    }

    @Override
    public void deleteQuote(Integer quoteId, Authentication connectedUser) {
        if (quoteId == null) {
            throw new IllegalArgumentException("Story ID cannot be null");
        }
        User user = (User) connectedUser.getPrincipal();
        Quotes quote = repository.findById(quoteId)
                .orElseThrow(() -> new EntityNotFoundException("Story not found with id: " + quoteId));

        if (user.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new IllegalArgumentException("You are not allowed to delete this story");
        }

        repository.delete(quote);
    }


}
