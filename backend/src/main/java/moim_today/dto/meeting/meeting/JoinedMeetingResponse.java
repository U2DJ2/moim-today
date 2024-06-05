package moim_today.dto.meeting.meeting;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.*;

@Builder
public record JoinedMeetingResponse(
        long meetingId,
        String agenda,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate startDate
) {

    public static List<JoinedMeetingResponse> toResponses(final List<JoinedMeetingDao> joinedMeetingDaos) {
        return joinedMeetingDaos.stream()
                .map(JoinedMeetingResponse::toResponse)
                .collect(toList());
    }

    public static JoinedMeetingResponse toResponse(final JoinedMeetingDao joinedMeetingDao) {
        return JoinedMeetingResponse.builder()
                .meetingId(joinedMeetingDao.meetingId())
                .agenda(joinedMeetingDao.agenda())
                .startDate(joinedMeetingDao.startDateTime().toLocalDate())
                .build();
    }
}
