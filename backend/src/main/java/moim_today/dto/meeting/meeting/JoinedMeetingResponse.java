package moim_today.dto.meeting.meeting;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import moim_today.domain.meeting.Meeting;

import java.time.LocalDate;
import java.util.List;


@Builder
public record JoinedMeetingResponse(
        long moimId,
        long meetingId,
        String moimTitle,
        String agenda,
        long dDay,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate startDate
) {

    public static List<JoinedMeetingResponse> toResponses(final List<JoinedMeetingDao> joinedMeetingDaos) {
        return joinedMeetingDaos.stream()
                .map(JoinedMeetingResponse::toResponse)
                .toList();
    }

    public static JoinedMeetingResponse toResponse(final JoinedMeetingDao joinedMeetingDao) {
        LocalDate currentDate = LocalDate.now();
        long dDay = Meeting.calculateDDay(currentDate, joinedMeetingDao.startDateTime().toLocalDate());

        return JoinedMeetingResponse.builder()
                .moimId(joinedMeetingDao.moimId())
                .meetingId(joinedMeetingDao.meetingId())
                .moimTitle(joinedMeetingDao.moimTitle())
                .agenda(joinedMeetingDao.agenda())
                .dDay(dDay)
                .startDate(joinedMeetingDao.startDateTime().toLocalDate())
                .build();
    }
}
