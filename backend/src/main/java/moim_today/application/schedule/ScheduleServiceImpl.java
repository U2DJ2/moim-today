package moim_today.application.schedule;

import moim_today.domain.schedule.AvailableTime;
import moim_today.domain.schedule.TimeTableProcessor;
import moim_today.dto.schedule.*;
import moim_today.implement.moim.joined_moim.JoinedMoimComposition;
import moim_today.implement.schedule.schedule.*;
import moim_today.implement.schedule.schedule_color.ScheduleColorComposition;
import moim_today.persistence.entity.schedule.schedule.ScheduleJpaEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import static moim_today.global.constant.NumberConstant.SCHEDULE_COLOR_NEXT_COUNT;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleComposition scheduleComposition;
    private final JoinedMoimComposition joinedMoimComposition;
    private final ScheduleColorComposition scheduleColorComposition;

    public ScheduleServiceImpl(final ScheduleComposition scheduleComposition,
                               final JoinedMoimComposition joinedMoimComposition,
                               final ScheduleColorComposition scheduleColorComposition) {
        this.scheduleComposition = scheduleComposition;
        this.joinedMoimComposition = joinedMoimComposition;
        this.scheduleColorComposition = scheduleColorComposition;
    }

    @Override
    public List<ScheduleResponse> findAllByWeekly(final long memberId, final LocalDate startDate) {
        return scheduleComposition.findAllByWeekly(memberId, startDate);
    }

    @Override
    public List<AvailableTimeInMoimResponse> findWeeklyAvailableTimeInMoim(final long moimId, final LocalDate startDate) {
        List<Long> memberIds = joinedMoimComposition.findAllJoinedMemberId(moimId);
        List<MoimScheduleResponse> moimScheduleResponses = scheduleComposition.findAllInMembersByWeekly(memberIds, startDate);
        List<AvailableTime> availableTimes = AvailableTime.calculateAvailableTimes(moimScheduleResponses, startDate);

        return AvailableTimeInMoimResponse.from(availableTimes);
    }

    @Override
    public List<AvailableTimeForMemberResponse> findWeeklyAvailableTimeForMember(final long memberId, final LocalDate startDate) {
        List<MoimScheduleResponse> moimScheduleResponses =
                scheduleComposition.findAllInMembersByWeekly(List.of(memberId), startDate);
        List<AvailableTime> availableTimes = AvailableTime.calculateAvailableTimes(moimScheduleResponses, startDate);

        return AvailableTimeForMemberResponse.from(availableTimes);
    }

    @Override
    public List<ScheduleResponse> findAllByMonthly(final long memberId, final YearMonth yearMonth) {
        return scheduleComposition.findAllByMonthly(memberId, yearMonth);
    }

    @Override
    public void fetchTimeTable(final long memberId, final TimeTableRequest timeTableRequest) {
        String timeTableXML = scheduleComposition.fetchTimetable(timeTableRequest.everytimeUrl());
        int count = scheduleColorComposition.getColorCount(memberId);
        TimeTableProcessor timeTableProcessor =
                scheduleComposition.processTimetable(timeTableXML, timeTableRequest, count);
        int colorCount = timeTableProcessor.getColorCountSize();
        scheduleColorComposition.updateColorCount(memberId, colorCount);

        scheduleComposition.batchUpdateSchedules(timeTableProcessor.schedules(), memberId);
    }

    @Override
    public void createSchedule(final long memberId, final ScheduleCreateRequest scheduleCreateRequest) {
        String colorHex = scheduleColorComposition.getColorHex(memberId);
        scheduleColorComposition.updateColorCount(memberId, SCHEDULE_COLOR_NEXT_COUNT.value());
        ScheduleJpaEntity scheduleJpaEntity = scheduleCreateRequest.toEntity(memberId, colorHex);
        scheduleComposition.createSchedule(scheduleJpaEntity);
    }

    @Override
    public void updateSchedule(final long memberId, final ScheduleUpdateRequest scheduleUpdateRequest) {
        scheduleComposition.updateSchedule(memberId, scheduleUpdateRequest);
    }

    @Override
    public void deleteSchedule(final long memberId, final long scheduleId) {
        scheduleComposition.deleteSchedule(memberId, scheduleId);
    }

    @Override
    public void deleteAllByMeetingId(final long meetingId) {
        scheduleComposition.deleteAllByMeetingId(meetingId);
    }
}