package moim_today.persistence.repository.meeting.meeting;

import moim_today.domain.meeting.enums.MeetingStatus;
import moim_today.dto.admin.meeting.AdminMeetingResponse;
import moim_today.dto.mail.UpcomingMeetingNoticeResponse;
import moim_today.dto.meeting.meeting.JoinedMeetingDao;
import moim_today.dto.meeting.meeting.MeetingSimpleDao;
import moim_today.persistence.entity.meeting.meeting.MeetingJpaEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface MeetingRepository {

    List<Long> findMeetingIdsByMoimId(final long moimId);

    List<Long> findUpcomingMeetingIdsByMoimId(final long moimId, final LocalDate currentDate);

    List<MeetingSimpleDao> findAllByMoimId(final long moimId, final long memberId, final LocalDateTime currentDateTime);

    List<MeetingSimpleDao> findAllUpcomingByMoimId(final long moimId, final long memberId, final LocalDateTime currentDateTime);

    List<MeetingSimpleDao> findAllPastByMoimId(final long moimId, final long memberId, final LocalDateTime currentDateTime);

    List<JoinedMeetingDao> findAllByMeetingIds(final List<Long> meetingIds);

    List<JoinedMeetingDao> findAllUpcomingByMeetingIds(final List<Long> meetingIds, final LocalDateTime currentDateTime);

    List<JoinedMeetingDao> findAllPastByMeetingIds(final List<Long> meetingIds, final LocalDateTime currentDateTime);

    List<AdminMeetingResponse> findAllByAdminMoimId(final long moimId);

    long getHostIdByMeetingId(final long meetingId);

    List<UpcomingMeetingNoticeResponse> findUpcomingNotices(final LocalDateTime currentDateTime);

    MeetingJpaEntity getById(final long meetingId);

    MeetingJpaEntity save(final MeetingJpaEntity meetingJpaEntity);

    void delete(final MeetingJpaEntity meetingJpaEntity);

    long count();

    long findMoimIdByMeetingId(final long meetingId);
}
