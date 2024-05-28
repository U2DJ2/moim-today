package moim_today.persistence.repository.meeting.meeting_comment;

import com.querydsl.jpa.impl.JPAQueryFactory;
import moim_today.dto.meeting.meeting_comment.MeetingCommentResponse;
import moim_today.dto.meeting.meeting_comment.QMeetingCommentResponse;
import moim_today.global.error.NotFoundException;
import moim_today.persistence.entity.meeting.meeting_comment.MeetingCommentJpaEntity;
import moim_today.persistence.entity.meeting.meeting_comment.QMeetingCommentJpaEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

import static moim_today.global.constant.MemberConstant.UNKNOWN_MEMBER;
import static moim_today.global.constant.exception.MeetingCommentExceptionConstant.MEETING_COMMENT_NOT_FOUND_ERROR;
import static moim_today.persistence.entity.meeting.meeting_comment.QMeetingCommentJpaEntity.meetingCommentJpaEntity;
import static moim_today.persistence.entity.member.QMemberJpaEntity.memberJpaEntity;

@Repository
public class MeetingCommentRepositoryImpl implements MeetingCommentRepository {

    private final MeetingCommentJpaRepository meetingCommentJpaRepository;
    private final JPAQueryFactory queryFactory;

    public MeetingCommentRepositoryImpl(final MeetingCommentJpaRepository meetingCommentJpaRepository,
                                        final JPAQueryFactory queryFactory) {
        this.meetingCommentJpaRepository = meetingCommentJpaRepository;
        this.queryFactory = queryFactory;
    }

    @Override
    public MeetingCommentJpaEntity save(final MeetingCommentJpaEntity meetingJpaEntity) {
        return meetingCommentJpaRepository.save(meetingJpaEntity);
    }

    @Override
    public MeetingCommentJpaEntity findById(final long meetingCommentId) {
        return meetingCommentJpaRepository.findById(meetingCommentId)
                .orElseThrow(() -> new NotFoundException(MEETING_COMMENT_NOT_FOUND_ERROR.message()));
    }

    @Override
    public void updateDeletedMembers(final long memberId, final List<Long> meetingIds) {
        queryFactory.update(meetingCommentJpaEntity)
                .set(meetingCommentJpaEntity.memberId, UNKNOWN_MEMBER.longValue())
                .where(meetingCommentJpaEntity.memberId.eq(memberId)
                        .and(meetingCommentJpaEntity.meetingId.in(meetingIds)))
                .execute();
    }

    @Override
    public long count() {
        return meetingCommentJpaRepository.count();
    }

    @Override
    public List<MeetingCommentResponse> findAllByMeetingId(final long meetingId) {
        return queryFactory.select(
                        new QMeetingCommentResponse(
                                meetingCommentJpaEntity.memberId,
                                meetingCommentJpaEntity.id,
                                memberJpaEntity.username,
                                memberJpaEntity.memberProfileImageUrl,
                                meetingCommentJpaEntity.contents,
                                meetingCommentJpaEntity.createdAt
                        ))
                .from(meetingCommentJpaEntity)
                .join(memberJpaEntity).on(meetingCommentJpaEntity.memberId.eq(memberJpaEntity.id))
                .where(meetingCommentJpaEntity.meetingId.eq(meetingId))
                .fetch();
    }

    @Override
    public MeetingCommentJpaEntity getById(final long meetingCommentId) {
        return meetingCommentJpaRepository.findById(meetingCommentId)
                .orElseThrow(() -> new NotFoundException(MEETING_COMMENT_NOT_FOUND_ERROR.message()));
    }

    @Override
    public void deleteById(final long meetingCommentId) {
        meetingCommentJpaRepository.deleteById(meetingCommentId);
    }
}
