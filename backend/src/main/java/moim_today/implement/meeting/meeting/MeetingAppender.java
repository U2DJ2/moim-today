package moim_today.implement.meeting.meeting;

import moim_today.domain.meeting.enums.MeetingCategory;
import moim_today.dto.meeting.MeetingCreateRequest;
import moim_today.global.annotation.Implement;
import moim_today.persistence.entity.meeting.meeting.MeetingJpaEntity;
import moim_today.persistence.repository.meeting.meeting.MeetingRepository;
import org.springframework.transaction.annotation.Transactional;

@Implement
public class MeetingAppender {

    private final MeetingRepository meetingRepository;

    public MeetingAppender(final MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    @Transactional
    public void createMeeting(final MeetingCreateRequest meetingCreateRequest) {
        MeetingCategory meetingCategory = meetingCreateRequest.meetingCategory();

        if (meetingCategory.equals(MeetingCategory.SINGLE)) {
            MeetingJpaEntity meetingJpaEntity = meetingCreateRequest.toEntity();
            meetingRepository.save(meetingJpaEntity);
        }
    }
}
