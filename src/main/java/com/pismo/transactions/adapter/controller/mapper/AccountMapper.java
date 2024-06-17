package com.pismo.transactions.adapter.controller.mapper;

import com.pismo.transactions.adapter.controller.response.AccountResponse;
import com.pismo.transactions.domain.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountResponse convert(Account account);

}
