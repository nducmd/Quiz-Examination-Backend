package com.bdgh.examsystem.service.Impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.bdgh.examsystem.dto.Exam.ExamBasicDto;
import com.bdgh.examsystem.dto.Exam.ExamDetailsDto;
import com.bdgh.examsystem.dto.Exam.ExamSummaryDto;
import com.bdgh.examsystem.dto.Question.QuestionDetailsDto;
import com.bdgh.examsystem.dto.Question.QuestionSummaryDto;
import com.bdgh.examsystem.dto.Result.ResultDetailsDto;
import com.bdgh.examsystem.dto.Result.ResultSummaryDto;
import com.bdgh.examsystem.dto.Student.StudentBasicDto;
import com.bdgh.examsystem.dto.Student.StudentDetailsDto;
import com.bdgh.examsystem.dto.Student.StudentSummaryDto;
import com.bdgh.examsystem.dto.Teacher.TeacherBasicDto;
import com.bdgh.examsystem.dto.Teacher.TeacherDetailsDto;
import com.bdgh.examsystem.dto.Teacher.TeacherSummaryDto;
import com.bdgh.examsystem.entity.Exam;
import com.bdgh.examsystem.entity.Question;
import com.bdgh.examsystem.entity.Result;
import com.bdgh.examsystem.entity.Student;
import com.bdgh.examsystem.entity.Teacher;
import com.bdgh.examsystem.service.ConvertToDtoService;

@Service
public class ConvertToDtoServiceImpl implements ConvertToDtoService {

    @Override
    public TeacherDetailsDto toTeacherDetailsDto(Teacher teacher) {
        TeacherDetailsDto teacherDetailsDto = TeacherDetailsDto.builder()
                .id(teacher.getId())
                .ho(teacher.getHo())
                .ten(teacher.getTen())
                .dob(teacher.getDob() != null ? teacher.getDob().toString() : "")
                .title(teacher.getTitle())
                .examList(toExamBasicDtoList(teacher.getExamList()))
                .build();
        return teacherDetailsDto;
    }

    @Override
    public TeacherSummaryDto toTeacherSummaryDto(Teacher teacher) {
        TeacherSummaryDto teacherSummaryDto = TeacherSummaryDto.builder()
                .id(teacher.getId())
                .ho(teacher.getHo())
                .ten(teacher.getTen())
                .dob(teacher.getDob() != null ? teacher.getDob().toString() : "")
                .title(teacher.getTitle())
                .build();
        return teacherSummaryDto;
    }

    @Override
    public TeacherBasicDto toTeacherBasicDto(Teacher teacher) {
        return TeacherBasicDto.builder()
                .id(teacher.getId())
                .ho(teacher.getHo())
                .ten(teacher.getTen())
                .title(teacher.getTitle())
                .build();
    }

    @Override
    public Page<TeacherSummaryDto> toTeacherSummaryPage(Page<Teacher> teacherPage) {
        return teacherPage.map(this::toTeacherSummaryDto);
    }

    @Override
    public ResultDetailsDto toResultDetailsDto(Result result) {
        return ResultDetailsDto.builder()
                .id((result.getId()))
                .soCauDung(result.getSoCauDung())
                .batDau(result.getBatDau() != null ? result.getBatDau().toString() : "")
                .nopBai(result.getNopBai() != null ? result.getNopBai().toString() : "")
                .studentAnswer(result.getStudentAnswer())
                .exam(result.getExam() != null ? toExamBasicDto(result.getExam()) : null)
                .student(result.getStudent() != null ? toStudentBasicDto(result.getStudent()) : null)
                //                .examId(result.getExam() != null ? result.getExam().getId() : null)
                //                .studentId(result.getStudent() != null ? result.getStudent().getId() :
                // null)
                .build();
    }

    @Override
    public ResultSummaryDto toResultSummaryDto(Result result) {
        ResultSummaryDto resultSummaryDto = new ResultSummaryDto();
        resultSummaryDto.setId(result.getId());
        resultSummaryDto.setSoCauDung(result.getSoCauDung());
        resultSummaryDto.setBatDau(result.getBatDau().toString());
        resultSummaryDto.setNopBai(result.getNopBai().toString());
        resultSummaryDto.setStudentAnswer(result.getStudentAnswer());
        return resultSummaryDto;
    }

    @Override
    public List<ResultSummaryDto> toResultSummaryDtoList(List<Result> resultList) {
        return resultList.stream().map(this::toResultSummaryDto).collect(Collectors.toList());
    }

    @Override
    public ExamDetailsDto toExamDetailsDto(Exam exam) {
        ExamDetailsDto examDetailsDTO = new ExamDetailsDto();
        examDetailsDTO.setId(exam.getId());
        examDetailsDTO.setTen(exam.getTen());
        examDetailsDTO.setNgayBatDau(exam.getNgayBatDau());
        examDetailsDTO.setGioBatDau(exam.getGioBatDau());
        examDetailsDTO.setNgayKetThuc(exam.getNgayKetThuc());
        examDetailsDTO.setGioKetThuc(exam.getGioKetThuc());
        examDetailsDTO.setPassword(exam.getPassword());
        examDetailsDTO.setExamType(exam.getExamType());
        examDetailsDTO.setTeacher(toTeacherBasicDto(exam.getTeacher()));
        examDetailsDTO.setResultList(toResultSummaryDtoList(exam.getResultList()));
        examDetailsDTO.setQuestionList(toQuestionSummaryDtoList(exam.getQuestionList()));
        return examDetailsDTO;
    }

