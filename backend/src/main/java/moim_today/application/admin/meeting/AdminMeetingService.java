package moim_today.application.admin.meeting;

import moim_today.dto.admin.meeting.AdminMeetingResponse;
import moim_today.global.error.ForbiddenException;
import moim_today.persistence.entity.meeting.meeting.MeetingJpaEntity;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import moim_today.persistence.repository.meeting.meeting.MeetingRepository;
import moim_today.persistence.repository.moim.moim.MoimRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static moim_today.global.constant.exception.MeetingExceptionConstant.*;
import static moim_today.global.constant.exception.MoimExceptionConstant.MOIM_FORBIDDEN_ERROR;

@Service
public class AdminMeetingService {

    private final MeetingRepository meetingRepository;
    private final MoimRepository moimRepository;

    public AdminMeetingService(final MeetingRepository meetingRepository,
                               final MoimRepository moimRepository) {
        this.meetingRepository = meetingRepository;
        this.moimRepository = moimRepository;
    }

    @Transactional(readOnly = true)
    public List<AdminMeetingResponse> findAllByMoimId(final long universityId, final long moimId) {
        MoimJpaEntity moimJpaEntity = moimRepository.getById(moimId);
        if (moimJpaEntity.getUniversityId() != universityId) {
            throw new ForbiddenException(MOIM_FORBIDDEN_ERROR.message());
        }
        return meetingRepository.findAllByAdminMoimId(moimId);
    }

    @Transactional
    public void deleteMeeting(final long universityId, final long meetingId) {
        MeetingJpaEntity meetingJpaEntity = meetingRepository.getById(meetingId);
        MoimJpaEntity moimJpaEntity = moimRepository.getById(meetingJpaEntity.getMoimId());

        if (moimJpaEntity.getUniversityId() != universityId) {
            throw new ForbiddenException(MEETING_FORBIDDEN_ERROR.message());
        }

        meetingRepository.delete(meetingJpaEntity);
    }
}
