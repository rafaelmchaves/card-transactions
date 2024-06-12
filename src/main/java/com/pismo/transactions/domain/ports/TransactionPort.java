package com.pismo.transactions.domain.ports;

import com.pismo.transactions.domain.Transaction;

public interface TransactionPort {

    Transaction createTransaction(Transaction transaction);

}
