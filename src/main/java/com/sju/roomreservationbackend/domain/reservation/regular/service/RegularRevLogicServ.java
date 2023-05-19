package com.sju.roomreservationbackend.domain.reservation.regular.service;

import com.sju.roomreservationbackend.common.message.MessageConfig;
import com.sju.roomreservationbackend.domain.reservation.profile.dto.request.CreateReserveReqDTO;
import com.sju.roomreservationbackend.domain.reservation.regular.entity.RegularRev;
import com.sju.roomreservationbackend.domain.reservation.regular.repository.RegularRevRepo;
import com.sju.roomreservationbackend.domain.user.profile.entity.Permission;
import com.sju.roomreservationbackend.domain.user.profile.entity.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RegularRevLogicServ {
    protected final RegularRevRepo regularRevRepo;
    protected final MessageSource reservationMsgSrc = MessageConfig.getReservationMsgSrc();

    protected boolean isRegularRevIsAllowed(UserProfile user, RegularRev regularRev) throws Exception {
        // if user is student, regularRev is not allowed
        if(user.getPermissions().contains(Permission.STUDENT)) {
            return false;
        }

        // else (professor, graduated), regularRev is allowed
        return true;
    }

    protected boolean userNotOwnsRegularRev(UserProfile user, RegularRev regularRev) throws Exception {
        return !user.getId().equals(regularRev.getRevOwner().getId());
    }

    protected List<CreateReserveReqDTO> generateRevReqDtoForRegularRev(RegularRev regularRev, List<Long> attendants) {
        List<CreateReserveReqDTO> revReqDtoList = new ArrayList<>();
        LocalDate repeatDate = regularRev.getDate();

        switch (regularRev.getType()) {
            // if daily of regularRev is true, repeat generate revReqDto for every day
            case DAILY -> {
                for (int i = 0; i < regularRev.getIteration(); i++) {
                    revReqDtoList.add(new CreateReserveReqDTO(
                            regularRev.getRoom().getId(),
                            repeatDate,
                            regularRev.getStart(),
                            regularRev.getEnd(),
                            attendants,
                            regularRev
                    ));
                    repeatDate = repeatDate.plusDays(1);
                }
            }
            // if weekly of regularRev is true, repeat generate revReqDto for every week
            case WEEKLY -> {
                for (int i = 0; i < regularRev.getIteration(); i++) {
                    revReqDtoList.add(new CreateReserveReqDTO(
                            regularRev.getRoom().getId(),
                            repeatDate,
                            regularRev.getStart(),
                            regularRev.getEnd(),
                            attendants,
                            regularRev
                    ));
                    repeatDate = repeatDate.plusWeeks(1);
                }
            }
            // else if monthly of regularRev is true, repeat generate revReqDto for every month
            case MONTHLY -> {
                // calculate date's week of month and day of week
                Calendar calWeek = Calendar.getInstance();
                calWeek.set(Calendar.YEAR, repeatDate.getYear());
                calWeek.set(Calendar.MONTH, repeatDate.getMonth().getValue());
                calWeek.set(Calendar.DAY_OF_MONTH, repeatDate.getDayOfMonth());
                int weekOfMonth = calWeek.get(Calendar.WEEK_OF_MONTH);
                DayOfWeek dayOfWeek = repeatDate.getDayOfWeek();

                for (int i = 0; i < regularRev.getIteration(); i++) {
                    revReqDtoList.add(new CreateReserveReqDTO(
                            regularRev.getRoom().getId(),
                            repeatDate,
                            regularRev.getStart(),
                            regularRev.getEnd(),
                            attendants,
                            regularRev
                    ));
                    // calculate date as weekOfMonth of next month
                    TemporalAdjuster temporalAdjuster = TemporalAdjusters.dayOfWeekInMonth(weekOfMonth, dayOfWeek);
                    // move to next month
                    repeatDate = repeatDate.plusMonths(1);
                    // adjust date as weekOfMonth of next month
                    repeatDate = repeatDate.with( temporalAdjuster );
                }
            }
        }

        return revReqDtoList;
    }
}
