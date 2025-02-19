package com.yassir.budgetbuddy.note.controller;

import com.yassir.budgetbuddy.note.Note;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NoteMapper {

    public Note toNote (NoteRequest request){
        return Note.builder()
                .id(request.id())
                .title(request.title())
                .content(request.content())
                .build();
    }

    public NoteResponse toNoteResponse(Note note) {
        return NoteResponse.builder()
                .id(note.getId())
                .title(note.getTitle())
                .content(note.getContent())
                .createdDate(note.getCreatedDate())
                .build();
    }
}
