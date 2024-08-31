package com.bdgh.examsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bdgh.examsystem.dto.Exam.ExamDetailsDto;
import com.bdgh.examsystem.dto.Exam.ExamOverviewDto;
import com.bdgh.examsystem.dto.Exam.ExamSummaryDto;
import com.bdgh.examsystem.entity.ExamType;
import com.bdgh.examsystem.entity.ResponseObject;
import com.bdgh.examsystem.service.ExamService;

@RestController
@RequestMapping("/api/v1/exams")
public class ExamController {

    @GetMapping("/statistic/{id}")
    public ResponseEntity<ResponseObject> getOverview(@PathVariable("id") Long id) {

        ExamOverviewDto examOverviewDto = examService.getOverview(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("ok", "Lấy thống kê kì thi thành công", examOverviewDto));
    }

    @GetMapping("/filter")
    public ResponseEntity<ResponseObject> filterExam(
            @RequestParam(value = "type", defaultValue = "CUOIKY") ExamType type,
            @RequestParam(value = "page", defaultValue = "0") int page) {

        Page<ExamSummaryDto> examSummaryDtoList = examService.filterExam(type, page);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("ok", "Lấy danh sách kì thi thành công", examSummaryDtoList));
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseObject> getAllExams() {
        List<ExamSummaryDto> exams = examService.findALL();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("ok", "Lấy danh sách kì thi thành công", exams));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getExamById(@PathVariable("id") Long id) {

        ExamDetailsDto examDetailsDto = examService.findById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("ok", "Tìm kì thi thành công", examDetailsDto));
    }

    @PostMapping("")
    public ResponseEntity<ResponseObject> createExam(@RequestBody ExamDetailsDto examDetailsDTO) {

        ExamSummaryDto examSummaryDto = examService.add(examDetailsDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("ok", "Tạo kì thi mới thành công", examSummaryDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateExam(
            @PathVariable("id") Long id, @RequestBody ExamDetailsDto updatedExamDetailsDto) {

        ExamSummaryDto updatedExam = examService.update(id, updatedExamDetailsDto);
        return ResponseEntity.ok(new ResponseObject("ok", "Cập nhật thành công", updatedExam));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteExam(@PathVariable("id") Long id) {
        examService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Xoá kì thi thành công", null));
    }

    @Autowired
    private ExamService examService;
}
