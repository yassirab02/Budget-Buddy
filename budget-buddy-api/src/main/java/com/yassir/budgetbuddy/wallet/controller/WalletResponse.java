package com.yassir.budgetbuddy.wallet.controller;

import com.yassir.budgetbuddy.expenses.controller.ExpensesResponse;
import com.yassir.budgetbuddy.income.controller.IncomeResponse;
import com.yassir.budgetbuddy.transaction.controller.TransactionResponse;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalletResponse {

        private Integer id;
        private String name;
        private BigDecimal balance;
        private BigDecimal totalIncome;
        private BigDecimal totalExpenses;
        private String walletType; // Name of the wallet type
        private String owner; // Full name of the user who owns the wallet
        private List<IncomeResponse> incomes; // A list of income responses
        private List<ExpensesResponse> expenses; // A list of expense responses
        private List<TransactionResponse> sentTransfers; // A list of sent transfers
        private List<TransactionResponse> receivedTransfers; // A list of received transfers

}
