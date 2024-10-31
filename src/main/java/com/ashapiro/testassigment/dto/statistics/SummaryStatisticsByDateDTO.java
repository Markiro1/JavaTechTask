package com.ashapiro.testassigment.dto.statistics;

import com.ashapiro.testassigment.dto.SummaryStatisticsBaseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class SummaryStatisticsByDateDTO extends SummaryStatisticsBaseDTO {
    private Integer totalUnitsRefunded;
    private Integer totalUnitsShipped;
    private Integer totalOrdersShipped;
}
