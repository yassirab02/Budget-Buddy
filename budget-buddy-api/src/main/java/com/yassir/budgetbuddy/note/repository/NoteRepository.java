package com.yassir.budgetbuddy.note.repository;

import com.yassir.budgetbuddy.note.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {
}
