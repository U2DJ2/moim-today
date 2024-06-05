package moim_today.implement.schedule.schedule;

import moim_today.domain.schedule.Schedule;
import moim_today.domain.schedule.TimeTableProcessor;
import moim_today.dto.schedule.MoimScheduleResponse;
import moim_today.dto.schedule.ScheduleResponse;
import moim_today.dto.schedule.ScheduleUpdateRequest;
import moim_today.dto.schedule.TimeTableRequest;
import moim_today.global.annotation.Implement;
import moim_today.persistence.entity.schedule.schedule.ScheduleJpaEntity;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;


@Implement
public class ScheduleComposition {

    private final ScheduleAppender scheduleAppender;
    private final ScheduleFinder scheduleFinder;
    private final ScheduleUpdater scheduleUpdater;
    private final ScheduleRemover scheduleRemover;
    private final ScheduleFetcher scheduleFetcher;

    public ScheduleComposition(final ScheduleAppender scheduleAppender,
                               final ScheduleFinder scheduleFinder,
                               final ScheduleUpdater scheduleUpdater,
                               final ScheduleRemover scheduleRemover,
                               final ScheduleFetcher scheduleFetcher) {
        this.scheduleAppender = scheduleAppender;
        this.scheduleFinder = scheduleFinder;
        this.scheduleUpdater = scheduleUpdater;
        this.scheduleRemover = scheduleRemover;
        this.scheduleFetcher = scheduleFetcher;
    }

    public void batchUpdateSchedules(final List<Schedule> schedules, final long memberId) {
        scheduleAppender.batchUpdateSchedules(schedules, memberId);
    }

    public void createSchedule(final ScheduleJpaEntity scheduleJpaEntity) {
        scheduleAppender.createSchedule(scheduleJpaEntity);
    }

    public boolean createScheduleIfNotExist(final ScheduleJpaEntity scheduleJpaEntity) {
        return scheduleAppender.createScheduleIfNotExist(scheduleJpaEntity);
    }

    public List<ScheduleResponse> findAllByWeekly(final long memberId, final LocalDate startDate) {
        return scheduleFinder.findAllByWeekly(memberId, startDate);
    }

    public List<MoimScheduleResponse> findAllInMembersByWeekly(final List<Long> memberIds, final LocalDate startDate) {
        return scheduleFinder.findAllInMembersByWeekly(memberIds, startDate);
    }

    public List<ScheduleResponse> findAllByMonthly(final long memberId, final YearMonth yearMonth) {
        return scheduleFinder.findAllByMonthly(memberId, yearMonth);
    }

    public void updateSchedule(final long memberId, final ScheduleUpdateRequest scheduleUpdateRequest) {
        scheduleUpdater.updateSchedule(memberId, scheduleUpdateRequest);
    }

    public void deleteSchedule(final long memberId, final long scheduleId) {
        scheduleRemover.deleteSchedule(memberId, scheduleId);
    }

    public void deleteAllByMeetingId(final long meetingId) {
        scheduleRemover.deleteAllByMeetingId(meetingId);
    }

    public void deleteByMemberIdAndMeetingId(final long memberId, final long meetingId) {
        scheduleRemover.deleteByMemberIdAndMeetingId(memberId, meetingId);
    }

    public String fetchTimetable(final String everytimeUrl) {
        return scheduleFetcher.fetchTimetable(everytimeUrl);
    }

    public TimeTableProcessor processTimetable(final String timeTableXML,
                                               final TimeTableRequest timeTableRequest,
                                               final int colorCount) {
        return scheduleFetcher.processTimetable(timeTableXML, timeTableRequest, colorCount);
    }
}
