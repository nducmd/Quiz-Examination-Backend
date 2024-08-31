package com.bdgh.examsystem.config;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bdgh.examsystem.entity.InvalidatedToken;
import com.bdgh.examsystem.repository.InvalidatedTokenRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ScheduledTask {

    @Autowired
    private InvalidatedTokenRepository invalidatedTokenRepository;

    @Scheduled(fixedRate = 600000) // 10 phút
    public void cancelUnpaidOrders() {
        LocalDateTime tenMinutesAgo = LocalDateTime.now().minusMinutes(10);
        List<InvalidatedToken> tokenList = invalidatedTokenRepository.findAll();
        tokenList.forEach(token -> {
            if (token.getExpiredTime().before(new Date())) {
                invalidatedTokenRepository.delete(token);
            }
            log.info("Xoá token ID {} do hết hạn.", token.getId());
        });
    }
}
