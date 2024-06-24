package com.pismo.transactions.adapter.infrastructure.h2.jpa;

import com.pismo.transactions.adapter.infrastructure.h2.jpa.entity.TransactionJpaEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionJPARepository extends ListCrudRepository<TransactionJpaEntity, Long> {

    @Query("select transactions from TransactionJpaEntity transactions where transactions.account.id = :accountId and " +
            "transactions.balance < 0.0 and operationType.multiplier = -1 order by transactions.eventDate")
    List<TransactionJpaEntity> findAllByAccountIdSortByEventDate(UUID accountId);
}
