package com.carta.batch.entity;

import lombok.*;

import java.math.BigDecimal;

/**
 * POJO class for TradeCustomer
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class TradeCustomer {

    private String vest;

    private String employeeId;

    private String employeeName;

    private String awardId;

    private String date;

    private BigDecimal quantity;

}
