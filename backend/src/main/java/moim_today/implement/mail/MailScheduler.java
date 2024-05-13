package moim_today.implement.mail;

import moim_today.dto.mail.MailSendRequest;
import moim_today.dto.mail.UpcomingMeetingNoticeResponse;
import moim_today.global.annotation.Implement;
import moim_today.implement.meeting.joined_meeting.JoinedMeetingUpdater;
import moim_today.implement.meeting.meeting.MeetingFinder;

import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static moim_today.global.constant.MailConstant.*;

@Implement
public class MailScheduler {

    private static final int ONE_HOUR_IN_MILLISECONDS = 60 * 60 * 1000;

    private final MailSender mailSender;
    private final MeetingFinder meetingFinder;
    private final JoinedMeetingUpdater joinedMeetingUpdater;

    public MailScheduler(final MailSender mailSender, final MeetingFinder meetingFinder,
                         final JoinedMeetingUpdater joinedMeetingUpdater) {
        this.mailSender = mailSender;
        this.meetingFinder = meetingFinder;
        this.joinedMeetingUpdater = joinedMeetingUpdater;
    }

    @Scheduled(fixedRate = ONE_HOUR_IN_MILLISECONDS)
    public void sendInvitationMail() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        List<UpcomingMeetingNoticeResponse> upcomingNotices = meetingFinder.findUpcomingNotices(currentDateTime);

        for (UpcomingMeetingNoticeResponse upcomingNotice : upcomingNotices) {
            MailSendRequest mailSendRequest =
                    MailSendRequest.of(MEETING_INVITATION_EMAIL_SUBJECT.value(), List.of(upcomingNotice.email()));
            String data = makeData(upcomingNotice);

            mailSender.send(mailSendRequest, UPCOMING_MEETING_NOTICE_MAIL.value(), data);
            joinedMeetingUpdater.updateUpcomingNoticeSent(upcomingNotice.joinedMeetingId(), true);
        }
    }

    private String makeData(final UpcomingMeetingNoticeResponse upcomingNotice) {
        StringBuilder sb = new StringBuilder();

        sb.append(MEETING_AGENDA.value()).append(upcomingNotice.agenda()).append(MAIL_LINE_BREAK.value())
                .append(MEETING_START_DATE_TIME.value()).append(formatDateTime(upcomingNotice.startDateTime())).append(MAIL_LINE_BREAK.value())
                .append(MEETING_END_DATE_TIME.value()).append(formatDateTime(upcomingNotice.endDateTime())).append(MAIL_LINE_BREAK.value())
                .append(MEETING_PLACE.value()).append(upcomingNotice.place()).append(MAIL_LINE_BREAK.value())
                .append(MEETING_ATTENDANCE.value()).append(upcomingNotice.attendance() ? ATTENDANCE.value() : ABSENCE.value())
                .append(MAIL_LINE_BREAK.value());

        return sb.toString();
    }

    private String formatDateTime(final LocalDateTime dateTime) {
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(MAIL_DATE_TIME_FORMAT.value());
        return dateTime.format(outputFormatter);
    }
}
