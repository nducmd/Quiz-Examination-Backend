package com.bdgh.examsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bdgh.examsystem.entity.Result;

public interface ResultRepository extends JpaRepository<Result, Long> {
    List<Result> findByExamId(Long examId);

    List<Result> findByStudentId(Long studentId);
}
