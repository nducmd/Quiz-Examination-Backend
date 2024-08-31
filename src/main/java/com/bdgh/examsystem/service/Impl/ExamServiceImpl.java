package com.bdgh.examsystem.service.Impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bdgh.examsystem.dto.Exam.ExamDetailsDto;
import com.bdgh.examsystem.dto.Exam.ExamOverviewDto;
import com.bdgh.examsystem.dto.Exam.ExamSummaryDto;
import com.bdgh.examsystem.entity.*;
import com.bdgh.examsystem.exception.NoContentException;
import com.bdgh.examsystem.exception.NotFoundException;
import com.bdgh.examsystem.repository.ExamRepository;
import com.bdgh.examsystem.repository.ResultRepository;
import com.bdgh.examsystem.repository.StudentRepository;
import com.bdgh.examsystem.repository.TeacherRepository;
import com.bdgh.examsystem.service.ConvertToDtoService;
import com.bdgh.examsystem.service.ExamService;

@Service
public class ExamServiceImpl implements ExamService {

    @Override
    public ExamOverviewDto getOverview(Long id) {
        Exam exam = examRepository.findById(id).orElse(null);
        if (exam == null) throw new NotFoundException("Không tìm thấy kì thi với id: " + id);
        long diemCaoNhat = 0;
        long diemThapNhat = 10000;
        long diemTrungBinh = 0;
        List<Result> resultList = resultRepository.findByExamId(id);
        if (resultList.isEmpty()) throw new NoContentException("Kì thi không có bài làm nào");
        Set<Long> studentIdSet = new HashSet<>();
        long soCauHoi = exam.getQuestionList().size();
        if (soCauHoi == 0) throw new NoContentException("Kì thi không có câu hỏi nào");
        for (Result r : resultList) {
            if (r.getSoCauDung() > diemCaoNhat) diemCaoNhat = r.getSoCauDung();
            if (r.getSoCauDung() < diemThapNhat) diemThapNhat = r.getSoCauDung();
            diemTrungBinh += r.getSoCauDung();
            studentIdSet.add(r.getStudent().getId());
        }
        long soNguoiThamGia = studentIdSet.size();
        if (soNguoiThamGia == 0) throw new NoContentException("Kì thi không có sinh viên nào");
        long soLuotLamBai = resultList.size();
        if (soLuotLamBai == 0) throw new NoContentException("Kì thi không có lượt làm bài nào");
        return ExamOverviewDto.builder()
                .soNguoiThamGia(soNguoiThamGia)
                .soCauHoi(soCauHoi)
                .soLuotLamBai(soLuotLamBai)
                .diemCaoNhat((10.0 / soCauHoi) * diemCaoNhat)
                .diemThapNhat((10.0 / soCauHoi) * diemThapNhat)
                .diemTrungBinh((10.0 / soCauHoi) * diemTrungBinh / soLuotLamBai)
                .build();
    }

    @Override
    public List<ExamSummaryDto> findALL() {
        List<Exam> examList = examRepository.findAll();
        if (examList.isEmpty()) throw new NoContentException("Không tìm thấy kì thi nào");
        return convertToDtoService.toExamSummaryDtoList(examList);
    }

    @Override
    public ExamDetailsDto findById(Long id) {
        Exam exam =
                examRepository.findById(id).orElseThrow(() -> new NotFoundException("Không có kì thi với id: " + id));
        return convertToDtoService.toExamDetailsDto(exam);
    }

    @Override
    public ExamSummaryDto add(ExamDetailsDto examDetailsDTO) {
        Teacher teacher = teacherRepository
                .findById(examDetailsDTO.getTeacher().getId())
                .orElseThrow(() -> new NotFoundException("Không thể thêm kì thi do giáo viên không tồn tại"));
        Exam exam = Exam.builder()
                .ten(examDetailsDTO.getTen())
                .gioKetThuc(examDetailsDTO.getGioKetThuc())
                .ngayBatDau(examDetailsDTO.getNgayBatDau())
                .gioBatDau(examDetailsDTO.getGioBatDau())
                .ngayKetThuc(examDetailsDTO.getNgayKetThuc())
                .examType(examDetailsDTO.getExamType())
                .password(examDetailsDTO.getPassword())
                .teacher(teacher)
                .build();
        return convertToDtoService.toExamSummaryDto(examRepository.save(exam));
    }

    @Override
    public ExamSummaryDto update(Long id, ExamDetailsDto examDetailsDTO) {
        Exam exam =
                examRepository.findById(id).orElseThrow(() -> new NotFoundException("Không có kì thi với id: " + id));

        Teacher teacher = teacherRepository
                .findById(examDetailsDTO.getTeacher().getId())
                .orElseThrow(() -> new NotFoundException("Không thể sửa kì thi do giáo viên không tồn tại"));

        exam.setTen(examDetailsDTO.getTen());
        exam.setGioKetThuc(examDetailsDTO.getGioKetThuc());
        exam.setNgayBatDau(examDetailsDTO.getNgayBatDau());
        exam.setGioBatDau(examDetailsDTO.getGioBatDau());
        exam.setNgayKetThuc(examDetailsDTO.getNgayKetThuc());
        exam.setExamType(examDetailsDTO.getExamType());
        exam.setPassword(examDetailsDTO.getPassword());
        exam.setTeacher(teacher);
        return convertToDtoService.toExamSummaryDto(examRepository.save(exam));
    }

    @Override
    public void deleteById(Long id) {
        Exam exam = examRepository.findById(id).orElse(null);
        if (exam == null) return;
        examRepository.deleteById(id);
    }

    @Override
    public Page<ExamSummaryDto> filterExam(ExamType type, int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Exam> examList = examRepository.findByExamType(type, pageable);
        if (examList.isEmpty()) throw new NoContentException("Không tìm thấy kì thi nào");
        return convertToDtoService.toExamSummaryDtoPage(examList);
    }

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private ConvertToDtoService convertToDtoService;
}
