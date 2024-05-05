package moim_today.persistence.repository.meeting.meeting_comment;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public void updateDeletedMembers(final long memberId, final List<Long> meetingIds) {
        queryFactory.update(meetingCommentJpaEntity)
                .set(meetingCommentJpaEntity.memberId, (Long) null)
                .where(meetingCommentJpaEntity.memberId.eq(memberId)
                        .and(meetingCommentJpaEntity.meetingId.in(meetingIds)))
                .execute();
    }
}
