package moim_today.dto.meeting;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import moim_today.domain.meeting.Meeting;
import moim_today.persistence.entity.meeting.meeting.MeetingJpaEntity;

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
        long dDay
) {

    public static List<MeetingSimpleResponse> toResponses(final List<MeetingJpaEntity> meetingJpaEntities) {
        LocalDate currentDate = LocalDate.now();
        List<MeetingSimpleResponse> meetingSimpleResponses = new ArrayList<>();

        for (MeetingJpaEntity meetingJpaEntity : meetingJpaEntities) {
            long dDay = Meeting.calculateDDay(currentDate, meetingJpaEntity.getStartDateTime().toLocalDate());
            MeetingSimpleResponse meetingSimpleResponse = toResponse(meetingJpaEntity, dDay);
            meetingSimpleResponses.add(meetingSimpleResponse);
        }

        return meetingSimpleResponses;
    }

    private static MeetingSimpleResponse toResponse(final MeetingJpaEntity meetingJpaEntity, final long dDay) {
        return MeetingSimpleResponse.builder()
                .meetingId(meetingJpaEntity.getId())
                .agenda(meetingJpaEntity.getAgenda())
                .startDate(meetingJpaEntity.getStartDateTime().toLocalDate())
                .dayOfWeek(meetingJpaEntity.getStartDateTime().getDayOfWeek())
                .dDay(dDay)
                .build();
    }
}
