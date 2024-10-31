package com.ashapiro.testassigment.model;

import com.ashapiro.testassigment.model.sales.SalesByDate;
import com.ashapiro.testassigment.model.sales.TrafficByDate;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDate;

@Document
@Getter @Setter
@ToString
@EqualsAndHashCode
public class SalesAndTrafficByDate  implements Serializable{

    private LocalDate date;

    private SalesByDate salesByDate;

    private TrafficByDate trafficByDate;

}
