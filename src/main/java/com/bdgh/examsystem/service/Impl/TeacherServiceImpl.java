package com.bdgh.examsystem.service.Impl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.bdgh.examsystem.dto.Teacher.TeacherDetailsDto;
import com.bdgh.examsystem.dto.Teacher.TeacherSummaryDto;
import com.bdgh.examsystem.entity.Teacher;
import com.bdgh.examsystem.entity.User;
import com.bdgh.examsystem.exception.NoContentException;
import com.bdgh.examsystem.exception.NotFoundException;
import com.bdgh.examsystem.repository.TeacherRepository;
import com.bdgh.examsystem.repository.UserRepository;
import com.bdgh.examsystem.service.ConvertToDtoService;
import com.bdgh.examsystem.service.TeacherService;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConvertToDtoService convertToDtoService;

    @Override
    public TeacherSummaryDto editTeacher(Long id, TeacherDetailsDto teacherDetailsDto) {
        Teacher teacher = teacherRepository.findById(id).orElse(null);
        if (teacher == null) throw new NotFoundException("Không tồn tại giáo viên với id: " + id);
        teacher.setHo(teacherDetailsDto.getHo());
        teacher.setTen(teacherDetailsDto.getTen());
        teacher.setDob(LocalDate.parse(teacherDetailsDto.getDob()));
        teacher.setTitle(teacherDetailsDto.getTitle());
        return convertToDtoService.toTeacherSummaryDto(teacherRepository.save(teacher));
    }

    @Override
    public Page<TeacherSummaryDto> getPageTeacherSummary(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Teacher> teacherPage = teacherRepository.findAll(pageable);
        if (teacherPage.isEmpty()) throw new NoContentException("Không tìm thấy giáo viên nào");
        return convertToDtoService.toTeacherSummaryPage(teacherPage);
    }

    @Override
    public Teacher findById(Long id) {
        return teacherRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Không tồn tại giáo viên với id: " + id));
    }

    @Override
    public void delete(Long id) {
        Teacher teacher = teacherRepository.findById(id).orElse(null);
        if (teacher == null) return;
        teacherRepository.deleteById(id);
    }

    @Override
    public TeacherDetailsDto getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String email = context.getAuthentication().getName();
        User user =
                userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Không tồn tại người dùng"));
        Teacher teacher =
                teacherRepository.findByUser(user).orElseThrow(() -> new NotFoundException("Không tồn tại giáo viên"));
        return convertToDtoService.toTeacherDetailsDto(teacher);
    }
}
