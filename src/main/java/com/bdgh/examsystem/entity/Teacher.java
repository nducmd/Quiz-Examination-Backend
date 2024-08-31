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
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String ho;
    String ten;
    String title;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate dob;

    @OneToOne
    User user;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.REMOVE)
    List<Exam> examList;
}
