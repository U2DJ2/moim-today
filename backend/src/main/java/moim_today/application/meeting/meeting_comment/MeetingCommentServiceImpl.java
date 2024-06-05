package moim_today.application.meeting.meeting_comment;

import moim_today.dto.meeting.meeting_comment.MeetingCommentCreateRequest;
import moim_today.dto.meeting.meeting_comment.MeetingCommentResponse;
import moim_today.dto.meeting.meeting_comment.MeetingCommentUpdateRequest;
import moim_today.implement.meeting.meeting.MeetingComposition;
import moim_today.implement.meeting.meeting_comment.*;
import moim_today.implement.moim.joined_moim.JoinedMoimComposition;
import moim_today.persistence.entity.meeting.meeting_comment.MeetingCommentJpaEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MeetingCommentServiceImpl implements MeetingCommentService {

    private final MeetingCommentComposition meetingCommentComposition;
    private final MeetingComposition meetingComposition;
    private final JoinedMoimComposition joinedMoimComposition;

    public MeetingCommentServiceImpl(final MeetingCommentComposition meetingCommentComposition,
                                     final MeetingComposition meetingComposition,
                                     final JoinedMoimComposition joinedMoimComposition) {
        this.meetingCommentComposition = meetingCommentComposition;
        this.meetingComposition = meetingComposition;
        this.joinedMoimComposition = joinedMoimComposition;
    }

    @Override
    public void createMeetingComment(final long memberId, final MeetingCommentCreateRequest meetingCommentCreateRequest) {
        long moimId = meetingComposition.getMoimIdByMeetingId(meetingCommentCreateRequest.meetingId());
        joinedMoimComposition.validateMemberInMoim(memberId, moimId);
        meetingCommentComposition.createMeetingComment(memberId, meetingCommentCreateRequest);
    }

    @Override
    public List<MeetingCommentResponse> findAllByMeetingId(final long memberId, final long meetingId) {
        long moimId = meetingComposition.getMoimIdByMeetingId(meetingId);
        joinedMoimComposition.validateMemberInMoim(memberId, moimId);
        return meetingCommentComposition.findAllByMeetingId(meetingId);
    }

    @Transactional
    @Override
    public void updateMeetingComment(final long memberId, final MeetingCommentUpdateRequest meetingCommentUpdateRequest) {
        long meetingCommentId = meetingCommentUpdateRequest.meetingCommentId();
        MeetingCommentJpaEntity meetingCommentJpaEntity = meetingCommentComposition.getById(meetingCommentId);
        meetingCommentJpaEntity.validateMember(memberId);
        meetingCommentComposition.updateMeetingComment(meetingCommentId, meetingCommentUpdateRequest);
    }

    @Override
    public void deleteMeetingComment(final long memberId, final long meetingCommentId) {
        MeetingCommentJpaEntity meetingCommentJpaEntity = meetingCommentComposition.getById(meetingCommentId);
        meetingCommentJpaEntity.validateMember(memberId);
        meetingCommentComposition.deleteById(meetingCommentId);
    }
}
