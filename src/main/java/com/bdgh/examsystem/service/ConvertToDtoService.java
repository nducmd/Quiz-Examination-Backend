package com.bdgh.examsystem.service;

import java.util.List;

import org.springframework.data.domain.Page;

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
import com.bdgh.examsystem.entity.*;

public interface ConvertToDtoService {
    ExamDetailsDto toExamDetailsDto(Exam exam);

    ExamBasicDto toExamBasicDto(Exam exam);

    ExamSummaryDto toExamSummaryDto(Exam exam);

    List<ExamSummaryDto> toExamSummaryDtoList(List<Exam> examList);

    List<ExamBasicDto> toExamBasicDtoList(List<Exam> examList);

    Page<ExamSummaryDto> toExamSummaryDtoPage(Page<Exam> examPage);

    QuestionDetailsDto convertQuestionToDto(Question question);

    List<QuestionDetailsDto> convertQuestionListToDto(List<Question> questionList);

    QuestionSummaryDto toQuestionSummaryDto(Question question);

    List<QuestionSummaryDto> toQuestionSummaryDtoList(List<Question> questionList);

    TeacherDetailsDto toTeacherDetailsDto(Teacher teacher);

    TeacherSummaryDto toTeacherSummaryDto(Teacher teacher);

    TeacherBasicDto toTeacherBasicDto(Teacher teacher);

    Page<TeacherSummaryDto> toTeacherSummaryPage(Page<Teacher> teacherPage);

    ResultDetailsDto toResultDetailsDto(Result result);

    ResultSummaryDto toResultSummaryDto(Result result);

    List<ResultSummaryDto> toResultSummaryDtoList(List<Result> resultList);

    StudentDetailsDto toStudentDetailsDto(Student student);

    StudentSummaryDto toStudentSummaryDto(Student student);

    StudentBasicDto toStudentBasicDto(Student student);

    Page<StudentSummaryDto> toStudentSummaryPage(Page<Student> studentPage);
}
