package com.ashapiro.testassigment.util;

import com.ashapiro.testassigment.dto.statistics.StatisticsDTO;
import com.ashapiro.testassigment.model.ReportSpecification;
import com.ashapiro.testassigment.model.SalesAndTrafficByAsin;
import com.ashapiro.testassigment.model.SalesAndTrafficByDate;
import com.ashapiro.testassigment.repository.ReportSpecificationRepo;
import com.ashapiro.testassigment.repository.SalesAndTrafficByAsinRepo;
import com.ashapiro.testassigment.repository.SalesAndTrafficByDateRepo;
import com.ashapiro.testassigment.repository.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer {

    private final ReportSpecificationRepo reportSpecificationRepo;

    private final SalesAndTrafficByAsinRepo salesAndTrafficByAsinRepo;

    private final SalesAndTrafficByDateRepo salesAndTrafficByDateRepo;

    private final UserRepository userRepository;

    private final ObjectMapper objectMapper;
    private final File file = new File("C:\\Users\\65sum\\Desktop\\test_report.json");


    @PostConstruct
    public void init() {
        initializeDataBase();
    }

    @Scheduled(fixedRate = 600_000)
    public void scheduledUpdate() {
        log.info("Update all statistics");
        updateData();
    }

    private void updateData() {
        StatisticsDTO fileDTO = readFileData();
        if (fileDTO == null) return;

        updateReportSpecification(fileDTO.getReportSpecification());
        updateSalesAndTrafficByAsinData(fileDTO.getSalesAndTrafficByAsin());
        updateSalesAndTrafficByDateData(fileDTO.getSalesAndTrafficByDate());
    }

    private StatisticsDTO readFileData() {
        try {
            return objectMapper.readValue(file, new TypeReference<>() {});
        } catch (IOException e) {
            log.error("Error reading file data: {}", e.getMessage());
            return null;
        }
    }

    private void updateReportSpecification(ReportSpecification fileSpec) {
        var dbSpecs = reportSpecificationRepo.findAll();
        if (dbSpecs.stream().noneMatch(dbSpec -> dbSpec.equals(fileSpec))) {
            reportSpecificationRepo.save(fileSpec);
        }
    }

    private void updateSalesAndTrafficByAsinData(List<SalesAndTrafficByAsin> fileDataList) {
        List<SalesAndTrafficByAsin> dbDataList = salesAndTrafficByAsinRepo.findAll();
        updateSalesAndTrafficByAsinData(dbDataList, fileDataList);
    }

    private void updateSalesAndTrafficByDateData(List<SalesAndTrafficByDate> fileDataList) {
        List<SalesAndTrafficByDate> dbDataList = salesAndTrafficByDateRepo.findAll();
        updateSalesAndTrafficByDateData(dbDataList, fileDataList);
    }

    private void updateSalesAndTrafficByDateData(List<SalesAndTrafficByDate> dbDataList, List<SalesAndTrafficByDate> fileDataList) {
        fileDataList.forEach(fileData -> dbDataList.stream()
                .filter(dbData -> dbData.getDate().equals(fileData.getDate()))
                .findFirst()
                .ifPresentOrElse(
                        dbData -> {
                            if (!dbData.equals(fileData)) {
                                salesAndTrafficByDateRepo.delete(dbData);
                                salesAndTrafficByDateRepo.save(fileData);
                            }
                        },
                        () -> salesAndTrafficByDateRepo.save(fileData)
                ));
    }

    private void updateSalesAndTrafficByAsinData(List<SalesAndTrafficByAsin> dbDataList, List<SalesAndTrafficByAsin> fileDataList) {
        fileDataList.forEach(fileData -> dbDataList.stream()
                .filter(dbData -> dbData.getParentAsin().equals(fileData.getParentAsin()))
                .findFirst()
                .ifPresentOrElse(
                        dbData -> {
                            if (!dbData.equals(fileData)) {
                                salesAndTrafficByAsinRepo.delete(dbData);
                                salesAndTrafficByAsinRepo.save(fileData);
                            }
                        },
                        () -> salesAndTrafficByAsinRepo.save(fileData)
                ));
    }
    private void initializeDataBase() {
        StatisticsDTO dto = readFileData();
        if (dto == null) return;

        userRepository.deleteAll();
        reportSpecificationRepo.deleteAll();
        salesAndTrafficByAsinRepo.deleteAll();
        salesAndTrafficByDateRepo.deleteAll();

        reportSpecificationRepo.save(dto.getReportSpecification());
        salesAndTrafficByAsinRepo.saveAll(dto.getSalesAndTrafficByAsin());
        salesAndTrafficByDateRepo.saveAll(dto.getSalesAndTrafficByDate());
    }
}
