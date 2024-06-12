package com.pismo.transactions.infrastructure.jpa;

import com.pismo.transactions.infrastructure.entity.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

}
