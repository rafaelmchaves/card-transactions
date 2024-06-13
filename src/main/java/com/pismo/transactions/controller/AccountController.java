package com.pismo.transactions.controller;

import com.pismo.transactions.controller.requests.AccountRequest;
import com.pismo.transactions.controller.response.AccountResponse;
import com.pismo.transactions.domain.Account;
import com.pismo.transactions.domain.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/accounts")
    public ResponseEntity<AccountResponse> createAccount(@RequestBody AccountRequest accountRequest) {
        final var account = accountService.createAccount(Account.builder().documentNumber(accountRequest.getDocumentNumber()).build());
        // TODO think about the return of the method. Should I return the id in the header or a body with the saved entity?
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(AccountResponse.builder().id(account.getId())
                        .documentNumber(account.getDocumentNumber()).build());
    }

    @GetMapping("/accounts/{accountId}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable String accountId) {
        final var account = this.accountService.getAccount(accountId);
        return ResponseEntity.ok(AccountResponse.builder().id(accountId).documentNumber(account.getDocumentNumber()).build());
    }


}
