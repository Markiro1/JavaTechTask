package com.ashapiro.testassigment.service.impl;

import com.ashapiro.testassigment.dto.SummaryStatisticsBaseDTO;
import com.ashapiro.testassigment.dto.statistics.SummaryStatisticsByAsinDTO;
import com.ashapiro.testassigment.dto.statistics.SummaryStatisticsByDateDTO;
import com.ashapiro.testassigment.exception.StatisticsNotFoundException;
import com.ashapiro.testassigment.model.SalesAndTrafficByAsin;
import com.ashapiro.testassigment.model.SalesAndTrafficByDate;
import com.ashapiro.testassigment.repository.SalesAndTrafficByAsinRepo;
import com.ashapiro.testassigment.repository.SalesAndTrafficByDateRepo;
import com.ashapiro.testassigment.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final SalesAndTrafficByAsinRepo salesAndTrafficByAsinRepo;

    private final SalesAndTrafficByDateRepo salesAndTrafficByDateRepo;

    private final MongoTemplate mongoTemplate;

    @Override
    @Cacheable(value = "statisticsCache", key = "#date")
    public List<SalesAndTrafficByDate> getStatisticsByDate(LocalDate date) {
        return salesAndTrafficByDateRepo.findByDate(date)
                .orElseThrow(StatisticsNotFoundException::new);
    }

    @Override
    @Cacheable(value = "statisticsCache", key = "#startDate.toString() + '-'+ #endDate.toString()")
    public List<SalesAndTrafficByDate> getStatisticsByDateRange(LocalDate startDate, LocalDate endDate) {
        var list = salesAndTrafficByDateRepo.findByDateBetween(startDate, endDate)
                .orElseThrow(StatisticsNotFoundException::new);
        return list;
    }

    @Override
    @Cacheable(value = "statisticsCache", key = "#asin")
    public SalesAndTrafficByAsin getStatisticsByAsin(String asin) {
        return salesAndTrafficByAsinRepo.findByParentAsin(asin)
                .orElseThrow(StatisticsNotFoundException::new);
    }

    @Override
    @Cacheable(value = "statisticsCache", key = "T(java.util.Objects).hash(#asins.stream().distinct().sorted().toArray())")
    public List<SalesAndTrafficByAsin> getStatisticsByListOfAsins(List<String> asins) {
        return salesAndTrafficByAsinRepo.findByParentAsinIn(asins)
                .orElseThrow(StatisticsNotFoundException::new);
    }

    @Override
    @Cacheable(value = "statisticsCache", key = "'summaryDateStatistics'")
    public SummaryStatisticsBaseDTO getSummaryDateStatistics() {
        AggregationResults<SummaryStatisticsByDateDTO> results = aggregate(
                "salesAndTrafficByDate",
                "salesByDate",
                SummaryStatisticsByDateDTO.class);
        return getSummaryStatisticsDTO(results);
    }

    @Override
    @Cacheable(value = "statisticsCache", key = "'summaryAsinStatistics'")
    public SummaryStatisticsBaseDTO getSummaryAsinStatistics() {
        AggregationResults<SummaryStatisticsByAsinDTO> results = aggregate(
                "salesAndTrafficByAsin",
                "salesByAsin",
                SummaryStatisticsByAsinDTO.class
        );
        return getSummaryStatisticsDTO(results);
    }

    private <T> AggregationResults<T> aggregate(String collectionName, String field, Class<T> clazz) {
        Aggregation aggregation = (field.equals("salesByDate"))
                ? initializeAggregationForDate(field)
                : initializeAggregationForAsin(field);

        return mongoTemplate.aggregate(aggregation, collectionName, clazz);
    }

    private Aggregation initializeAggregationForAsin(String field) {
        return Aggregation.newAggregation(
                Aggregation.group()
                        .sum(String.format("%s.orderedProductSales.amount", field)).as("totalOrderedProductSales")
                        .sum(String.format("%s.orderedProductSalesB2B.amount", field)).as("totalOrderedProductSalesB2B")
                        .sum(String.format("%s.totalOrderItems", field)).as("totalOrderItems")
                        .sum(String.format("%s.totalOrderItemsB2B", field)).as("totalOrderItemsB2B")
                        .sum(String.format("%s.unitsOrdered", field)).as("totalUnitsOrdered")
                        .sum(String.format("%s.unitsOrderedB2B", field)).as("totalUnitsOrderedB2B")
        );
    }

    private Aggregation initializeAggregationForDate(String field) {
        return Aggregation.newAggregation(
                Aggregation.group()
                        .sum(String.format("%s.orderedProductSales.amount", field)).as("totalOrderedProductSales")
                        .sum(String.format("%s.orderedProductSalesB2B.amount", field)).as("totalOrderedProductSalesB2B")
                        .sum(String.format("%s.totalOrderItems", field)).as("totalOrderItems")
                        .sum(String.format("%s.totalOrderItemsB2B", field)).as("totalOrderItemsB2B")
                        .sum(String.format("%s.unitsOrdered", field)).as("totalUnitsOrdered")
                        .sum(String.format("%s.unitsOrderedB2B", field)).as("totalUnitsOrderedB2B")
                        .sum(String.format("%s.unitsRefunded", field)).as("totalUnitsRefunded")
                        .sum(String.format("%s.unitsShipped", field)).as("totalUnitsShipped")
                        .sum(String.format("%s.ordersShipped", field)).as("totalOrdersShipped")
        );
    }

    private SummaryStatisticsBaseDTO getSummaryStatisticsDTO(AggregationResults<? extends SummaryStatisticsBaseDTO> results) {
        return Optional.ofNullable(results.getUniqueMappedResult())
                .orElseThrow(StatisticsNotFoundException::new);
    }

}
