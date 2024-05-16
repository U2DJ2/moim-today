package moim_today.implement.meeting.meeting_comment;

import moim_today.dto.meeting.meeting_comment.MeetingCommentUpdateRequest;
import moim_today.persistence.entity.meeting.meeting_comment.MeetingCommentJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static moim_today.util.TestConstant.*;
import static org.assertj.core.api.Assertions.assertThat;

class MeetingCommentUpdaterTest extends ImplementTest {

    @Autowired
    private MeetingCommentUpdater meetingCommentUpdater;

    @DisplayName("미팅에서 삭제된 멤버 ID를 미팅 댓글에서 0으로 업데이트한다")
    @Test
    void updateDeletedMembers() {
        // given
        long meetingId1 = MEETING_ID.longValue();
        long meetingId2 = MEETING_ID.longValue() + 1L;
        long forcedOutMemberId = MEMBER_ID.longValue();

        MeetingCommentJpaEntity meetingCommentJpaEntity1 = MeetingCommentJpaEntity.builder()
                .memberId(forcedOutMemberId)
                .meetingId(meetingId1)
                .build();
        MeetingCommentJpaEntity meetingCommentJpaEntity2 = MeetingCommentJpaEntity.builder()
                .memberId(forcedOutMemberId)
                .meetingId(meetingId2)
                .build();

        MeetingCommentJpaEntity savedMeetingComment1 = meetingCommentRepository.save(meetingCommentJpaEntity1);
        MeetingCommentJpaEntity savedMeetingComment2 = meetingCommentRepository.save(meetingCommentJpaEntity2);

        List<Long> meetingIds = List.of(meetingId1, meetingId2);

        // when
        meetingCommentUpdater.updateDeletedMembers(forcedOutMemberId, meetingIds);

        // then
        assertThat(meetingCommentRepository
                .findById(savedMeetingComment1.getId())
                .getMemberId())
                .isEqualTo(0L);
        assertThat(meetingCommentRepository
                .findById(savedMeetingComment2.getId())
                .getMemberId())
                .isEqualTo(0L);
    }

    @DisplayName("미팅에서 삭제된 멤버 ID만 미팅 댓글에서 0으로 업데이트하고, 나머지 멤버는 건드리지 않는다")
    @Test
    void updateOnlyDeletedMembers() {
        // given
        long meetingId1 = MEETING_ID.longValue();
        long meetingId2 = MEETING_ID.longValue() + 1L;
        long forcedOutMemberId = MEMBER_ID.longValue();
        long stillInMemberId1 = MEMBER_ID.longValue()+ 1L;
        long stillInMemberId2 = MEMBER_ID.longValue() + 2L;

        MeetingCommentJpaEntity meetingCommentJpaEntity1 = MeetingCommentJpaEntity.builder()
                .memberId(forcedOutMemberId)
                .meetingId(meetingId1)
                .build();
        MeetingCommentJpaEntity meetingCommentJpaEntity2 = MeetingCommentJpaEntity.builder()
                .memberId(forcedOutMemberId)
                .meetingId(meetingId2)
                .build();
        MeetingCommentJpaEntity meetingCommentJpaEntity3 = MeetingCommentJpaEntity.builder()
                .memberId(stillInMemberId1)
                .meetingId(meetingId1)
                .build();
        MeetingCommentJpaEntity meetingCommentJpaEntity4 = MeetingCommentJpaEntity.builder()
                .memberId(stillInMemberId2)
                .meetingId(meetingId2)
                .build();

        meetingCommentRepository.save(meetingCommentJpaEntity1);
        meetingCommentRepository.save(meetingCommentJpaEntity2);
        MeetingCommentJpaEntity savedMeetingComment3 = meetingCommentRepository.save(meetingCommentJpaEntity3);
        MeetingCommentJpaEntity savedMeetingComment4 = meetingCommentRepository.save(meetingCommentJpaEntity4);

        List<Long> meetingIds = List.of(meetingId1, meetingId2);

        // when
        meetingCommentUpdater.updateDeletedMembers(forcedOutMemberId, meetingIds);

        // then
        long findMeetingCommentId1 = meetingCommentRepository.findById(savedMeetingComment3.getId())
                .getMemberId();
        assertThat(findMeetingCommentId1).isEqualTo(stillInMemberId1);

        long findMeetingCommentId2 = meetingCommentRepository.findById(savedMeetingComment4.getId())
                .getMemberId();
        assertThat(findMeetingCommentId2).isEqualTo(stillInMemberId2);
    }
    
    @DisplayName("댓글을 수정한다.")
    @Test
    void updateMeetingComment(){
        //given
        MeetingCommentJpaEntity meetingCommentJpaEntity = MeetingCommentJpaEntity.builder()
                .build();

        meetingCommentRepository.save(meetingCommentJpaEntity);
        long commentId = meetingCommentJpaEntity.getId();
        String commentContents = MEETING_COMMENT_CONTENTS.value();

        MeetingCommentUpdateRequest meetingCommentUpdateRequest = MeetingCommentUpdateRequest.builder()
                .meetingCommentId(commentId)
                .contents(commentContents).build();

        //when
        meetingCommentUpdater.updateMeetingComment(commentId, meetingCommentUpdateRequest);

        //then
        MeetingCommentJpaEntity updatedMeetingComment = meetingCommentRepository.getById(commentId);
        assertThat(updatedMeetingComment.getContents()).isEqualTo(commentContents);
    }
}