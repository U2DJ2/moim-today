package moim_today.persistence.repository.meeting.meeting_comment;

import java.util.List;

public interface MeetingCommentRepository {

    void updateDeletedMembers(final long memberId, final List<Long> meetingIds);
}
