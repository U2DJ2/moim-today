package moim_today.implement.mail;

import moim_today.dto.mail.MailSendRequest;
import moim_today.dto.mail.UpcomingMeetingNoticeResponse;
import moim_today.global.annotation.Implement;
import moim_today.implement.meeting.meeting.MeetingFinder;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static moim_today.global.constant.MailConstant.MEETING_INVITATION_EMAIL_SUBJECT;
import static moim_today.global.constant.MailConstant.UPCOMING_MEETING_NOTICE_MAIL;

@Implement
public class MailScheduler {

    private static final int ONE_HOUR_IN_MILLISECONDS = 60 * 60 * 1000;

    private final MailSender mailSender;
    private final MeetingFinder meetingFinder;

    public MailScheduler(final MailSender mailSender, final MeetingFinder meetingFinder) {
        this.mailSender = mailSender;
        this.meetingFinder = meetingFinder;
    }

    @Scheduled(fixedRate = ONE_HOUR_IN_MILLISECONDS)
    @Transactional
    public void sendInvitationMail() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        List<UpcomingMeetingNoticeResponse> upcomingNotices = meetingFinder.findUpcomingNotices(currentDateTime);

        for (UpcomingMeetingNoticeResponse upcomingNotice : upcomingNotices) {
            MailSendRequest mailSendRequest =
                    MailSendRequest.of(MEETING_INVITATION_EMAIL_SUBJECT.value(), List.of(upcomingNotice.email()));
            String data = makeData(upcomingNotice);

            mailSender.send(mailSendRequest, UPCOMING_MEETING_NOTICE_MAIL.value(), data);
        }
    }

    private String makeData(final UpcomingMeetingNoticeResponse upcomingNotice) {
        StringBuilder sb = new StringBuilder();

        sb.append("1. 미팅 의제 : ").append(upcomingNotice.agenda()).append("<br>")
                .append("2. 시작 시간 : ").append(formatDateTime(upcomingNotice.startDateTime())).append("<br>")
                .append("3. 종료 시간 : ").append(formatDateTime(upcomingNotice.endDateTime())).append("<br>")
                .append("4. 미팅 장소 : ").append(upcomingNotice.place()).append("<br>")
                .append("5. 참석 여부 : ").append(upcomingNotice.attendance() ? "참석" : "불참").append("<br>");

        return sb.toString();
    }

    private String formatDateTime(final LocalDateTime dateTime) {
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분");
        return dateTime.format(outputFormatter);
    }
}
