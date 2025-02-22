package com.yassir.budgetbuddy.admin;

import com.yassir.budgetbuddy.common.PageResponse;
import com.yassir.budgetbuddy.contact.controller.ContactRequest;
import com.yassir.budgetbuddy.contact.controller.ContactResponse;
import com.yassir.budgetbuddy.contact.service.ContactService;
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
    private final ContactService contactService;

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

    @DeleteMapping("/{quote-id}")
    public ResponseEntity<?> deleteQuote(
            @PathVariable("quote-id") Integer quoteId,
            Authentication connectedUser
    ) {
        quotesService.deleteQuote(quoteId, connectedUser);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("all-contact")
    public ResponseEntity<PageResponse<ContactResponse>> findAllContactsMessages(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(contactService.findAllContactsMessages(page, size, connectedUser));
    }

    @DeleteMapping("/contact/{contact-id}")
    public int deleteContactMessage(
            @PathVariable("contact-id") Integer contactId,
            Authentication connectedUser
    ) {
         return contactService.deleteContactMessage(contactId, connectedUser);
    }

    @PostMapping("/contact/{contact-id}")
    public ResponseEntity<ContactResponse> updateContactMessage(
            @PathVariable("contact-id") ContactRequest request,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(contactService.updateContactMessage(request,connectedUser));
    }
}
