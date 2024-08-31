package com.bdgh.examsystem.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdgh.examsystem.dto.Question.QuestionDetailsDto;
import com.bdgh.examsystem.dto.Question.QuestionSummaryDto;
import com.bdgh.examsystem.entity.Exam;
import com.bdgh.examsystem.entity.Question;
import com.bdgh.examsystem.exception.NoContentException;
import com.bdgh.examsystem.exception.NotFoundException;
import com.bdgh.examsystem.repository.ExamRepository;
import com.bdgh.examsystem.repository.QuestionRepository;
import com.bdgh.examsystem.service.ConvertToDtoService;
import com.bdgh.examsystem.service.QuestionService;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private ConvertToDtoService convertToDtoService;

    @Override
    public List<QuestionSummaryDto> findAllByExamId(Long id) {
        Exam exam =
                examRepository.findById(id).orElseThrow(() -> new NotFoundException("Không có kì thi với id: " + id));
        if (exam.getQuestionList().isEmpty())
            throw new NoContentException("Không tìm thấy câu hỏi nào trong kì thi: " + id);
        return convertToDtoService.toQuestionSummaryDtoList(exam.getQuestionList());
    }

    @Override
    public QuestionDetailsDto findById(Long id) {
        Question question = questionRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Không có câu hỏi với id: " + id));
        return convertToDtoService.convertQuestionToDto(question);
    }

    @Override
    public QuestionDetailsDto add(QuestionDetailsDto questionDetailsDTO) {
        Exam exam = examRepository
                .findById(questionDetailsDTO.getExam().getId())
                .orElseThrow(() -> new NotFoundException("Không có kì thi với id: "
                        + questionDetailsDTO.getExam().getId()));
        Question question = Question.builder()
                .cauHoi(questionDetailsDTO.getCauHoi())
                .dapAn1(questionDetailsDTO.getDapAn1())
                .dapAn2(questionDetailsDTO.getDapAn2())
                .dapAn3(questionDetailsDTO.getDapAn3())
                .dapAn4(questionDetailsDTO.getDapAn4())
                .dapAnDung(questionDetailsDTO.getDapAnDung())
                .exam(exam)
                .build();
        return convertToDtoService.convertQuestionToDto(questionRepository.save(question));
    }

    @Override
    public QuestionDetailsDto update(Long id, QuestionDetailsDto questionDetailsDTO) {
        Question question = questionRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Không có câu hỏi với id: " + id));
        Exam exam = examRepository
                .findById(questionDetailsDTO.getExam().getId())
                .orElseThrow(() -> new NotFoundException("Không có kì thi với id: "
                        + questionDetailsDTO.getExam().getId()));

        question.setCauHoi(questionDetailsDTO.getCauHoi());
        question.setDapAn1(questionDetailsDTO.getDapAn1());
        question.setDapAn2(questionDetailsDTO.getDapAn2());
        question.setDapAn3(questionDetailsDTO.getDapAn3());
        question.setDapAn4(questionDetailsDTO.getDapAn4());
        question.setDapAnDung(questionDetailsDTO.getDapAnDung());
        question.setExam(exam);
        return convertToDtoService.convertQuestionToDto(questionRepository.save(question));
    }

    @Override
    public void deleteById(Long id) {
        Question question = questionRepository.findById(id).orElse(null);
        if (question == null) return;
        questionRepository.deleteById(id);
    }
}
