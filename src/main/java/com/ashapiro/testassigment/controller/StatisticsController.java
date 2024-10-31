package com.ashapiro.testassigment.controller;

import com.ashapiro.testassigment.dto.SummaryStatisticsBaseDTO;
import com.ashapiro.testassigment.model.SalesAndTrafficByAsin;
import com.ashapiro.testassigment.model.SalesAndTrafficByDate;
import com.ashapiro.testassigment.service.StatisticsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/statistics")
@AllArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/date-range")
    public List<SalesAndTrafficByDate> getStatisticsByDateRange(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate
    ) {
        return statisticsService.getStatisticsByDateRange(startDate, endDate);
    }

    @GetMapping("/date")
    public List<SalesAndTrafficByDate> getStatisticsByDate(@RequestParam LocalDate date) {
        return statisticsService.getStatisticsByDate(date);
    }

    @GetMapping("/asin")
    public SalesAndTrafficByAsin getStatisticsByDate(@RequestParam String asin) {
        return statisticsService.getStatisticsByAsin(asin);
    }

    @GetMapping("/asins")
    public List<SalesAndTrafficByAsin> getStatisticsByDateRange(@RequestParam List<String> asins) {
        return statisticsService.getStatisticsByListOfAsins(asins);
    }

    @GetMapping("/summary/dates")
    public SummaryStatisticsBaseDTO getSummaryStatisticsByDates() {
        return statisticsService.getSummaryDateStatistics();
    }

    @GetMapping("/summary/asins")
    public SummaryStatisticsBaseDTO getSummaryStatisticsByAsins() {
        return statisticsService.getSummaryAsinStatistics();
    }
}
