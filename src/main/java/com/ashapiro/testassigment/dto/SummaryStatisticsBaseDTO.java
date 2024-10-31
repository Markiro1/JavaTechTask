package com.ashapiro.testassigment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SummaryStatisticsBaseDTO implements Serializable {
    private Double totalOrderedProductSales;
    private Double totalOrderedProductSalesB2B;
    private Integer totalOrderItems;
    private Integer totalOrderItemsB2B;
    private Integer totalUnitsOrdered;
    private Integer totalUnitsOrderedB2B;
}
