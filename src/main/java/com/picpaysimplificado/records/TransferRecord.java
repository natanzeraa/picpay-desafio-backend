package com.picpaysimplificado.records;

import java.math.BigDecimal;

public record TransferRecord(BigDecimal amount, Integer sender, Integer receiver) {

}
