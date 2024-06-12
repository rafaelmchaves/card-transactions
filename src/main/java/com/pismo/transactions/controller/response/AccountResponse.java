package com.pismo.transactions.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AccountResponse {

    @JsonProperty("account_id")
    private String id;

    @JsonProperty("document_number")
    private String documentNumber;

}
