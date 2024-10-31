package com.ashapiro.testassigment.model;

import com.ashapiro.testassigment.model.sales.SalesByAsin;
import com.ashapiro.testassigment.model.sales.TrafficByAsin;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;


@Document
@Getter @Setter
@EqualsAndHashCode
public class SalesAndTrafficByAsin implements Serializable {

    private String parentAsin;

    private SalesByAsin salesByAsin;

    private TrafficByAsin trafficByAsin;
}
