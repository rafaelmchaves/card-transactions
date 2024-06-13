package com.pismo.transactions.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Account {

    private String id;
    private String documentNumber;

}
