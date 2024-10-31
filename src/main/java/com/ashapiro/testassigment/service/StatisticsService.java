package com.ashapiro.testassigment.service;

import com.ashapiro.testassigment.dto.SummaryStatisticsBaseDTO;
import com.ashapiro.testassigment.model.SalesAndTrafficByAsin;
import com.ashapiro.testassigment.model.SalesAndTrafficByDate;

import java.time.LocalDate;
import java.util.List;

public interface StatisticsService {

    SalesAndTrafficByAsin getStatisticsByAsin(String asin);

    List<SalesAndTrafficByAsin> getStatisticsByListOfAsins(List<String> asins);

    List<SalesAndTrafficByDate> getStatisticsByDate(LocalDate date);

    List<SalesAndTrafficByDate> getStatisticsByDateRange(LocalDate startDate, LocalDate endDate);

    SummaryStatisticsBaseDTO getSummaryDateStatistics();

    SummaryStatisticsBaseDTO getSummaryAsinStatistics();
}
