package com.bdgh.examsystem.dto.Exam;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.bdgh.examsystem.dto.Question.QuestionSummaryDto;
import com.bdgh.examsystem.dto.Result.ResultSummaryDto;
import com.bdgh.examsystem.dto.Teacher.TeacherBasicDto;
import com.bdgh.examsystem.entity.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExamDetailsDto {
    Long id;
    String ten;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate ngayBatDau;

    String gioBatDau;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate ngayKetThuc;

    String password;
    String gioKetThuc;
    ExamType examType;
    TeacherBasicDto teacher;
    List<ResultSummaryDto> resultList;
    List<QuestionSummaryDto> questionList;
}
