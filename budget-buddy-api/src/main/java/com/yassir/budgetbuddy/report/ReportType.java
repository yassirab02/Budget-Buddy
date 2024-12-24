package com.yassir.budgetbuddy.report;

import lombok.Getter;

@Getter
public enum ReportType {
    MONTHLY("Monthly"),
    YEARLY("Yearly"),
    ;

    private final String name;

    ReportType(String name) {
        this.name = this.name();
    }
}
