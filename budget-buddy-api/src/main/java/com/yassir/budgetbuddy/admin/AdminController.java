package com.yassir.budgetbuddy.admin;

import com.yassir.budgetbuddy.common.PageResponse;
import com.yassir.budgetbuddy.income.controller.IncomeRequest;
import com.yassir.budgetbuddy.quotes.Quotes;
import com.yassir.budgetbuddy.quotes.controller.QuotesRequest;
import com.yassir.budgetbuddy.quotes.service.QuotesService;
import com.yassir.budgetbuddy.story.controller.StoryResponse;
import com.yassir.budgetbuddy.story.service.StoryService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
@Tag(name = "admin")
public class AdminController {

    private final StoryService storyService;
    private final QuotesService quotesService;

    @GetMapping
    public ResponseEntity<PageResponse<StoryResponse>> findAllStories(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser

    ) {
        return ResponseEntity.ok(storyService.findAllStories(page, size,connectedUser));
    }

    @PostMapping("/add-quote")
    public ResponseEntity<Integer> addOrUpdateQuote(
            @RequestBody @Valid QuotesRequest request, Authentication connectedUser
    ) {
        return ResponseEntity.ok(quotesService.addOrUpdateQuote(request,connectedUser));
    }

    @PostMapping(value = "/photo/{quote-id}", consumes = "multipart/form-data")
    public ResponseEntity<?> uploadQuotePhoto(
            @PathVariable("quote-id") Integer quoteId,
            @Parameter()
            @RequestPart("file") MultipartFile file,
            Authentication connectedUser
    ) {
        quotesService.uploadQuotePicture(file, connectedUser, quoteId);
        return ResponseEntity.accepted().build();
    }


    @GetMapping("/today")
    public Quotes getTodayQuote() {
        return quotesService.getQuoteForToday();
    }

    @DeleteMapping("/{quote-id}")
    public ResponseEntity<?> deleteBudget(
            @PathVariable("quote-id") Integer quoteId,
            Authentication connectedUser
    ) {
        quotesService.deleteQuote(quoteId, connectedUser);
        return ResponseEntity.noContent().build();
    }


}
