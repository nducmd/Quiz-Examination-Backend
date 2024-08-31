package com.bdgh.examsystem.dto.Result;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResultSummaryDto {
    Long id;
    Long soCauDung;
    String batDau;
    String nopBai;
    String studentAnswer;
}
