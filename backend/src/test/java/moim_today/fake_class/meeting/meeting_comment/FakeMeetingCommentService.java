package moim_today.fake_class.meeting.meeting_comment;

import moim_today.application.meeting.meeting_comment.MeetingCommentService;
import moim_today.dto.meeting.meeting_comment.MeetingCommentCreateRequest;
import moim_today.dto.meeting.meeting_comment.MeetingCommentResponse;
import moim_today.dto.meeting.meeting_comment.MeetingCommentUpdateRequest;
import moim_today.global.error.NotFoundException;
import moim_today.util.TestConstant;

import java.time.LocalDateTime;
import java.util.List;

import static moim_today.global.constant.exception.MeetingExceptionConstant.MEETING_NOT_FOUND_ERROR;
import static moim_today.util.TestConstant.*;

public class FakeMeetingCommentService implements MeetingCommentService {

    @Override
    public void createMeetingComment(final long memberId, final MeetingCommentCreateRequest meetingCommentCreateRequest) {
        if (meetingCommentCreateRequest.meetingId() == NOT_FOUND_MEETING_ID.longValue()) {
            throw new NotFoundException(MEETING_NOT_FOUND_ERROR.message());
        }
    }

    @Override
    public List<MeetingCommentResponse> findAllByMeetingId(final long memberId, final long meetingId) {
        MeetingCommentResponse commentResponse1 = MeetingCommentResponse.builder()
                .memberId(MEMBER_ID.longValue())
                .meetingCommentId(MEETING_COMMENT_ID.longValue())
                .username(USERNAME.value())
                .imageUrl(PROFILE_IMAGE_URL.value())
                .contents(MEETING_COMMENT_CONTENTS.value())
                .createdAt(LocalDateTime.now())
                .build();

        MeetingCommentResponse commentResponse2 = MeetingCommentResponse.builder()
                .memberId(MEMBER_ID.longValue() + 1)
                .meetingCommentId(MEETING_COMMENT_ID.longValue() + 1)
                .username(USERNAME.value())
                .imageUrl(PROFILE_IMAGE_URL.value())
                .contents(MEETING_COMMENT_CONTENTS.value())
                .createdAt(LocalDateTime.now())
                .build();

        MeetingCommentResponse commentResponse3 = MeetingCommentResponse.builder()
                .memberId(MEMBER_ID.longValue() + 2)
                .meetingCommentId(MEETING_COMMENT_ID.longValue() + 2)
                .username(USERNAME.value())
                .imageUrl(PROFILE_IMAGE_URL.value())
                .contents(MEETING_COMMENT_CONTENTS.value())
                .createdAt(LocalDateTime.now())
                .build();

        return List.of(commentResponse1, commentResponse2, commentResponse3);
    }

    @Override
    public void updateMeetingComment(final long memberId, final MeetingCommentUpdateRequest meetingCommentUpdateRequest) {

    }

    @Override
    public void deleteMeetingComment(final long memberId, final long meetingCommentId) {

    }
}
