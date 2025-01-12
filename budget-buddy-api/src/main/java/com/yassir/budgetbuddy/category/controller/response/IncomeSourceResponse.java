package com.yassir.budgetbuddy.category.controller.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IncomeSourceResponse {

    private Integer id;
    private String name;
    private String description;
    private byte[] iconUrl;
}
