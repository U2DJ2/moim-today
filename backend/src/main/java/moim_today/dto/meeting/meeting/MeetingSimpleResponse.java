package moim_today.dto.meeting.meeting;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import moim_today.domain.meeting.Meeting;

import java.time.LocalDate;


@Builder
public record MeetingSimpleResponse(
        long meetingId,
        String agenda,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate startDate,
        long dDay,
        boolean attendance,
        boolean joinAvailability
) {

    public static MeetingSimpleResponse toResponse(final MeetingSimpleDao meetingSimpleDao,
                                                   final boolean joinAvailability) {
        LocalDate currentDate = LocalDate.now();
        long dDay = Meeting.calculateDDay(currentDate, meetingSimpleDao.startDateTime().toLocalDate());

        return MeetingSimpleResponse.builder()
                .meetingId(meetingSimpleDao.meetingId())
                .agenda(meetingSimpleDao.agenda())
                .startDate(meetingSimpleDao.startDateTime().toLocalDate())
                .dDay(dDay)
                .attendance(meetingSimpleDao.attendance())
                .joinAvailability(joinAvailability)
                .build();
    }
}
