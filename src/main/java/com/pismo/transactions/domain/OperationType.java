package com.pismo.transactions.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OperationType {

    private Integer id;
    private String description;
    private Integer multiplier;

}
