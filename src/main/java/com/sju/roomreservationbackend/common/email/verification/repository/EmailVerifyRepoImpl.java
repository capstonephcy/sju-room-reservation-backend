package com.sju.roomreservationbackend.common.email.verification.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static com.sju.roomreservationbackend.common.email.verification.entity.QEmailVerifyLog.emailVerifyLog;


@Repository
@AllArgsConstructor
public class EmailVerifyRepoImpl implements EmailVerifyDslRepo {
    private JPAQueryFactory queryFactory;

    @Override
    public void deleteExpiredLog() {
        queryFactory.delete(emailVerifyLog)
                .where(emailVerifyLog.verified.isFalse().and(emailVerifyLog.validUntil.before(LocalDateTime.now(ZoneId.of("Asia/Seoul")))))
                .execute();
    }
}
