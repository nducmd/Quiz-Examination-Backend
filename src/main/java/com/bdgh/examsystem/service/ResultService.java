package com.bdgh.examsystem.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import com.bdgh.examsystem.dto.Exam.ExamDetailsDto;
import com.bdgh.examsystem.dto.Result.ResultDetailsDto;
import com.bdgh.examsystem.dto.Result.ResultSummaryDto;

public interface ResultService {
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    ResultDetailsDto editResult(Long id, ResultDetailsDto resultDetailsDto);

    ResultDetailsDto createResult(ResultDetailsDto resultDetailsDto);

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    List<ResultSummaryDto> findAllResult();

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    ResultDetailsDto findById(Long id);

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    void deleteResultById(Long id);

    void updateSoCauDung(ResultDetailsDto resultDetailsDto, ExamDetailsDto examDetailsDto);

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    List<ResultSummaryDto> getResultsByExamId(Long examId);

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    List<ResultSummaryDto> getResultsByStudentId(Long studentId);
}
