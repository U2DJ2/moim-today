package moim_today.domain.member;

import lombok.Builder;
import moim_today.dto.schedule.MoimScheduleResponse;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.*;


@Builder
public record Member(
        long memberId,
        String username,
        String memberProfileImageUrl
) {

    public static List<Member> filterByDateTime(final List<MoimScheduleResponse> moimScheduleResponses,
                                                final LocalDateTime startDateTime, final LocalDateTime endDateTime) {
        return moimScheduleResponses.stream()
                .filter(schedule -> !isScheduleConflicting(schedule, startDateTime, endDateTime))
                .map(MoimScheduleResponse::toDomain)
                .collect(toList());
    }

    private static boolean isScheduleConflicting(final MoimScheduleResponse moimScheduleResponse,
                                                 final LocalDateTime startDateTime, final LocalDateTime endDateTime) {
        return moimScheduleResponse.startDateTime().isBefore(endDateTime)
                && moimScheduleResponse.endDateTime().isAfter(startDateTime);
    }
}
