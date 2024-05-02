package moim_today.dto.meeting;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import moim_today.domain.meeting.enums.MeetingCategory;
import moim_today.persistence.entity.meeting.meeting.MeetingJpaEntity;

import java.time.LocalDateTime;

@Builder
public record MeetingCreateRequest(
        long moimId,
        String agenda,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime startDateTime,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime endDateTime,

        String place,
        MeetingCategory meetingCategory
) {

    public MeetingJpaEntity toEntity() {
        return MeetingJpaEntity.builder()
                .moimId(moimId)
                .agenda(agenda)
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .place(place)
                .build();
    }
}
