package moim_today.implement.meeting.meeting;

import moim_today.global.annotation.Implement;
import moim_today.persistence.repository.meeting.meeting.MeetingRepository;

import java.util.List;

@Implement
public class MeetingFinder {

    private final MeetingRepository meetingRepository;

    public MeetingFinder(final MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    public List<Long> findAllByMoimId(final long moimId) {
        return meetingRepository.findAllByMoimId(moimId);
    }
}
