package com.pismo.transactions.infrastructure.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pismo.transactions.adapter.controller.requests.TransactionRequest;
import com.pismo.transactions.adapter.infrastructure.h2.entity.AccountJpaEntity;
import com.pismo.transactions.adapter.infrastructure.h2.repository.AccountJPARepository;
import com.pismo.transactions.adapter.infrastructure.h2.repository.TransactionJPARepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TransactionJPARepository transactionJPARepository;

    @Autowired
    private AccountJPARepository accountJPARepository;

    private AccountJpaEntity accountJpaEntity;

    @BeforeEach
    public void setUp() {

        //save a new account in the database
        accountJpaEntity = AccountJpaEntity.builder()
                .documentNumber("23184721")
                .build();
        accountJPARepository.save(accountJpaEntity);

        transactionJPARepository.deleteAll();

    }

    @Test
    public void createTransaction_givenADebitTransaction_ShouldReturn201AndSaveDataInDatabase() throws Exception {

        final var amount = BigDecimal.valueOf(45.78);
        // Given
        TransactionRequest transactionRequest = TransactionRequest.builder().amount(amount)
                .accountId(accountJpaEntity.getId().toString()).operationTypeId(1)
                .build();

        // When
        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionRequest)))
                .andExpect(status().isCreated());

        // Then
        final var transactionJpaEntities = transactionJPARepository.findAll();
        assertEquals(1, transactionJpaEntities.size());

        final var savedTransaction = transactionJpaEntities.get(0);

        assertEquals(BigDecimal.valueOf(-45.78), savedTransaction.getAmount());
        assertEquals(1, savedTransaction.getOperationType().getId());
        assertEquals(accountJpaEntity.getId(), savedTransaction.getAccount().getId());
    }

    @Test
    public void createTransaction_givenACreditTransaction_ShouldReturn201AndSaveDataInDatabase() throws Exception {

        final var amount = BigDecimal.valueOf(45.78);
        // Given
        TransactionRequest transactionRequest = TransactionRequest.builder().amount(amount)
                .accountId(accountJpaEntity.getId().toString()).operationTypeId(4)
                .build();

        // When
        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionRequest)))
                .andExpect(status().isCreated());

        // Then
        final var transactionJpaEntities = transactionJPARepository.findAll();
        assertEquals(1, transactionJpaEntities.size());

        final var savedTransaction = transactionJpaEntities.get(0);

        assertEquals(BigDecimal.valueOf(45.78), savedTransaction.getAmount());
        assertEquals(4, savedTransaction.getOperationType().getId());
        assertEquals(accountJpaEntity.getId(), savedTransaction.getAccount().getId());
    }

    @Test
    public void createTransaction_givenAnOperationTypeNotExist_ShouldReturn400AndDontSaveDataInDatabase() throws Exception {

        final var amount = BigDecimal.valueOf(45.78);
        // Given
        TransactionRequest transactionRequest = TransactionRequest.builder().amount(amount)
                .accountId(accountJpaEntity.getId().toString()).operationTypeId(5)
                .build();

        // When
        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionRequest)))
                .andExpect(status().is(400))
                .andExpect(content().json("{\"message\":\"Operation type not found\",\"errorCode\":\"OPT_01\"}"));

        // Then
        final var transactionJpaEntities = transactionJPARepository.findAll();
        assertEquals(0, transactionJpaEntities.size());

    }

    @Test
    public void createTransaction_givenAnAccountNotExist_ShouldReturn400AndDontSaveDataInDatabase() throws Exception {

        final var amount = BigDecimal.valueOf(45.78);
        // Given
        TransactionRequest transactionRequest = TransactionRequest.builder().amount(amount)
                .accountId(UUID.randomUUID().toString()).operationTypeId(4)
                .build();

        // When
        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionRequest)))
                .andExpect(status().is(400))
                .andExpect(content().json("{\"message\":\"Account not found\",\"errorCode\":\"ACC_01\"}"));

        // Then
        final var transactionJpaEntities = transactionJPARepository.findAll();
        assertEquals(0, transactionJpaEntities.size());
    }
}
