package com.ashapiro.testassigment.model.sales;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter @Setter
@ToString
@EqualsAndHashCode
public class Monetary implements Serializable {

    private Double amount;

    private String currencyCode;
}
