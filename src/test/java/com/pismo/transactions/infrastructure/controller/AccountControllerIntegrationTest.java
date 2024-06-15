package com.pismo.transactions.infrastructure.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pismo.transactions.adapter.controller.requests.AccountRequest;
import com.pismo.transactions.adapter.infrastructure.h2.repository.AccountJPARepository;
import com.pismo.transactions.domain.service.AccountService;
import org.junit.jupiter.api.Assertions;
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
    private AccountService accountService;

    @Autowired
    private AccountJPARepository accountJPARepository;

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

}
