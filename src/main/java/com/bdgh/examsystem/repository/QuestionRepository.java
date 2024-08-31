package com.bdgh.examsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bdgh.examsystem.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {}
