package moim_today.application.meeting.meeting_comment;

import moim_today.implement.meeting.meeting_comment.MeetingCommentAppender;
import moim_today.implement.meeting.meeting_comment.MeetingCommentFinder;
import moim_today.implement.meeting.meeting_comment.MeetingCommentRemover;
import moim_today.implement.meeting.meeting_comment.MeetingCommentUpdater;
import org.springframework.stereotype.Service;

@Service
public class MeetingCommentServiceImpl implements MeetingCommentService {

    private final MeetingCommentAppender meetingCommentAppender;
    private final MeetingCommentFinder meetingCommentFinder;
    private final MeetingCommentUpdater meetingCommentUpdater;
    private final MeetingCommentRemover meetingCommentRemover;

    public MeetingCommentServiceImpl(final MeetingCommentAppender meetingCommentAppender, final MeetingCommentFinder meetingCommentFinder, final MeetingCommentUpdater meetingCommentUpdater, final MeetingCommentRemover meetingCommentRemover) {
        this.meetingCommentAppender = meetingCommentAppender;
        this.meetingCommentFinder = meetingCommentFinder;
        this.meetingCommentUpdater = meetingCommentUpdater;
        this.meetingCommentRemover = meetingCommentRemover;
    }
}
