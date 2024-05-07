package moim_today.dto.schedule;

import lombok.Builder;
import moim_today.domain.member.Member;
import moim_today.domain.schedule.AvailableTime;
import moim_today.domain.schedule.enums.AvailableColorHex;
import moim_today.dto.member.MemberSimpleResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
public record AvailableTimeResponse(
        List<MemberSimpleResponse> members,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        String colorHex
) {

    public static List<AvailableTimeResponse> from(final List<AvailableTime> availableTimes) {
        List<AvailableTimeResponse> availableTimeResponses = new ArrayList<>();

        for (AvailableTime availableTime : availableTimes) {
            List<Member> members = availableTime.members();
            AvailableTimeResponse availableTimeResponse = of(availableTime, members);
            availableTimeResponses.add(availableTimeResponse);
        }

        return availableTimeResponses;
    }

    private static AvailableTimeResponse of(final AvailableTime availableTime, final List<Member> members) {
        return AvailableTimeResponse.builder()
                .members(MemberSimpleResponse.of(members))
                .startDateTime(availableTime.startDateTime())
                .endDateTime(availableTime.endDateTime())
                .colorHex(AvailableColorHex.getHexByCount(members.size()))
                .build();
    }
}
