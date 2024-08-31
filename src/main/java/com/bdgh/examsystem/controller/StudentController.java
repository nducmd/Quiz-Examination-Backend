package com.bdgh.examsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bdgh.examsystem.dto.Student.StudentDetailsDto;
import com.bdgh.examsystem.dto.Student.StudentSummaryDto;
import com.bdgh.examsystem.entity.ResponseObject;
import com.bdgh.examsystem.service.StudentService;

@RestController
@RequestMapping("/api/v1/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/find")
    ResponseEntity<ResponseObject> find(
            @RequestParam(value = "keyword", defaultValue = "") String keyword,
            @RequestParam(value = "page", defaultValue = "0") int page) {

        Page<StudentSummaryDto> summaryDtoPage = studentService.getStudentByKeyword(keyword, page);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("ok", "Tìm sinh viên thành công", summaryDtoPage));
    }

    @GetMapping("/all")
    ResponseEntity<ResponseObject> getAllStudent(@RequestParam(value = "page", defaultValue = "0") int page) {

        Page<StudentSummaryDto> summaryDtoPage = studentService.getPageStudentSummary(page, 10);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("ok", "Lấy danh sách sinh viên thành công", summaryDtoPage));
    }

    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> editStudent(
            @RequestBody StudentDetailsDto studentDetailsDto, @PathVariable Long id) {

        StudentSummaryDto studentSummaryDto = studentService.editStudent(id, studentDetailsDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("ok", "Sửa thông tin sinh viên thành công", studentSummaryDto));
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> findStudent(@PathVariable Long id) {

        StudentDetailsDto studentDetailsDto = studentService.findStudentById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("ok", "Lấy thông tin sinh viên thành công", studentDetailsDto));
    }

    @GetMapping("/myInfo")
    ResponseEntity<ResponseObject> getStudentInfo() {

        StudentDetailsDto studentDetailsDto = studentService.getMyInfo();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("ok", "Lấy thông tin sinh viên thành công", studentDetailsDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteStudent(@PathVariable("id") Long id) {

        studentService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Xoá sinh viên thành công", null));
    }
}
