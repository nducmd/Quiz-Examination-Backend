package com.bdgh.examsystem.service;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import com.bdgh.examsystem.dto.Student.StudentDetailsDto;
import com.bdgh.examsystem.dto.Student.StudentSummaryDto;

public interface StudentService {
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    StudentSummaryDto editStudent(Long id, StudentDetailsDto studentDetailsDto);

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    Page<StudentSummaryDto> getPageStudentSummary(int page, int size);

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    Page<StudentSummaryDto> getStudentByKeyword(String keyword, int page);

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    void delete(Long id);

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    StudentDetailsDto findStudentById(Long id);

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    StudentDetailsDto getMyInfo();
}
