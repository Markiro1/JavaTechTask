package com.ashapiro.testassigment.model;

import com.ashapiro.testassigment.model.report.ReportOptions;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Document
@Getter
@Setter
@EqualsAndHashCode
public class ReportSpecification implements Serializable {

    @Id
    private String id;

    private String reportType;

    private ReportOptions reportOptions;

    private LocalDate dataStartTime;

    private LocalDate dataEndTime;

    private List<String> marketplaceIds;
}
