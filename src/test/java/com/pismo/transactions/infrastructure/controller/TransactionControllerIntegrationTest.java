package com.pismo.transactions.infrastructure.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pismo.transactions.adapter.controller.requests.TransactionRequest;
import com.pismo.transactions.adapter.infrastructure.h2.jpa.entity.AccountJpaEntity;
import com.pismo.transactions.adapter.infrastructure.h2.jpa.AccountJPARepository;
import com.pismo.transactions.adapter.infrastructure.h2.jpa.TransactionJPARepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
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

        transactionJPARepository.deleteAll();
        accountJPARepository.deleteAll();

        //save a new account in the database
        accountJpaEntity = AccountJpaEntity.builder()
                .documentNumber("23184721")
                .build();
        accountJPARepository.save(accountJpaEntity);



    }

    @Test
    public void createTransaction_3DebitOperationAndOneCreditOperation_dischardDone() throws Exception {
        final var amount = BigDecimal.valueOf(50);
        // Given
        TransactionRequest transactionRequest1 = TransactionRequest.builder().amount(amount)
                .accountId(accountJpaEntity.getId().toString()).operationTypeId(1)
                .build();

        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionRequest1)))
                .andExpect(status().isCreated());

        // Given
        TransactionRequest transactionRequest2 = TransactionRequest.builder().amount(BigDecimal.valueOf(23.5))
                .accountId(accountJpaEntity.getId().toString()).operationTypeId(1)
                .build();

        performTransaction(transactionRequest2);
        TransactionRequest transactionRequest3 = TransactionRequest.builder().amount(BigDecimal.valueOf(18.7))
                .accountId(accountJpaEntity.getId().toString()).operationTypeId(1)
                .build();

        performTransaction(transactionRequest3);

        TransactionRequest transactionRequest4 = TransactionRequest.builder().amount(BigDecimal.valueOf(60))
                .accountId(accountJpaEntity.getId().toString()).operationTypeId(4)
                .build();

        performTransaction(transactionRequest4);

        final var transactionJpaEntities = transactionJPARepository.findAll();

        assertAll(() -> {
            assertEquals(BigDecimal.valueOf(0.00).setScale(2, RoundingMode.HALF_UP), transactionJpaEntities.get(0).getBalance());
            assertEquals(BigDecimal.valueOf(-13.5).setScale(2, RoundingMode.HALF_UP), transactionJpaEntities.get(1).getBalance());
            assertEquals(BigDecimal.valueOf(-18.7).setScale(2, RoundingMode.HALF_UP), transactionJpaEntities.get(2).getBalance());
            assertEquals(BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_UP), transactionJpaEntities.get(3).getBalance());
        });

    }

    @Test
    public void createTransaction_3DebitOperationAnd2CreditOperation_dischardDone() throws Exception {
        final var amount = BigDecimal.valueOf(50);
        // Given
        TransactionRequest transactionRequest1 = TransactionRequest.builder().amount(amount)
                .accountId(accountJpaEntity.getId().toString()).operationTypeId(1)
                .build();

        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionRequest1)))
                .andExpect(status().isCreated());

        // Given
        TransactionRequest transactionRequest2 = TransactionRequest.builder().amount(BigDecimal.valueOf(23.5))
                .accountId(accountJpaEntity.getId().toString()).operationTypeId(1)
                .build();

        performTransaction(transactionRequest2);
        TransactionRequest transactionRequest3 = TransactionRequest.builder().amount(BigDecimal.valueOf(18.7))
                .accountId(accountJpaEntity.getId().toString()).operationTypeId(1)
                .build();

        performTransaction(transactionRequest3);

        TransactionRequest transactionRequest4 = TransactionRequest.builder().amount(BigDecimal.valueOf(60))
                .accountId(accountJpaEntity.getId().toString()).operationTypeId(4)
                .build();

        performTransaction(transactionRequest4);

        TransactionRequest transactionRequest5 = TransactionRequest.builder().amount(BigDecimal.valueOf(100))
                .accountId(accountJpaEntity.getId().toString()).operationTypeId(4)
                .build();

        performTransaction(transactionRequest5);

        final var transactionJpaEntities = transactionJPARepository.findAll();

        assertAll(() -> {
            assertEquals(BigDecimal.valueOf(0.00).setScale(2, RoundingMode.HALF_UP), transactionJpaEntities.get(0).getBalance());
            assertEquals(BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_UP), transactionJpaEntities.get(1).getBalance());
            assertEquals(BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_UP), transactionJpaEntities.get(2).getBalance());
            assertEquals(BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_UP), transactionJpaEntities.get(3).getBalance());
            assertEquals(BigDecimal.valueOf(67.8).setScale(2, RoundingMode.HALF_UP), transactionJpaEntities.get(4).getBalance());
        });

    }

    private void performTransaction(TransactionRequest transactionRequest) throws Exception {
        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionRequest)))
                .andExpect(status().isCreated());
    }


    @DisplayName("Given a transaction which operation type is 1 ('COMPRA A VISTA') for a valid account id, " +
            "when we ask to create a transaction, then the transaction is " +
            "created and saved in the database, and the amount is negative")
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

    @DisplayName("Given a transaction which operation type is 4 ('PAGAMENTO') for a valid account id, " +
            "when we ask to create a transaction, then the transaction is " +
            "created and saved in the database and the amount is positive")
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

    @DisplayName("Given a transaction which operation type is invalid for a valid account id, " +
            "when we ask to create a transaction, then the transaction is not created and the returned http status is 400" )
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

    @DisplayName("Given a transaction which operation type is valid for an invalid account id, " +
            "when we ask to create a transaction, then the transaction is not created and the returned http status is 400" )
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

    @DisplayName("Given a transaction without a operation type id for a valid account id, " +
            "when we ask to create a transaction, then the transaction is not created and the returned http status is 400" )
    @Test
    public void createTransaction_givenOperationTypeIsNotPresent_ShouldReturn400AndDontSaveDataInDatabase() throws Exception {

        final var amount = BigDecimal.valueOf(45.78);
        // Given
        TransactionRequest transactionRequest = TransactionRequest.builder().amount(amount)
                .accountId(UUID.randomUUID().toString())
                .build();

        // When
        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionRequest)))
                .andExpect(status().is(400))
                .andExpect(content().json("{\"errors\":[\"Operation type cannot be null\"]}"));

        // Then
        final var transactionJpaEntities = transactionJPARepository.findAll();
        assertEquals(0, transactionJpaEntities.size());
    }

    @DisplayName("Given a transaction which operation type is valid and the account id is not present in the body of the request " +
            "when we ask to create a transaction, then the transaction is not created and the returned http status is 400" )
    @Test
    public void createTransaction_givenAccountIdIsNotPresent_ShouldReturn400AndDontSaveDataInDatabase() throws Exception {

        final var amount = BigDecimal.valueOf(45.78);
        // Given
        TransactionRequest transactionRequest = TransactionRequest.builder().amount(amount)
                .operationTypeId(4).build();

        // When
        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionRequest)))
                .andExpect(status().is(400))
                .andExpect(content().json("{\"errors\":[\"Account id cannot be null\"]}"));

        // Then
        final var transactionJpaEntities = transactionJPARepository.findAll();
        assertEquals(0, transactionJpaEntities.size());
    }

    @DisplayName("Given a transaction which operation type is valid for a valid account id but amount is not present in the body of the request " +
            "when we ask to create a transaction, then the transaction is not created and the returned http status is 400" )
    @Test
    public void createTransaction_givenAmountIsNotPresent_ShouldReturn400AndDontSaveDataInDatabase() throws Exception {

        // Given
        TransactionRequest transactionRequest = TransactionRequest.builder()
                .accountId(UUID.randomUUID().toString()).operationTypeId(4).build();

        // When
        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionRequest)))
                .andExpect(status().is(400))
                .andExpect(content().json("{\"errors\":[\"Amount cannot be null\"]}"));

        // Then
        final var transactionJpaEntities = transactionJPARepository.findAll();
        assertEquals(0, transactionJpaEntities.size());
    }
}
