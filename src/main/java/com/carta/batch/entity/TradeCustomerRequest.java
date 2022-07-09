package com.carta.batch.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Request class for controller
 */
@Valid
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TradeCustomerRequest {

@NotNull
private String filePath;

@NotNull
private String date;

private Integer fraction;
}
