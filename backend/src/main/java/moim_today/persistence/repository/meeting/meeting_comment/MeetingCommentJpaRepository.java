package moim_today.persistence.repository.meeting.meeting_comment;

import moim_today.persistence.entity.meeting.meeting_comment.MeetingCommentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MeetingCommentJpaRepository extends JpaRepository<MeetingCommentJpaEntity, Long> {

    Optional<MeetingCommentJpaEntity> findById(final long meetingCommentId);
}
