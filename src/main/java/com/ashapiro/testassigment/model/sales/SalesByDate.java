package com.ashapiro.testassigment.model.sales;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
@EqualsAndHashCode
public class SalesByDate implements Serializable {

    private Monetary orderedProductSales;
    private Monetary orderedProductSalesB2B;
    private Integer unitsOrdered;
    private Integer unitsOrderedB2B;
    private Integer totalOrderItems;
    private Integer totalOrderItemsB2B;
    private Monetary averageSalesPerOrderItem;
    private Monetary averageSalesPerOrderItemB2B;
    private Double averageUnitsPerOrderItem;
    private Double averageUnitsPerOrderItemB2B;
    private Monetary averageSellingPrice;
    private Monetary averageSellingPriceB2B;
    private Integer unitsRefunded;
    private Double refundRate;
    private Integer claimsGranted;
    private Monetary claimsAmount;
    private Monetary shippedProductSales;
    private Integer unitsShipped;
    private Integer ordersShipped;
}
