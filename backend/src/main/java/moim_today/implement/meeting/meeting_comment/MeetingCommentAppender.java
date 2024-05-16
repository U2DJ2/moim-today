package moim_today.implement.meeting.meeting_comment;

import moim_today.global.annotation.Implement;
import moim_today.persistence.repository.meeting.meeting_comment.MeetingCommentRepository;

@Implement
public class MeetingCommentAppender {

    private final MeetingCommentRepository meetingCommentRepository;

    public MeetingCommentAppender(final MeetingCommentRepository meetingCommentRepository) {
        this.meetingCommentRepository = meetingCommentRepository;
    }
}
