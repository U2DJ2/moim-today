package moim_today.dto.schedule;

import moim_today.dto.member.MemberSimpleResponse;

import java.time.LocalDateTime;
import java.util.List;

public record AvailableTimeResponse(
        List<MemberSimpleResponse> members,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime
) {
}
