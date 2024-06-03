package moim_today.dto.meeting.meeting_comment;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import moim_today.persistence.entity.meeting.meeting_comment.MeetingCommentJpaEntity;

import static moim_today.global.constant.NumberConstant.DEFAULT_PARENT_COMMENT_ID;

@Builder
public record MeetingCommentCreateRequest (
        @Min(value = 1, message = MEETING_ID_MIN_ERROR) long meetingId,
        @NotBlank(message = COMMENT_CONTENT_BLANK_ERROR) String contents
){
    private static final String MEETING_ID_MIN_ERROR = "잘못된 미팅 ID 값이 입력 되었습니다.";
    private static final String COMMENT_CONTENT_BLANK_ERROR = "미팅 내용은 공백일 수 없습니다.";

    public MeetingCommentJpaEntity toEntity(final long memberId) {
        return MeetingCommentJpaEntity.builder()
                .meetingId(meetingId)
                .parentId(DEFAULT_PARENT_COMMENT_ID.value())
                .memberId(memberId)
                .contents(contents)
                .build();
    }
}
