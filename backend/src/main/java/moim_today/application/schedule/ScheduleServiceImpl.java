package moim_today.application.schedule;

import moim_today.domain.schedule.AvailableTime;
import moim_today.domain.schedule.TimeTableProcessor;
import moim_today.dto.schedule.*;
import moim_today.implement.moim.joined_moim.JoinedMoimFinder;
import moim_today.implement.schedule.schedule.*;
import moim_today.implement.schedule.schedule_color.ScheduleColorManager;
import moim_today.persistence.entity.schedule.schedule.ScheduleJpaEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import static moim_today.global.constant.NumberConstant.SCHEDULE_COLOR_NEXT_COUNT;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleManager scheduleManager;
    private final ScheduleFinder scheduleFinder;
    private final ScheduleAppender scheduleAppender;
    private final ScheduleUpdater scheduleUpdater;
    private final ScheduleRemover scheduleRemover;
    private final JoinedMoimFinder joinedMoimFinder;
    private final ScheduleColorManager scheduleColorManager;

    public ScheduleServiceImpl(final ScheduleManager scheduleManager, final ScheduleFinder scheduleFinder,
                               final ScheduleAppender scheduleAppender, final ScheduleUpdater scheduleUpdater,
                               final ScheduleRemover scheduleRemover, final JoinedMoimFinder joinedMoimFinder,
                               final ScheduleColorManager scheduleColorManager) {
        this.scheduleManager = scheduleManager;
        this.scheduleFinder = scheduleFinder;
        this.scheduleAppender = scheduleAppender;
        this.scheduleUpdater = scheduleUpdater;
        this.scheduleRemover = scheduleRemover;
        this.joinedMoimFinder = joinedMoimFinder;
        this.scheduleColorManager = scheduleColorManager;
    }

    @Override
    public List<ScheduleResponse> findAllByWeekly(final long memberId, final LocalDate startDate) {
        return scheduleFinder.findAllByWeekly(memberId, startDate);
    }

    @Override
    public List<AvailableTimeResponse> findWeeklyAvailableTime(final long moimId, final LocalDate startDate) {
        List<Long> memberIds = joinedMoimFinder.findAllJoinedMemberId(moimId);
        List<MoimScheduleResponse> moimScheduleResponses = scheduleFinder.findAllInMoimByWeekly(memberIds, startDate);

        List<AvailableTime> availableTimes = AvailableTime.calculateAvailableTimes(moimScheduleResponses);

        return null;
    }

    @Override
    public List<ScheduleResponse> findAllByMonthly(final long memberId, final YearMonth yearMonth) {
        return scheduleFinder.findAllByMonthly(memberId, yearMonth);
    }

    @Override
    public void fetchTimeTable(final long memberId, final TimeTableRequest timeTableRequest) {
        String timeTableXML = scheduleManager.fetchTimetable(timeTableRequest.everytimeUrl());
        int count = scheduleColorManager.getColorCount(memberId);
        TimeTableProcessor timeTableProcessor =
                scheduleManager.processTimetable(timeTableXML, timeTableRequest, count);
        int colorCount = timeTableProcessor.getColorCountSize();
        scheduleColorManager.updateColorCount(memberId, colorCount);

        scheduleAppender.batchUpdateSchedules(timeTableProcessor.schedules(), memberId);
    }

    @Override
    public void createSchedule(final long memberId, final ScheduleCreateRequest scheduleCreateRequest) {
        String colorHex = scheduleColorManager.getColorHex(memberId);
        scheduleColorManager.updateColorCount(memberId, SCHEDULE_COLOR_NEXT_COUNT.value());
        ScheduleJpaEntity scheduleJpaEntity = scheduleCreateRequest.toEntity(memberId, colorHex);
        scheduleAppender.createSchedule(scheduleJpaEntity);
    }

    @Override
    public void updateSchedule(final long memberId, final ScheduleUpdateRequest scheduleUpdateRequest) {
        scheduleUpdater.updateSchedule(memberId, scheduleUpdateRequest);
    }

    @Override
    public void deleteSchedule(final long memberId, final long scheduleId) {
        scheduleRemover.deleteSchedule(memberId, scheduleId);
    }
}