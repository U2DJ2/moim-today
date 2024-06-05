package moim_today.dto.meeting.meeting_comment;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import moim_today.persistence.entity.meeting.meeting_comment.MeetingCommentJpaEntity;

import static moim_today.global.constant.NumberConstant.DEFAULT_PARENT_COMMENT_ID;
import static moim_today.global.constant.exception.ValidationExceptionConstant.MEETING_COMMENT_CONTENT_BLANK_ERROR;
import static moim_today.global.constant.exception.ValidationExceptionConstant.MEETING_ID_MIN_ERROR;

@Builder
public record MeetingCommentCreateRequest (
        @Min(value = 0, message = MEETING_ID_MIN_ERROR) long meetingId,
        @NotBlank(message = MEETING_COMMENT_CONTENT_BLANK_ERROR) String contents
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
