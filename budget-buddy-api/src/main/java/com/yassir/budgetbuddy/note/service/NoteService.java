package com.yassir.budgetbuddy.note.service;

import com.yassir.budgetbuddy.note.controller.NoteRequest;
import org.springframework.transaction.annotation.Transactional;

public interface NoteService {

    @Transactional
    int addNote(NoteRequest request);

    void deleteNote(Integer id);
}
