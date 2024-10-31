package com.ashapiro.testassigment.model.sales;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
@EqualsAndHashCode
public class SalesByAsin implements Serializable {

    private Integer unitsOrdered;
    private Integer unitsOrderedB2B;
    private Monetary orderedProductSales;
    private Monetary orderedProductSalesB2B;
    private Integer totalOrderItems;
    private Integer totalOrderItemsB2B;
}
