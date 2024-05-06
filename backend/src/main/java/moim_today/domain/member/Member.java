package moim_today.domain.member;

import moim_today.dto.schedule.MoimScheduleResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public record Member(
        long memberId,
        String username,
        String memberProfileImageUrl
) {

    public static Member toDomain(final long memberId, final String username, final String memberProfileImageUrl) {
        return new Member(memberId, username, memberProfileImageUrl);
    }

    public static List<Member> filterByDateTime(final Map<Long, List<MoimScheduleResponse>> schedulesByMember,
                                                final LocalDateTime startDateTime, final LocalDateTime endDateTime) {
        return schedulesByMember.values().stream()
                .filter(scheduleResponses -> scheduleResponses.stream().noneMatch(schedule ->
                        schedule.startDateTime().isBefore(endDateTime) && schedule.endDateTime().isAfter(startDateTime)))
                .map(scheduleResponses -> {
                    // 해당 회원의 일정으로부터 상세 정보 추출
                    MoimScheduleResponse firstSchedule = scheduleResponses.get(0);
                    return Member.toDomain(
                            firstSchedule.memberId(), firstSchedule.username(), firstSchedule.memberProfileImageUrl()
                    );
                })
                .collect(toList());
    }
}
