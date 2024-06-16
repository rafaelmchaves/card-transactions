package com.pismo.transactions.infrastructure.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pismo.transactions.adapter.controller.requests.AccountRequest;
import com.pismo.transactions.adapter.controller.requests.TransactionRequest;
import com.pismo.transactions.adapter.infrastructure.h2.jpa.AccountJPARepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AccountJPARepository accountJPARepository;

    @DisplayName("Given a document number, when we ask to create an account, then the account is created and " +
            "the returned http status is 201, and we check if the account was created by the get endpoint, and its returns ok(200)")
    @Test
    void createAccountAndGetAccount_byDocumentNumber_accountCreated() throws Exception {
        String documentNumber = "31324124";
        final AccountRequest accountRequest = new AccountRequest();
        accountRequest.setDocumentNumber(documentNumber);

        mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountRequest)))
                .andExpect(status().isCreated());

        var accountList = accountJPARepository.findAll();
        assertAll(() -> {
                assertNotNull(accountList);
                assertEquals(1, accountList.size());
                assertNotNull(accountList.get(0).getCreation());
                assertEquals(documentNumber, accountList.get(0).getDocumentNumber());
                UUID id = accountList.get(0).getId();

                mockMvc.perform(get("/accounts/" + id.toString()))
                        .andExpect(status().isOk())
                        .andExpect(content().json("{\"account_id\": \"" + id + "\"," + "\"document_number\": \"31324124\"}"));
        });

    }

    @DisplayName("Given a duplicated document number, when we ask to create an account, then the account is not created and " +
            "the returned http status is 400")
    @Test
    void createAccount_byADuplicatedDocumentNumber_accountNotCreated() throws Exception {
        String documentNumber = "31324124";
        final AccountRequest accountRequest = new AccountRequest();
        accountRequest.setDocumentNumber(documentNumber);

        mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountRequest)));

        mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"message\":\"Document number already exists in our base\",\"errorCode\":\"ACC_02\"}"));

        var accountList = accountJPARepository.findAll();
        assertAll(() -> {
            assertNotNull(accountList);
            assertEquals(1, accountList.size());
            assertNotNull(accountList.get(0).getCreation());
            assertEquals(documentNumber, accountList.get(0).getDocumentNumber());
        });

    }

    @DisplayName("Given a request without document number, when we ask for create the account, then the account is not created " +
            "and the returned http status is 400")
    @Test
    public void createAccount_givenDocumentNumberIsNotPresent_ShouldReturn400AndDontSaveDataInDatabase() throws Exception {

        // Given
        final AccountRequest accountRequest = new AccountRequest();

        // When
        mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountRequest)))
                .andExpect(status().is(400))
                .andExpect(content().json("{\"errors\":[\"Document Number cannot be null\"]}"));

        // Then
        final var accountJpaEntities = accountJPARepository.findAll();
        assertEquals(0, accountJpaEntities.size());
    }

    @DisplayName("Given an invalid account id, when we ask for get the account with this id, then the account is not found," +
            " and we receive a 204 as http status response")
    @Test
    void getAccount_idDoesNotExist_returnNoContentStatusAndEmptyBody() throws Exception {

        mockMvc.perform(get("/accounts/" + UUID.randomUUID()))
                .andExpect(status().isNoContent());

        var accountList = accountJPARepository.findAll();
        assertAll(() -> {
            assertNotNull(accountList);
            assertEquals(0, accountList.size());
        });
    }
}
