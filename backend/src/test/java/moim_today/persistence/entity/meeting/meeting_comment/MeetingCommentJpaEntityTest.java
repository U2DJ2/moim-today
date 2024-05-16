package moim_today.persistence.entity.meeting.meeting_comment;

import moim_today.global.error.ForbiddenException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static moim_today.global.constant.exception.MeetingExceptionConstant.MEETING_COMMENT_FORBIDDEN_ERROR;
import static moim_today.util.TestConstant.MEMBER_ID;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MeetingCommentJpaEntityTest {

    @DisplayName("댓글 작성자가 맞다면 검증에 성공한다.")
    @Test
    void validateMember() {
        //given
        long memberId = MEMBER_ID.longValue();
        MeetingCommentJpaEntity meetingCommentJpaEntity = MeetingCommentJpaEntity.builder()
                .memberId(memberId)
                .build();

        //expected
        assertThatCode(() -> meetingCommentJpaEntity.validateMember(memberId))
                .doesNotThrowAnyException();
    }

    @DisplayName("댓글 작성자가 아니라면 검증에 실패한다.")
    @Test
    void validateMemberFailure() {
        //given
        long memberId = MEMBER_ID.longValue();
        long otherMemberId = MEMBER_ID.longValue() + 1;
        MeetingCommentJpaEntity meetingCommentJpaEntity = MeetingCommentJpaEntity.builder()
                .memberId(memberId)
                .build();

        //expected
        assertThatThrownBy(() -> meetingCommentJpaEntity.validateMember(otherMemberId))
                .isInstanceOf(ForbiddenException.class)
                .hasMessage(MEETING_COMMENT_FORBIDDEN_ERROR.message());
    }

}