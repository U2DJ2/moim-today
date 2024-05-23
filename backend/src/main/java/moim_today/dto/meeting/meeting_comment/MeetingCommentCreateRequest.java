package moim_today.dto.meeting.meeting_comment;

import lombok.Builder;
import moim_today.persistence.entity.meeting.meeting_comment.MeetingCommentJpaEntity;

import static moim_today.global.constant.NumberConstant.DEFAULT_PARENT_COMMENT_ID;

@Builder
public record MeetingCommentCreateRequest (
        long meetingId,
        String contents
){

    public MeetingCommentJpaEntity toEntity(final long memberId) {
        return MeetingCommentJpaEntity.builder()
                .meetingId(meetingId)
                .parentId(DEFAULT_PARENT_COMMENT_ID.value())
                .memberId(memberId)
                .contents(contents)
                .build();
    }
}
