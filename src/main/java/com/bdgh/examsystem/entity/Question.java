package com.bdgh.examsystem.entity;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String cauHoi;
    String dapAn1;
    String dapAn2;
    String dapAn3;
    String dapAn4;
    Long dapAnDung;

    @ManyToOne
    @JoinColumn(name = "exam_id")
    Exam exam;
}
