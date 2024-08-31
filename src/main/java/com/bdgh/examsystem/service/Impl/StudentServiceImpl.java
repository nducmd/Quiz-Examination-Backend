package com.bdgh.examsystem.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.bdgh.examsystem.dto.Student.StudentDetailsDto;
import com.bdgh.examsystem.dto.Student.StudentSummaryDto;
import com.bdgh.examsystem.entity.Student;
import com.bdgh.examsystem.entity.User;
import com.bdgh.examsystem.exception.NoContentException;
import com.bdgh.examsystem.exception.NotFoundException;
import com.bdgh.examsystem.repository.StudentRepository;
import com.bdgh.examsystem.repository.UserRepository;
import com.bdgh.examsystem.service.ConvertToDtoService;
import com.bdgh.examsystem.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConvertToDtoService toDtoService;

    @Override
    public StudentSummaryDto editStudent(Long id, StudentDetailsDto studentDetailsDto) {
        Student student = studentRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Không có sinh viên với id: " + id));
        student.setHo(studentDetailsDto.getHo());
        student.setTen(studentDetailsDto.getTen());
        student.setDob(studentDetailsDto.getDob());
        student.setLop(studentDetailsDto.getLop());
        student.setMsv(studentDetailsDto.getMsv());
        return toDtoService.toStudentSummaryDto(studentRepository.save(student));
    }

    @Override
    public Page<StudentSummaryDto> getPageStudentSummary(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Student> studentPage = studentRepository.findAll(pageable);
        if (studentPage.isEmpty()) throw new NoContentException("Không tìm thấy sinh viên nào");
        return toDtoService.toStudentSummaryPage(studentPage);
    }

    @Override
    public Page<StudentSummaryDto> getStudentByKeyword(String keyword, int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Student> studentPage = studentRepository.searchByKeyword(keyword, pageable);
        if (studentPage.isEmpty()) throw new NoContentException("Không tìm thấy sinh viên nào");
        return toDtoService.toStudentSummaryPage(studentPage);
    }

    @Override
    public void delete(Long id) {
        Student student = studentRepository.findById(id).orElse(null);
        if (student == null) return;
        studentRepository.deleteById(id);
    }

    @Override
    public StudentDetailsDto findStudentById(Long id) {
        Student student = studentRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Không có sinh viên với id: " + id));
        return toDtoService.toStudentDetailsDto(student);
    }

    @Override
    public StudentDetailsDto getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String email = context.getAuthentication().getName();
        User user =
                userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Không tồn tại người dùng "));
        Student student =
                studentRepository.findByUser(user).orElseThrow(() -> new NotFoundException("Không tồn tại sinh viên"));
        return toDtoService.toStudentDetailsDto(student);
    }
}
