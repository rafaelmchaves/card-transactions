package com.pismo.transactions.adapter.controller.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
public class AccountRequest implements Serializable {

    @NotBlank(message = "Document Number is required")
    @JsonProperty("document_number")
    private String documentNumber;

}
