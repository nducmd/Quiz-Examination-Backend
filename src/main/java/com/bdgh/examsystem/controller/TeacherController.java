package com.bdgh.examsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bdgh.examsystem.dto.Teacher.TeacherDetailsDto;
import com.bdgh.examsystem.dto.Teacher.TeacherSummaryDto;
import com.bdgh.examsystem.entity.ResponseObject;
import com.bdgh.examsystem.service.TeacherService;

@RestController
@RequestMapping("/api/v1/teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @GetMapping("/all")
    ResponseEntity<ResponseObject> getAllTeacher(@RequestParam(value = "page", defaultValue = "0") int page) {

        Page<TeacherSummaryDto> summaryDtoPage = teacherService.getPageTeacherSummary(page, 10);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("ok", "Lấy danh sách giáo viên thành công", summaryDtoPage));
    }

    @GetMapping("/myInfo")
    ResponseEntity<ResponseObject> getMyInfo() {

        TeacherDetailsDto teacherDetailsDto = teacherService.getMyInfo();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("ok", "Lấy thông tin sách giáo viên thành công", teacherDetailsDto));
    }

    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> editTeacher(
            @RequestBody TeacherDetailsDto teacherDetailsDto, @PathVariable Long id) {

        TeacherSummaryDto teacherSummaryDto = teacherService.editTeacher(id, teacherDetailsDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("ok", "Sửa thông tin giáo viên thành công", teacherSummaryDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteTeacher(@PathVariable("id") Long id) {

        teacherService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Xoá sinh viên thành công", null));
    }
}
