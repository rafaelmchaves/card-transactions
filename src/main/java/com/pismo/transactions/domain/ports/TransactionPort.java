package com.pismo.transactions.domain.ports;

import com.pismo.transactions.domain.Transaction;

import java.util.List;

public interface TransactionPort {

    Transaction createTransaction(Transaction transaction);

    void updateTransaction(Transaction transaction);

    List<Transaction> findAllNegativeBalanceTransactions(String accountId);

}