    @Override
    public ExamBasicDto toExamBasicDto(Exam exam) {
        return ExamBasicDto.builder()
                .id(exam.getId())
                .ten(exam.getTen())
                .examType(exam.getExamType())
                .build();
    }

    @Override
    public ExamSummaryDto toExamSummaryDto(Exam exam) {
        return ExamSummaryDto.builder()
                .id(exam.getId())
                .ten(exam.getTen())
                .ngayBatDau(exam.getNgayBatDau())
                .gioBatDau(exam.getGioBatDau())
                .ngayKetThuc(exam.getNgayKetThuc())
                .gioKetThuc(exam.getGioKetThuc())
                .password(exam.getPassword())
                .examType(exam.getExamType())
                .teacher(toTeacherBasicDto(exam.getTeacher()))
                .build();
    }

    @Override
    public List<ExamSummaryDto> toExamSummaryDtoList(List<Exam> examList) {
        return examList.stream().map(this::toExamSummaryDto).collect(Collectors.toList());
    }

    @Override
    public List<ExamBasicDto> toExamBasicDtoList(List<Exam> examList) {
        return examList.stream().map(this::toExamBasicDto).collect(Collectors.toList());
    }

    @Override
    public Page<ExamSummaryDto> toExamSummaryDtoPage(Page<Exam> examPage) {
        return examPage.map(this::toExamSummaryDto);
    }

    @Override
    public QuestionDetailsDto convertQuestionToDto(Question question) {
        QuestionDetailsDto questionDetailsDto = new QuestionDetailsDto();
        questionDetailsDto.setId(question.getId());
        questionDetailsDto.setCauHoi(question.getCauHoi());
        questionDetailsDto.setDapAn1(question.getDapAn1());
        questionDetailsDto.setDapAn2(question.getDapAn2());
        questionDetailsDto.setDapAn3(question.getDapAn3());
        questionDetailsDto.setDapAn4(question.getDapAn4());
        questionDetailsDto.setDapAnDung(question.getDapAnDung());
        questionDetailsDto.setExam(toExamBasicDto(question.getExam()));
        return questionDetailsDto;
    }

    @Override
    public List<QuestionDetailsDto> convertQuestionListToDto(List<Question> questionList) {
        List<QuestionDetailsDto> questionDetailsDtoList =
                questionList.stream().map(this::convertQuestionToDto).collect(Collectors.toList());
        return questionDetailsDtoList;
    }

    @Override
    public QuestionSummaryDto toQuestionSummaryDto(Question question) {
        QuestionSummaryDto questionSummaryDto = new QuestionSummaryDto();
        questionSummaryDto.setId(question.getId());
        questionSummaryDto.setCauHoi(question.getCauHoi());
        questionSummaryDto.setDapAn1(question.getDapAn1());
        questionSummaryDto.setDapAn2(question.getDapAn2());
        questionSummaryDto.setDapAn3(question.getDapAn3());
        questionSummaryDto.setDapAn4(question.getDapAn4());
        questionSummaryDto.setDapAnDung(question.getDapAnDung());
        return questionSummaryDto;
    }

    @Override
    public List<QuestionSummaryDto> toQuestionSummaryDtoList(List<Question> questionList) {
        return questionList.stream().map(this::toQuestionSummaryDto).collect(Collectors.toList());
    }

    @Override
    public StudentDetailsDto toStudentDetailsDto(Student student) {
        if (student == null) {
            return null;
        }
        LocalDate dob =
                student.getDob() != null ? LocalDate.parse(student.getDob().toString()) : null;
        StudentDetailsDto studentDetailsDto = StudentDetailsDto.builder()
                .id(student.getId())
                .diemTrungBinh(student.getDiemTrungBinh())
                .dob(dob)
                .ho(student.getHo())
                .lop(student.getLop())
                .msv(student.getMsv())
                .ten(student.getTen())
                .results(toResultSummaryDtoList(student.getResults()))
                .build();
        return studentDetailsDto;
    }

    @Override
    public StudentSummaryDto toStudentSummaryDto(Student student) {
        if (student == null) {
            return null;
        }
        LocalDate dob =
                student.getDob() != null ? LocalDate.parse(student.getDob().toString()) : null;
        StudentSummaryDto studentSummaryDto = StudentSummaryDto.builder()
                .id(student.getId())
                .diemTrungBinh(student.getDiemTrungBinh())
                .dob(dob)
                .ho(student.getHo())
                .lop(student.getLop())
                .msv(student.getMsv())
                .ten(student.getTen())
                .build();
        return studentSummaryDto;
    }

    @Override
    public StudentBasicDto toStudentBasicDto(Student student) {
        return StudentBasicDto.builder()
                .id(student.getId())
                .ho(student.getHo())
                .ten(student.getTen())
                .msv(student.getMsv())
                .build();
    }

    @Override
    public Page<StudentSummaryDto> toStudentSummaryPage(Page<Student> studentPage) {
        return studentPage.map(this::toStudentSummaryDto);
    }
}
