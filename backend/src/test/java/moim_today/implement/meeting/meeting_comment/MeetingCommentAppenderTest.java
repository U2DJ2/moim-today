package moim_today.implement.meeting.meeting_comment;

import moim_today.dto.meeting.meeting_comment.MeetingCommentCreateRequest;
import moim_today.persistence.entity.meeting.meeting_comment.MeetingCommentJpaEntity;
import moim_today.util.ImplementTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static moim_today.util.TestConstant.*;
import static org.assertj.core.api.Assertions.*;

class MeetingCommentAppenderTest extends ImplementTest {

    @Autowired
    private MeetingCommentAppender meetingCommentAppender;
    
    @DisplayName("미팅에 댓글을 작성한다.")
    @Test
    void createMeetingComment(){
        //given
        long memberId = MEMBER_ID.longValue();

        MeetingCommentCreateRequest meetingCommentCreateRequest = MeetingCommentCreateRequest.builder()
                .meetingId(MEETING_ID.longValue())
                .contents(MEETING_COMMENT_CONTENTS.value())
                .build();

        //when
        meetingCommentAppender.createMeetingComment(memberId, meetingCommentCreateRequest);

        //then
        assertThat(meetingCommentRepository.count()).isEqualTo(1);
    }
}