package moim_today.persistence.repository.meeting.meeting_comment;

import com.querydsl.jpa.impl.JPAQueryFactory;
import moim_today.global.error.BadRequestException;
import moim_today.global.error.NotFoundException;
import moim_today.persistence.entity.meeting.meeting.MeetingJpaEntity;
import moim_today.persistence.entity.meeting.meeting_comment.MeetingCommentJpaEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

import static moim_today.global.constant.exception.MeetingCommentExceptionConstant.MEETING_COMMENT_NOT_FOUND_ERROR;
import static moim_today.persistence.entity.meeting.meeting_comment.QMeetingCommentJpaEntity.meetingCommentJpaEntity;

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
                .set(meetingCommentJpaEntity.memberId, 0L)
                .where(meetingCommentJpaEntity.memberId.eq(memberId)
                        .and(meetingCommentJpaEntity.meetingId.in(meetingIds)))
                .execute();
    }

}
