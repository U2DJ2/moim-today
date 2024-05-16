package moim_today.persistence.entity.meeting.meeting_comment;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import moim_today.dto.meeting.meeting_comment.MeetingCommentUpdateRequest;
import moim_today.global.annotation.Association;
import moim_today.global.base_entity.BaseTimeEntity;
import moim_today.global.error.ForbiddenException;

import static moim_today.global.constant.exception.MeetingExceptionConstant.MEETING_COMMENT_FORBIDDEN_ERROR;

@Getter
@Table(name = "meeting_comment")
@Entity
public class MeetingCommentJpaEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_comment_id")
    private long id;

    @Association
    private long meetingId;

    @Association
    private long parentId;

    @Association
    private long memberId;

    private String contents;

    protected MeetingCommentJpaEntity() {
    }

    @Builder
    private MeetingCommentJpaEntity(final long meetingId, final long parentId,
                                    final long memberId, final String contents) {
        this.meetingId = meetingId;
        this.parentId = parentId;
        this.memberId = memberId;
        this.contents = contents;
    }

    public void validateMember(final long memberId) {
        if(this.memberId != memberId){
            throw new ForbiddenException(MEETING_COMMENT_FORBIDDEN_ERROR.message());
        }
    }

    public void updateMeetingComment(final MeetingCommentUpdateRequest meetingCommentUpdateRequest) {
        this.contents = meetingCommentUpdateRequest.contents();
    }
}
