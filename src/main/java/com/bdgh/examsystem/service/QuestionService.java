package com.bdgh.examsystem.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import com.bdgh.examsystem.dto.Question.QuestionDetailsDto;
import com.bdgh.examsystem.dto.Question.QuestionSummaryDto;

public interface QuestionService {
    QuestionDetailsDto findById(Long id);

    List<QuestionSummaryDto> findAllByExamId(Long id);

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    QuestionDetailsDto add(QuestionDetailsDto questionDetailsDto);

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    QuestionDetailsDto update(Long id, QuestionDetailsDto questionDetailsDto);

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    void deleteById(Long id);
}
