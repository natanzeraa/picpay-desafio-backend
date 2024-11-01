package com.picpaysimplificado.records;

import java.math.BigDecimal;

public record UserRecord(Integer id, String name, String email, String password, String document, BigDecimal balance, String userType) {

}
