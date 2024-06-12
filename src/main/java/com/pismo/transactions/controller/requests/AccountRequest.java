package com.pismo.transactions.controller.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequest implements Serializable {

    @JsonProperty("document_number")
    private String documentNumber;

}
