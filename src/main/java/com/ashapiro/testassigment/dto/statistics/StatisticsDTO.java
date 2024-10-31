package com.ashapiro.testassigment.dto.statistics;

import com.ashapiro.testassigment.model.ReportSpecification;
import com.ashapiro.testassigment.model.SalesAndTrafficByAsin;
import com.ashapiro.testassigment.model.SalesAndTrafficByDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsDTO implements Serializable {
    private ReportSpecification reportSpecification;
    private List<SalesAndTrafficByDate> salesAndTrafficByDate;
    private List<SalesAndTrafficByAsin> salesAndTrafficByAsin;

}
