package com.bdgh.examsystem.dto.Student;

import java.time.LocalDate;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentSummaryDto {
    Long id;
    String ho;
    String ten;
    String msv;
    String lop;
    double diemTrungBinh;
    LocalDate dob;
}
