package com.yassir.budgetbuddy.common;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageResponse<T> {

    private List<T> content;
    private int number; // page number
    private int size; // size of each page
    private long totalElements;
    private int totalPages;
    // if the page is the last or the first
    private boolean first;
    private boolean last;

}
