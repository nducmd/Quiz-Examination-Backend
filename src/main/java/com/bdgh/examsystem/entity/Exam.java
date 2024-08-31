package com.bdgh.examsystem.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "exam")
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "ten")
    String ten;

    @Column(name = "ngay_bat_dau")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate ngayBatDau;

    @Column(name = "gio_bat_dau")
    String gioBatDau;

    @Column(name = "ngay_ket_thuc")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate ngayKetThuc;

    @Column(name = "gio_ket_thuc")
    String gioKetThuc;

    @Column(name = "password")
    String password;

    @Column(name = "exam_type")
    @Enumerated(EnumType.STRING)
    ExamType examType;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    Teacher teacher;

    @OneToMany(mappedBy = "exam", cascade = CascadeType.REMOVE)
    List<Result> resultList;

    @OneToMany(mappedBy = "exam", cascade = CascadeType.REMOVE)
    List<Question> questionList;
}
