package com.pismo.transactions.adapter.controller;

import com.pismo.transactions.adapter.controller.mapper.TransactionMapper;
import com.pismo.transactions.adapter.controller.requests.TransactionRequest;
import com.pismo.transactions.common.exceptions.ErrorMessage;
import com.pismo.transactions.domain.Account;
import com.pismo.transactions.domain.OperationType;
import com.pismo.transactions.domain.Transaction;
import com.pismo.transactions.domain.usecases.TransactionUseCase;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Tag(name = "Transaction", description = "Transaction APIs")
public class TransactionController {

    private final TransactionUseCase transactionUseCase;

    private final TransactionMapper transactionMapper;

    @PostMapping("/transactions")
    @Operation(summary = "Create a new transaction for a particular account.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transaction created"),
            @ApiResponse(responseCode = "400", description = "Account or Operation type not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    public ResponseEntity<Void> createTransaction(@Valid @RequestBody TransactionRequest transactionRequest) {

        transactionUseCase.createTransaction(transactionMapper.convert(transactionRequest));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }



}
