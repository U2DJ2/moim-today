package moim_today.implement.meeting.meeting_comment;

import moim_today.persistence.entity.meeting.meeting_comment.MeetingCommentJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class MeetingCommentRemoverTest extends ImplementTest {

    @Autowired
    private MeetingCommentRemover meetingCommentRemover;

    @DisplayName("댓글을 삭제한다.")
    @Test
    void deleteMeetingComment(){
        //given
        MeetingCommentJpaEntity meetingCommentJpaEntity = MeetingCommentJpaEntity.builder()
                .build();

        meetingCommentRepository.save(meetingCommentJpaEntity);
        long commentId = meetingCommentJpaEntity.getId();

        //when
        meetingCommentRemover.deleteById(commentId);

        //then
        assertThat(meetingCommentRepository.count()).isEqualTo(0);
    }
}