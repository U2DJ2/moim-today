package moim_today.implement.schedule.schedule;

import moim_today.domain.schedule.ScheduleLocalDate;
import moim_today.domain.schedule.ScheduleYearMonth;
import moim_today.dto.schedule.MoimScheduleResponse;
import moim_today.dto.schedule.ScheduleResponse;
import moim_today.global.annotation.Implement;
import moim_today.persistence.entity.schedule.schedule.ScheduleJpaEntity;
import moim_today.persistence.repository.schedule.schedule.ScheduleRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;


@Implement
public class ScheduleFinder {

    private final ScheduleRepository scheduleRepository;

    public ScheduleFinder(final ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Transactional(readOnly = true)
    public List<ScheduleResponse> findAllByWeekly(final long memberId, final LocalDate startDate) {
        ScheduleLocalDate scheduleLocalDate = ScheduleLocalDate.from(startDate);
        LocalDateTime startDateTime = scheduleLocalDate.atWeeklyStartDateTime();
        LocalDateTime endDateTime = scheduleLocalDate.atWeeklyEndDateTime();

        return scheduleRepository.findAllByDateTime(memberId, startDateTime, endDateTime);
    }

    @Transactional(readOnly = true)
    public List<MoimScheduleResponse> findAllInMembersByWeekly(final List<Long> memberIds, final LocalDate startDate) {
        ScheduleLocalDate scheduleLocalDate = ScheduleLocalDate.from(startDate);
        LocalDateTime startDateTime = scheduleLocalDate.atWeeklyStartDateTime();
        LocalDateTime endDateTime = scheduleLocalDate.atWeeklyEndDateTime();

        return scheduleRepository.findAllInMembersByDateTime(memberIds, startDateTime, endDateTime);
    }

    @Transactional(readOnly = true)
    public List<ScheduleResponse> findAllByMonthly(final long memberId, final YearMonth yearMonth) {
        ScheduleYearMonth scheduleYearMonth = ScheduleYearMonth.from(yearMonth);
        LocalDateTime startDateTime = scheduleYearMonth.atMonthlyStartDateTime();
        LocalDateTime endDateTime = scheduleYearMonth.atMonthlyEndDateTime();

        return scheduleRepository.findAllByDateTime(memberId, startDateTime, endDateTime);
    }

    public boolean checkJoinAvailability(final ScheduleJpaEntity scheduleJpaEntity) {
        return !scheduleRepository.exists(scheduleJpaEntity);
    }
}
