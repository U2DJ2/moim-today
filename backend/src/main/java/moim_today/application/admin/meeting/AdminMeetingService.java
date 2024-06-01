package moim_today.application.admin.meeting;

import moim_today.dto.admin.meeting.AdminMeetingResponse;
import moim_today.persistence.repository.meeting.meeting.MeetingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminMeetingService {

    private final MeetingRepository meetingRepository;

    public AdminMeetingService(final MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    @Transactional(readOnly = true)
    public List<AdminMeetingResponse> findAllByMoimId(final long moimId) {
        return meetingRepository.findAllByAdminMoimId(moimId);
    }
}
