package moim_today.dto.meeting.meeting;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import moim_today.dto.member.MemberSimpleResponse;
import moim_today.persistence.entity.meeting.meeting.MeetingJpaEntity;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record MeetingDetailResponse(
        long meetingId,
        String agenda,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime startDateTime,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime endDateTime,

        String place,
        List<MemberSimpleResponse> members
) {

    public static MeetingDetailResponse toResponse(final MeetingJpaEntity meetingJpaEntity,
                                                   final List<MemberSimpleResponse> memberSimpleResponses) {
        return MeetingDetailResponse.builder()
                .meetingId(meetingJpaEntity.getId())
                .agenda(meetingJpaEntity.getAgenda())
                .startDateTime(meetingJpaEntity.getStartDateTime())
                .endDateTime(meetingJpaEntity.getEndDateTime())
                .place(meetingJpaEntity.getPlace())
                .members(memberSimpleResponses)
                .build();
    }
}
