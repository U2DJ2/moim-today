package moim_today.domain.member;

import lombok.Builder;
import moim_today.dto.schedule.MoimScheduleResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static moim_today.global.constant.NumberConstant.MOIM_SCHEDULE_FIRST_IDX;

@Builder
public record Member(
        long memberId,
        String username,
        String memberProfileImageUrl
) {

    public static Map<Long, List<MoimScheduleResponse>> groupSchedulesByMember(final List<MoimScheduleResponse> moimScheduleResponses) {

        return moimScheduleResponses.stream()
                .collect(Collectors.groupingBy(MoimScheduleResponse::memberId));
    }

    public static List<Member> filterByDateTime(final Map<Long, List<MoimScheduleResponse>> schedulesByMember,
                                                final LocalDateTime startDateTime, final LocalDateTime endDateTime) {
        return schedulesByMember.values().stream()
                .filter(
                        moimScheduleResponses -> moimScheduleResponses.stream()
                                .noneMatch(
                                        moimScheduleResponse -> isScheduleConflicting(moimScheduleResponse, startDateTime, endDateTime))
                )
                .map(moimScheduleResponses -> {
                    MoimScheduleResponse moimScheduleResponse = moimScheduleResponses.get(MOIM_SCHEDULE_FIRST_IDX.value());
                    return moimScheduleResponse.toDomain();
                })
                .collect(toList());
    }

    private static boolean isScheduleConflicting(final MoimScheduleResponse moimScheduleResponse,
                                                 final LocalDateTime startDateTime, final LocalDateTime endDateTime) {
        return moimScheduleResponse.startDateTime().isBefore(endDateTime)
                && moimScheduleResponse.endDateTime().isAfter(startDateTime);
    }
}
