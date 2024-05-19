package moim_today.implement.meeting.meeting;

import moim_today.domain.meeting.enums.MeetingCategory;
import moim_today.dto.meeting.MeetingCreateRequest;
import moim_today.dto.meeting.MeetingCreateResponse;
import moim_today.dto.moim.moim.MoimDateResponse;
import moim_today.global.annotation.Implement;
import moim_today.implement.meeting.joined_meeting.JoinedMeetingAppender;
import moim_today.implement.meeting.joined_meeting.JoinedMeetingFinder;
import moim_today.implement.moim.moim.MoimFinder;
import moim_today.implement.schedule.schedule.ScheduleAppender;
import moim_today.persistence.entity.meeting.meeting.MeetingJpaEntity;
import moim_today.persistence.entity.schedule.schedule.ScheduleJpaEntity;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static moim_today.global.constant.NumberConstant.SCHEDULE_MEETING_ID;
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

    @Transactional
    public MeetingCreateResponse createMeeting(final MeetingCreateRequest meetingCreateRequest) {
        MeetingCategory meetingCategory = meetingCreateRequest.meetingCategory();
        String moimTitle = moimFinder.getTitleById(meetingCreateRequest.moimId());

        if (meetingCategory.equals(MeetingCategory.SINGLE)) {
            return createSingleMeeting(meetingCreateRequest, moimTitle);
        } else {
            return createRegularMeeting(meetingCreateRequest, moimTitle);
        }
    }

    private MeetingCreateResponse createSingleMeeting(final MeetingCreateRequest meetingCreateRequest, final String moimTitle) {
        MeetingJpaEntity meetingJpaEntity = meetingCreateRequest.toEntity(
                        meetingCreateRequest.startDateTime(),
                        meetingCreateRequest.endDateTime()
                );

        MeetingJpaEntity saveEntity = meetingAppender.saveMeeting(meetingJpaEntity);
        joinedMeetingAppender.saveJoinedMeeting(meetingCreateRequest.moimId(), meetingJpaEntity.getId());
        createSchedules(moimTitle, meetingJpaEntity);

        return MeetingCreateResponse.of(saveEntity.getId(), meetingCreateRequest);
    }

    private MeetingCreateResponse createRegularMeeting(final MeetingCreateRequest meetingCreateRequest, final String moimTitle) {
        MoimDateResponse moimDateResponse = moimFinder.findMoimDate(meetingCreateRequest.moimId());
        LocalDate startDate = moimDateResponse.startDate();
        LocalDate endDate = moimDateResponse.endDate();

        LocalTime startTime = meetingCreateRequest.startDateTime().toLocalTime();
        LocalTime endTime = meetingCreateRequest.endDateTime().toLocalTime();
        long firstMeetingId = SCHEDULE_MEETING_ID.value();

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusWeeks(ONE_WEEK.time())) {
            LocalDateTime startDateTime = LocalDateTime.of(date, startTime);
            LocalDateTime endDateTime = LocalDateTime.of(date, endTime);

            MeetingJpaEntity meetingJpaEntity = meetingCreateRequest.toEntity(startDateTime, endDateTime);
            MeetingJpaEntity saveEntity = meetingAppender.saveMeeting(meetingJpaEntity);
            if(firstMeetingId == SCHEDULE_MEETING_ID.value()) {
                firstMeetingId = saveEntity.getId();
            }

            joinedMeetingAppender.saveJoinedMeeting(meetingCreateRequest.moimId(), meetingJpaEntity.getId());
            createSchedules(moimTitle, meetingJpaEntity);
        }

        return MeetingCreateResponse.of(firstMeetingId, meetingCreateRequest);
    }

    private void createSchedules(final String moimTitle, final MeetingJpaEntity meetingJpaEntity) {
        List<Long> memberIds = joinedMeetingFinder.findAllMemberId(meetingJpaEntity.getId());

        for (long memberId : memberIds) {
            ScheduleJpaEntity scheduleJpaEntity = ScheduleJpaEntity.toEntity(memberId, moimTitle, meetingJpaEntity);
            scheduleAppender.createScheduleIfNotExist(scheduleJpaEntity);
        }
    }
}
