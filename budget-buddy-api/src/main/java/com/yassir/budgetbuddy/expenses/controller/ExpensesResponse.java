package com.yassir.budgetbuddy.expenses.controller;

import com.yassir.budgetbuddy.note.controller.NoteResponse;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpensesResponse {

    private Integer id;
    private String name;
    private BigDecimal amount;
    private String description;
    private LocalDate date;
    private boolean archived;
    private String category;
    private String expensesType; // E.g., Fixed, Variable
    private String budget;
    private String wallet;
    private List<NoteResponse> notes;

    public ExpensesResponse(Integer id, String name, BigDecimal amount, String description, LocalDate date,String categoryName, String type) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.category = categoryName;
        this.expensesType = type;
    }
}
