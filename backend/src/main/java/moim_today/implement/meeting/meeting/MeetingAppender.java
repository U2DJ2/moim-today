package moim_today.implement.meeting.meeting;

import moim_today.domain.meeting.enums.MeetingCategory;
import moim_today.dto.meeting.MeetingCreateRequest;
import moim_today.dto.moim.moim.MoimDateResponse;
import moim_today.global.annotation.Implement;
import moim_today.implement.moim.moim.MoimFinder;
import moim_today.persistence.entity.meeting.meeting.MeetingJpaEntity;
import moim_today.persistence.repository.meeting.meeting.MeetingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static moim_today.global.constant.TimeConstant.*;


@Implement
public class MeetingAppender {

    private final MeetingRepository meetingRepository;
    private final MoimFinder moimFinder;

    public MeetingAppender(final MeetingRepository meetingRepository, final MoimFinder moimFinder) {
        this.meetingRepository = meetingRepository;
        this.moimFinder = moimFinder;
    }

    @Transactional
    public void createMeeting(final MeetingCreateRequest meetingCreateRequest) {
        MeetingCategory meetingCategory = meetingCreateRequest.meetingCategory();

        if (meetingCategory.equals(MeetingCategory.SINGLE)) {
            createSingleMeeting(meetingCreateRequest);

        } else if (meetingCategory.equals(MeetingCategory.REGULAR)) {
            createRegularMeeting(meetingCreateRequest);
        }
    }

    private void createSingleMeeting(final MeetingCreateRequest meetingCreateRequest) {
        MeetingJpaEntity meetingJpaEntity = meetingCreateRequest.toEntity(
                        meetingCreateRequest.startDateTime(),
                        meetingCreateRequest.endDateTime()
                );

        meetingRepository.save(meetingJpaEntity);
    }

    private void createRegularMeeting(final MeetingCreateRequest meetingCreateRequest) {
        MoimDateResponse moimDateResponse = moimFinder.findMoimDate(meetingCreateRequest.moimId());
        LocalDate startDate = moimDateResponse.startDate();
        LocalDate endDate = moimDateResponse.endDate();

        LocalTime startTime = meetingCreateRequest.startDateTime().toLocalTime();
        LocalTime endTime = meetingCreateRequest.endDateTime().toLocalTime();

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusWeeks(ONE_WEEK.time())) {
            LocalDateTime startDateTime = LocalDateTime.of(date, startTime);
            LocalDateTime endDateTime = LocalDateTime.of(date, endTime);

            MeetingJpaEntity meetingJpaEntity = meetingCreateRequest.toEntity(startDateTime, endDateTime);
            meetingRepository.save(meetingJpaEntity);
        }
    }
}
