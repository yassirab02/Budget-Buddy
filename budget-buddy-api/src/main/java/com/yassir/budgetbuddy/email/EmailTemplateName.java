package com.yassir.budgetbuddy.email;


import lombok.Getter;

@Getter
public enum EmailTemplateName {

    ACTIVATE_ACCOUNT("activate_account"),
    WELCOME_EMAIL("welcome_email"),
    MONTHLY_REPORT("monthly_report"),
    YEARLY_REPORT("yearly_report"),

    ;


    private final String name;

    EmailTemplateName(String name) {
        this.name = name;
    }
}
