package com.bdgh.examsystem.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.bdgh.examsystem.dto.Student.StudentDetailsDto;
import com.bdgh.examsystem.dto.Student.StudentSummaryDto;
import com.bdgh.examsystem.entity.Student;
import com.bdgh.examsystem.exception.NotFoundException;
import com.bdgh.examsystem.repository.StudentRepository;
import com.bdgh.examsystem.service.Impl.StudentServiceImpl;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    @Mock
    private ConvertToDtoService toDtoService;

    private Student student;
    private StudentDetailsDto studentDetailsDto;
    private StudentSummaryDto studentSummaryDto;

    @BeforeEach
    void setUp() {
        student = Student.builder()
                .id(1L)
                .ho("Nguyen")
                .ten("A")
                .lop("D21")
                .dob(LocalDate.now())
                .msv("B21")
                .diemTrungBinh(9.0)
                .build();

        studentDetailsDto = StudentDetailsDto.builder()
                .ho("Ngoc")
                .ten("B")
                .lop("D22")
                .dob(LocalDate.now())
                .msv("B22")
                .build();

        studentSummaryDto = StudentSummaryDto.builder()
                .id(1L)
                .ho("Ngoc")
                .ten("B")
                .lop("D22")
                .dob(LocalDate.now())
                .msv("B22")
                .diemTrungBinh(9.0)
                .build();
    }

    @Test
    void testEditStudent_Success() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(toDtoService.toStudentSummaryDto(any(Student.class))).thenReturn(studentSummaryDto);

        StudentSummaryDto result = studentService.editStudent(1L, studentDetailsDto);

        // Bắt đối tượng updated student, kiểm tra xem có được update không
        ArgumentCaptor<Student> studentCaptor = ArgumentCaptor.forClass(Student.class);
        // verify(studentRepository, times(1)).save(studentCaptor.capture());
        verify(toDtoService).toStudentSummaryDto(studentCaptor.capture());
        Student savedStudent = studentCaptor.getValue();

        assertThat(savedStudent.getHo()).isEqualTo(studentDetailsDto.getHo());
        assertThat(savedStudent.getTen()).isEqualTo(studentDetailsDto.getTen());
        assertThat(savedStudent.getDob()).isEqualTo(studentDetailsDto.getDob());
        assertThat(savedStudent.getLop()).isEqualTo(studentDetailsDto.getLop());
        assertThat(savedStudent.getMsv()).isEqualTo(studentDetailsDto.getMsv());

        verify(studentRepository, times(1)).findById(1L);
        verify(studentRepository, times(1)).save(student);
        verify(toDtoService, times(1)).toStudentSummaryDto(student);
    }

    @Test
    void testEditStudent_NotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        // Mong đợi thrown ngoại lệ
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            studentService.editStudent(1L, studentDetailsDto);
        });

        assertThat(exception.getMessage()).isEqualTo("Không có sinh viên với id: 1");

        verify(studentRepository, times(1)).findById(1L);
        verify(studentRepository, times(0)).save(any(Student.class));
        verify(toDtoService, times(0)).toStudentSummaryDto(any(Student.class));
    }
}
