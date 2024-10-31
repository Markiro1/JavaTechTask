package com.ashapiro.testassigment.model.report;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@EqualsAndHashCode
public class ReportOptions {

    private String dateGranularity;

    private String asinGranularity;
}
