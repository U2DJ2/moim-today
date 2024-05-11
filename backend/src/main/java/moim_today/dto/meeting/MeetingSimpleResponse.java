package moim_today.dto.meeting;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import moim_today.domain.meeting.Meeting;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Builder
public record MeetingSimpleResponse(
        long meetingId,
        String agenda,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate startDate,
        DayOfWeek dayOfWeek,
        long dDay,
        boolean attendance
) {

    public static List<MeetingSimpleResponse> toResponses(final List<MeetingSimpleDao> meetingSimpleDaos) {
        LocalDate currentDate = LocalDate.now();
        List<MeetingSimpleResponse> meetingSimpleResponses = new ArrayList<>();

        for (MeetingSimpleDao meetingSimpleDao : meetingSimpleDaos) {
            long dDay = Meeting.calculateDDay(currentDate, meetingSimpleDao.startDateTime().toLocalDate());
            MeetingSimpleResponse meetingSimpleResponse = toResponse(meetingSimpleDao, dDay);
            meetingSimpleResponses.add(meetingSimpleResponse);
        }

        return meetingSimpleResponses;
    }

    private static MeetingSimpleResponse toResponse(final MeetingSimpleDao meetingSimpleDao, final long dDay) {
        return MeetingSimpleResponse.builder()
                .meetingId(meetingSimpleDao.meetingId())
                .agenda(meetingSimpleDao.agenda())
                .startDate(meetingSimpleDao.startDateTime().toLocalDate())
                .dayOfWeek(meetingSimpleDao.startDateTime().getDayOfWeek())
                .dDay(dDay)
                .attendance(meetingSimpleDao.attendance())
                .build();
    }
}
