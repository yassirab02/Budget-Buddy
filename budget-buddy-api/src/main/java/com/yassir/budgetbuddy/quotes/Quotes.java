package com.yassir.budgetbuddy.quotes;

import com.yassir.budgetbuddy.common.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Quotes extends BaseEntity {

    private String quote;

    private String author;

    private String quotePhoto;


}
