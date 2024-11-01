package com.picpaysimplificado.records;

import java.math.BigDecimal;

public record NotificationRecord(String email, BigDecimal amount, String mesage) {
}
