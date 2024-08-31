package com.bdgh.examsystem.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.bdgh.examsystem.entity.Role;
import com.bdgh.examsystem.entity.Student;
import com.bdgh.examsystem.entity.User;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;
    private Student student;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .email("test@gmail.com")
                .password("password")
                .resetPasswordToken(null)
                .role(Role.STUDENT)
                .build();

        User user1 = userRepository.save(user);

        student = Student.builder()
                .ho("Nguyen")
                .ten("A")
                .lop("D21")
                .dob(LocalDate.now())
                .msv("B21")
                .diemTrungBinh(9.0)
                .user(user1)
                .build();
        studentRepository.save(student);
    }

    @Test
    void testSave() {
        Student newStudent = new Student();
        newStudent.setHo("Nguyen");
        newStudent.setTen("Minh Duc");
        newStudent.setMsv("MSV123456");

        Student savedStudent = studentRepository.save(newStudent);

        assertThat(savedStudent).isNotNull();
        assertThat(savedStudent.getHo()).isEqualTo("Nguyen");
        assertThat(savedStudent.getTen()).isEqualTo("Minh Duc");
        assertThat(savedStudent.getMsv()).isEqualTo("MSV123456");
    }

    @Test
    void testFindAll() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Student> page = studentRepository.findAll(pageable);
        assertThat(page.getTotalElements()).isEqualTo(1);
        assertThat(page.getContent()).containsExactly(student);
    }

    @Test
    void testFindByUser() {
        Student found = studentRepository.findByUser(user).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getUser().getEmail()).isEqualTo("test@gmail.com");
    }

    @Test
    void testSearchByKeyword() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Student> page = studentRepository.searchByKeyword("nguyen", pageable);
        assertThat(page.getContent()).isNotEmpty();
        assertThat(page.getTotalElements()).isEqualTo(1);
        assertThat(page.getContent()).containsExactly(student);
    }
}
