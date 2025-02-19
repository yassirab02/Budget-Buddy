package com.yassir.budgetbuddy.note.controller;

import com.yassir.budgetbuddy.note.service.NoteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("note")
@RequiredArgsConstructor
@Tag(name = "Note")
public class NoteController {

    private final NoteService service;

    @PostMapping("add-note")
    public ResponseEntity<Integer> createNote(
            @RequestBody @Valid NoteRequest request
    ) {
        return ResponseEntity.ok(service.addNote(request));
    }

    @DeleteMapping("/{note-id}")
    public ResponseEntity<?> deleteNote(
            @PathVariable("note-id") Integer noteId
    ) {
        service.deleteNote(noteId);
        return ResponseEntity.noContent().build();
    }
}
