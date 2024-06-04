package moim_today.implement.moim.moim;

import moim_today.dto.moim.moim.MoimCreateRequest;
import moim_today.global.annotation.Implement;
import moim_today.implement.meeting.joined_meeting.JoinedMeetingAppender;
import moim_today.implement.meeting.meeting.MeetingFinder;
import moim_today.implement.moim.joined_moim.JoinedMoimAppender;
import moim_today.implement.moim.joined_moim.JoinedMoimFinder;
import moim_today.persistence.entity.meeting.meeting.MeetingJpaEntity;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import moim_today.persistence.repository.moim.moim.MoimRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static moim_today.global.constant.NumberConstant.PLUS_ONE;

@Implement
public class MoimAppender {

    private final MoimRepository moimRepository;
    private final MoimFinder moimFinder;
    private final JoinedMoimFinder joinedMoimFinder;
    private final MoimUpdater moimUpdater;
    private final JoinedMoimAppender joinedMoimAppender;
    private final MeetingFinder meetingFinder;
    private final JoinedMeetingAppender joinedMeetingAppender;

    public MoimAppender(final MoimRepository moimRepository, final MoimFinder moimFinder,
                        final JoinedMoimFinder joinedMoimFinder, final MoimUpdater moimUpdater,
                        final JoinedMoimAppender joinedMoimAppender, final MeetingFinder meetingFinder,
                        final JoinedMeetingAppender joinedMeetingAppender) {
        this.moimRepository = moimRepository;
        this.moimFinder = moimFinder;
        this.joinedMoimFinder = joinedMoimFinder;
        this.moimUpdater = moimUpdater;
        this.joinedMoimAppender = joinedMoimAppender;
        this.meetingFinder = meetingFinder;
        this.joinedMeetingAppender = joinedMeetingAppender;
    }

    @Transactional
    public MoimJpaEntity createMoim(final long memberId, final long universityId,
                                    final MoimCreateRequest moimCreateRequest) {
        MoimJpaEntity moimJpaEntity = moimCreateRequest.toEntity(memberId, universityId);
        return moimRepository.save(moimJpaEntity);
    }

    @Transactional
    public void appendMemberToMoim(final long requestMemberId, final long moimId, final LocalDate currentDate) {
        MoimJpaEntity enterMoimEntity = moimFinder.getByIdWithPessimisticLock(moimId);
        enterMoimEntity.validateMoimNotEnd(currentDate);
        moimFinder.validateCapacity(enterMoimEntity);
        joinedMoimFinder.validateMemberNotInMoim(moimId, requestMemberId);

        moimUpdater.updateMoimCurrentCount(moimId, PLUS_ONE.value());
        joinedMoimAppender.createJoinedMoim(requestMemberId, moimId);

        List<Long> meetingIds = meetingFinder.findUpcomingMeetingIdsByMoimId(moimId, currentDate);
        for (long meetingId : meetingIds) {
            MeetingJpaEntity meetingJpaEntity = meetingFinder.getById(meetingId);
            joinedMeetingAppender.saveJoinedMeeting(moimId, meetingId, enterMoimEntity.getTitle(), meetingJpaEntity);
        }
    }
}
