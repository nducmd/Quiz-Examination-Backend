package com.bdgh.examsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bdgh.examsystem.dto.Result.ResultDetailsDto;
import com.bdgh.examsystem.dto.Result.ResultSummaryDto;
import com.bdgh.examsystem.entity.ResponseObject;
import com.bdgh.examsystem.service.ResultService;

@RestController
@RequestMapping("/api/v1/result")
public class ResultController {

    @Autowired
    private ResultService resultService;

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> editResult(
            @RequestBody ResultDetailsDto resultDetailsDto, @PathVariable Long id) {
        ResultDetailsDto resultDetailsDto1 = resultService.editResult(id, resultDetailsDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("ok", "Sửa bài làm thành công", resultDetailsDto1));
    }

    @PostMapping("")
    public ResponseEntity<ResponseObject> createResult(@RequestBody ResultDetailsDto resultDetailsDto) {

        ResultDetailsDto resultDetailsDto1 = resultService.createResult(resultDetailsDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("ok", "Nộp bài thành công", resultDetailsDto1));
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseObject> getAllResult() {

        List<ResultSummaryDto> resultDetailsDtoList = resultService.findAllResult();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("ok", "Lấy danh sách bài làm thành công", resultDetailsDtoList));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getResultById(@PathVariable("id") Long id) {

        ResultDetailsDto resultDetailsDto = resultService.findById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("ok", "Tìm thấy kết quả có id: " + id, resultDetailsDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteResult(@PathVariable("id") Long id) {

        resultService.deleteResultById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Xoá thành công", null));
    }

    @GetMapping("/exam/{examId}")
    public ResponseEntity<ResponseObject> getResultsByExam(@PathVariable Long examId) {

        List<ResultSummaryDto> results = resultService.getResultsByExamId(examId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("ok", "Lấy kết quả làm bài thành công", results));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<ResponseObject> getResultsByStudent(@PathVariable Long studentId) {

        List<ResultSummaryDto> results = resultService.getResultsByStudentId(studentId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("ok", "Lấy danh sách kết quả thành công", results));
    }
}
