package com.pismo.transactions.adapter.controller;

import com.pismo.transactions.adapter.controller.requests.AccountRequest;
import com.pismo.transactions.adapter.controller.response.AccountResponse;
import com.pismo.transactions.common.exceptions.ErrorMessage;
import com.pismo.transactions.domain.Account;
import com.pismo.transactions.domain.usecases.AccountUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Tag(name = "Account", description = "Account APIs")
public class AccountController {

    private final AccountUseCase accountUseCase;

    @PostMapping("/accounts")
    @Operation(summary = "Create a new account,so it would be possible to do a transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Account created"),
            @ApiResponse(responseCode = "400", description = "Document number is required",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
    })
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody AccountRequest accountRequest) {
        final var account = accountUseCase.createAccount(Account.builder().documentNumber(accountRequest.getDocumentNumber()).build());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(AccountResponse.builder().id(account.getId())
                        .documentNumber(account.getDocumentNumber()).build());
    }

    @GetMapping("/accounts/{accountId}")
    @Operation(summary = "Find the account by account Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Account found"),
            @ApiResponse(responseCode = "204", description = "No content was found for this account id", content = {})
    })
    public ResponseEntity<AccountResponse> getAccount(@PathVariable String accountId) {
        final var accountOptional = this.accountUseCase.getAccount(accountId);
        return accountOptional.map(account ->
                        ResponseEntity.ok(
                                AccountResponse.builder().id(account.getId()).documentNumber(account.getDocumentNumber()).build()))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

}
