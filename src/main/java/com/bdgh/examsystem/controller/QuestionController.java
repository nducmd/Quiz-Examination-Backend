package com.bdgh.examsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bdgh.examsystem.dto.Question.QuestionDetailsDto;
import com.bdgh.examsystem.dto.Question.QuestionSummaryDto;
import com.bdgh.examsystem.entity.ResponseObject;
import com.bdgh.examsystem.service.QuestionService;

@RestController
@RequestMapping("/api/v1/questions")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/all/{id}")
    public ResponseEntity<ResponseObject> getAllQuestions(@PathVariable("id") Long id) {

        List<QuestionSummaryDto> questions = questionService.findAllByExamId(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("ok", "Tìm danh sách câu hỏi thành công", questions));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getQuestionById(@PathVariable("id") Long id) {

        QuestionDetailsDto questionDetailsDto = questionService.findById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("ok", "Tìm câu hỏi thành công", questionDetailsDto));
    }

    @PostMapping("")
    public ResponseEntity<ResponseObject> createQuestion(@RequestBody QuestionDetailsDto questionDetailsDTO) {

        QuestionDetailsDto questionDetailsDto1 = questionService.add(questionDetailsDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("ok", "Tạo câu hỏi thành công", questionDetailsDto1));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateQuestion(
            @PathVariable("id") Long id, @RequestBody QuestionDetailsDto updatedQuestionDetailsDto) {

        QuestionDetailsDto updatedQuestion = questionService.update(id, updatedQuestionDetailsDto);
        return ResponseEntity.ok(new ResponseObject("ok", "Cập nhật thành công", updatedQuestion));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteQuestion(@PathVariable("id") Long id) {

        questionService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Xóa thành công", null));
    }
}
