package com.pismo.transactions.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OperationType {

    private Integer id;
    private String description;

}
