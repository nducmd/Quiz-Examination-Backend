package com.bdgh.examsystem.service;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import com.bdgh.examsystem.dto.Teacher.TeacherDetailsDto;
import com.bdgh.examsystem.dto.Teacher.TeacherSummaryDto;
import com.bdgh.examsystem.entity.Teacher;

public interface TeacherService {
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    TeacherSummaryDto editTeacher(Long id, TeacherDetailsDto teacherDetailsDto);

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    Page<TeacherSummaryDto> getPageTeacherSummary(int page, int size);

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    Teacher findById(Long id);

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    void delete(Long id);

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    TeacherDetailsDto getMyInfo();
}
