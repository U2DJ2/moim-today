package moim_today.persistence.repository.meeting.meeting_comment;

import org.springframework.stereotype.Repository;

@Repository
public class MeetingCommentRepositoryImpl implements MeetingCommentRepository {

    private final MeetingCommentJpaRepository meetingCommentJpaRepository;

    public MeetingCommentRepositoryImpl(final MeetingCommentJpaRepository meetingCommentJpaRepository) {
        this.meetingCommentJpaRepository = meetingCommentJpaRepository;
    }
}
