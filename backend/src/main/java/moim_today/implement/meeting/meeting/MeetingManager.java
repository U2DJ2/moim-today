package moim_today.implement.meeting.meeting;

import moim_today.domain.meeting.enums.MeetingCategory;
import moim_today.dto.meeting.MeetingCreateRequest;
import moim_today.dto.moim.moim.MoimDateResponse;
import moim_today.global.annotation.Implement;
import moim_today.implement.meeting.joined_meeting.JoinedMeetingAppender;
import moim_today.implement.meeting.joined_meeting.JoinedMeetingFinder;
import moim_today.implement.moim.moim.MoimFinder;
import moim_today.implement.schedule.ScheduleAppender;
import moim_today.persistence.entity.meeting.meeting.MeetingJpaEntity;
import moim_today.persistence.entity.schedule.ScheduleJpaEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static moim_today.global.constant.TimeConstant.*;


@Implement
public class MeetingManager {

    private final MeetingAppender meetingAppender;
    private final MoimFinder moimFinder;
    private final ScheduleAppender scheduleAppender;
    private final JoinedMeetingAppender joinedMeetingAppender;
    private final JoinedMeetingFinder joinedMeetingFinder;

    public MeetingManager(final MeetingAppender meetingAppender, final MoimFinder moimFinder,
                          final ScheduleAppender scheduleAppender, final JoinedMeetingAppender joinedMeetingAppender,
                          final JoinedMeetingFinder joinedMeetingFinder) {
        this.meetingAppender = meetingAppender;
        this.moimFinder = moimFinder;
        this.scheduleAppender = scheduleAppender;
        this.joinedMeetingAppender = joinedMeetingAppender;
        this.joinedMeetingFinder = joinedMeetingFinder;
    }

    public void createMeeting(final MeetingCreateRequest meetingCreateRequest) {
        MeetingCategory meetingCategory = meetingCreateRequest.meetingCategory();
        String moimTitle = moimFinder.getTitleById(meetingCreateRequest.moimId());

        if (meetingCategory.equals(MeetingCategory.SINGLE)) {
            createSingleMeeting(meetingCreateRequest, moimTitle);

        } else if (meetingCategory.equals(MeetingCategory.REGULAR)) {
            createRegularMeeting(meetingCreateRequest, moimTitle);
        }
    }

    private void createSingleMeeting(final MeetingCreateRequest meetingCreateRequest, final String moimTitle) {
        MeetingJpaEntity meetingJpaEntity = meetingCreateRequest.toEntity(
                        meetingCreateRequest.startDateTime(),
                        meetingCreateRequest.endDateTime()
                );

        meetingAppender.saveMeeting(meetingJpaEntity);
        joinedMeetingAppender.saveJoinedMeeting(meetingCreateRequest.moimId(), meetingJpaEntity.getId());
        createSchedules(moimTitle, meetingJpaEntity);
    }

    private void createRegularMeeting(final MeetingCreateRequest meetingCreateRequest, final String moimTitle) {
        MoimDateResponse moimDateResponse = moimFinder.findMoimDate(meetingCreateRequest.moimId());
        LocalDate startDate = moimDateResponse.startDate();
        LocalDate endDate = moimDateResponse.endDate();

        LocalTime startTime = meetingCreateRequest.startDateTime().toLocalTime();
        LocalTime endTime = meetingCreateRequest.endDateTime().toLocalTime();

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusWeeks(ONE_WEEK.time())) {
            LocalDateTime startDateTime = LocalDateTime.of(date, startTime);
            LocalDateTime endDateTime = LocalDateTime.of(date, endTime);

            MeetingJpaEntity meetingJpaEntity = meetingCreateRequest.toEntity(startDateTime, endDateTime);
            meetingAppender.saveMeeting(meetingJpaEntity);
            joinedMeetingAppender.saveJoinedMeeting(meetingCreateRequest.moimId(), meetingJpaEntity.getId());
            createSchedules(moimTitle, meetingJpaEntity);
        }
    }

    private void createSchedules(final String moimTitle, final MeetingJpaEntity meetingJpaEntity) {
        List<Long> memberIds = joinedMeetingFinder.findAllMemberId(meetingJpaEntity.getId());
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (long memberId : memberIds) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                ScheduleJpaEntity scheduleJpaEntity = ScheduleJpaEntity.toEntity(memberId, moimTitle, meetingJpaEntity);
                scheduleAppender.createSchedule(scheduleJpaEntity);
            });

            futures.add(future);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }
}
