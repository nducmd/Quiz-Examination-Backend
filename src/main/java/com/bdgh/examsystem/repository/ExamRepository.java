package com.bdgh.examsystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bdgh.examsystem.entity.Exam;
import com.bdgh.examsystem.entity.ExamType;

public interface ExamRepository extends JpaRepository<Exam, Long> {
    Page<Exam> findByExamType(ExamType examType, Pageable pageable);
}
