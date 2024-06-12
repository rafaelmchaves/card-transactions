package com.pismo.transactions.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Account {

    private String id;
    private String documentNumber;

}
