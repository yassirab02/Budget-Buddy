package com.yassir.budgetbuddy.note.service;

import com.yassir.budgetbuddy.expenses.Expenses;
import com.yassir.budgetbuddy.expenses.repository.ExpensesRepository;
import com.yassir.budgetbuddy.note.Note;
import com.yassir.budgetbuddy.note.controller.NoteMapper;
import com.yassir.budgetbuddy.note.controller.NoteRequest;
import com.yassir.budgetbuddy.note.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository repository;
    private final NoteMapper noteMapper;
    private final ExpensesRepository expensesRepository;


    @Transactional
    @Override
    public int addNote(NoteRequest request) {
        // Fetch the expense to ensure it exists
        Expenses expense = expensesRepository.findById(request.expenseId())
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        Note note = noteMapper.toNote(request);
        note.setExpense(expense);

        // Save the note and return its ID
        return repository.save(note).getId();
    }

    @Override
    public void deleteNote(Integer id) {
        if (!repository.existsById(id)) {
            return;
        }
        repository.deleteById(id);
    }
}
