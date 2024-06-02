package moim_today.implement.meeting.meeting;

import moim_today.domain.meeting.enums.MeetingCategory;
import moim_today.dto.meeting.meeting.MeetingCreateResponse;
import moim_today.dto.meeting.meeting.MeetingCreateRequest;
import moim_today.dto.moim.moim.MoimDateResponse;
import moim_today.global.annotation.Implement;
import moim_today.global.error.ForbiddenException;
import moim_today.implement.meeting.joined_meeting.JoinedMeetingAppender;
import moim_today.implement.moim.moim.MoimFinder;
import moim_today.persistence.entity.meeting.meeting.MeetingJpaEntity;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static moim_today.global.constant.NumberConstant.SCHEDULE_MEETING_ID;
import static moim_today.global.constant.TimeConstant.ONE_WEEK;
import static moim_today.global.constant.exception.MoimExceptionConstant.ORGANIZER_FORBIDDEN_ERROR;


@Implement
public class MeetingManager {

    private final MeetingAppender meetingAppender;
    private final MoimFinder moimFinder;
    private final JoinedMeetingAppender joinedMeetingAppender;

    public MeetingManager(final MeetingAppender meetingAppender, final MoimFinder moimFinder,
                          final JoinedMeetingAppender joinedMeetingAppender) {
        this.meetingAppender = meetingAppender;
        this.moimFinder = moimFinder;
        this.joinedMeetingAppender = joinedMeetingAppender;
    }

    @Transactional
    public MeetingCreateResponse createMeeting(final long memberId,
                                               final MeetingCreateRequest meetingCreateRequest,
                                               final LocalDate currentDate) {
        validateMoimDateTime(meetingCreateRequest);
        validateMemberIsHost(memberId, meetingCreateRequest);
        MeetingCategory meetingCategory = meetingCreateRequest.meetingCategory();
        String moimTitle = moimFinder.getTitleById(meetingCreateRequest.moimId());

        if (meetingCategory.equals(MeetingCategory.SINGLE)) {
            return createSingleMeeting(meetingCreateRequest, moimTitle);
        } else {
            return createRegularMeeting(meetingCreateRequest, moimTitle, currentDate);
        }
    }

    private void validateMoimDateTime(final MeetingCreateRequest meetingCreateRequest) {
        long moimId = meetingCreateRequest.moimId();
        LocalDateTime meetingStartDateTime = meetingCreateRequest.startDateTime();
        LocalDateTime meetingEndDateTime = meetingCreateRequest.endDateTime();

        MoimJpaEntity moimJpaEntity = moimFinder.getById(moimId);
        moimJpaEntity.validateDateTime(meetingStartDateTime, meetingEndDateTime);
    }

    private void validateMemberIsHost(final long memberId, final MeetingCreateRequest meetingCreateRequest) {
        if (!moimFinder.isHost(memberId, meetingCreateRequest.moimId())) {
            throw new ForbiddenException(ORGANIZER_FORBIDDEN_ERROR.message());
        }
    }

    private MeetingCreateResponse createSingleMeeting(final MeetingCreateRequest meetingCreateRequest, final String moimTitle) {
        MeetingJpaEntity meetingJpaEntity = meetingCreateRequest.toEntity(
                        meetingCreateRequest.startDateTime(),
                        meetingCreateRequest.endDateTime()
                );

        MeetingJpaEntity saveEntity = meetingAppender.saveMeeting(meetingJpaEntity);
        moimFinder.getTitleById(meetingCreateRequest.moimId());
        joinedMeetingAppender.saveJoinedMeeting(meetingCreateRequest.moimId(), meetingJpaEntity.getId(), moimTitle, saveEntity);

        return MeetingCreateResponse.of(saveEntity.getId(), meetingCreateRequest);
    }

    private MeetingCreateResponse createRegularMeeting(final MeetingCreateRequest meetingCreateRequest,
                                                       final String moimTitle, final LocalDate currentDate) {
        MoimDateResponse moimDateResponse = moimFinder.findMoimDate(meetingCreateRequest.moimId());
        LocalDate moimEndDate = moimDateResponse.endDate();

        LocalTime meetingStartTime = meetingCreateRequest.startDateTime().toLocalTime();
        LocalTime meetingEndTime = meetingCreateRequest.endDateTime().toLocalTime();
        LocalDate meetingStartDate = getMeetingStartDate(meetingCreateRequest, currentDate);

        long firstMeetingId = SCHEDULE_MEETING_ID.value();

        for (LocalDate date = meetingStartDate; !date.isAfter(moimEndDate); date = date.plusWeeks(ONE_WEEK.time())) {
            LocalDateTime startDateTime = LocalDateTime.of(date, meetingStartTime);
            LocalDateTime endDateTime = LocalDateTime.of(date, meetingEndTime);

            MeetingJpaEntity meetingJpaEntity = meetingCreateRequest.toEntity(startDateTime, endDateTime);
            MeetingJpaEntity saveEntity = meetingAppender.saveMeeting(meetingJpaEntity);
            if(firstMeetingId == SCHEDULE_MEETING_ID.value()) {
                firstMeetingId = saveEntity.getId();
            }

            joinedMeetingAppender.saveJoinedMeeting(
                    meetingCreateRequest.moimId(), meetingJpaEntity.getId(), moimTitle, meetingJpaEntity);
        }

        return MeetingCreateResponse.of(firstMeetingId, meetingCreateRequest);
    }

    private LocalDate getMeetingStartDate(final MeetingCreateRequest meetingCreateRequest,
                                          final LocalDate currentDate) {
        LocalDate meetingStartDate = meetingCreateRequest.startDateTime().toLocalDate();
        meetingStartDate = meetingStartDate.isAfter(currentDate) ? meetingStartDate : currentDate;

        return meetingStartDate;
    }
}
