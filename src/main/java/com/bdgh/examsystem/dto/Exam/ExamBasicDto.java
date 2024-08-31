package com.bdgh.examsystem.dto.Exam;

import com.bdgh.examsystem.entity.ExamType;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExamBasicDto {
    Long id;
    String ten;
    ExamType examType;
}
