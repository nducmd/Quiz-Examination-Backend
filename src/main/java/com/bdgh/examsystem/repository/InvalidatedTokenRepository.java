package com.bdgh.examsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bdgh.examsystem.entity.InvalidatedToken;

public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {}
