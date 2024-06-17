package com.pismo.transactions.adapter.controller.mapper;

import com.pismo.transactions.adapter.controller.requests.TransactionRequest;
import com.pismo.transactions.domain.Account;
import com.pismo.transactions.domain.OperationType;
import com.pismo.transactions.domain.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {AccountMapper.class})
public interface TransactionMapper {

    default Transaction convert(TransactionRequest transactionRequest) {
        return Transaction.builder().amount(transactionRequest.getAmount())
                .operationType(OperationType.builder().id(transactionRequest.getOperationTypeId()).build())
                .account(Account.builder().id(transactionRequest.getAccountId()).build())
                .build();
    }

}
