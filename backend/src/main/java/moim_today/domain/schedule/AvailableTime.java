package moim_today.domain.schedule;

import moim_today.domain.member.Member;
import moim_today.dto.schedule.MoimScheduleResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public record AvailableTime(
        List<Member> members,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime
) {

    public static AvailableTime toDomain(final List<Member> members, final LocalDateTime startDateTime,
                                         final LocalDateTime endDateTime) {
        return new AvailableTime(members, startDateTime, endDateTime);
    }

    public static List<AvailableTime> calculateAvailableTimes(final List<MoimScheduleResponse> moimScheduleResponses) {
        // 회원별로 일정을 그룹화하기 위해 Map을 사용
        Map<Long, List<MoimScheduleResponse>> schedulesByMember = moimScheduleResponses.stream()
                .collect(Collectors.groupingBy(MoimScheduleResponse::memberId));

        // 모든 시작 및 종료 시간을 모아 정렬된 고유 리스트 생성
        List<LocalDateTime> allTimes = getAllTimes(moimScheduleResponses);
        List<AvailableTime> availableTimes = new ArrayList<>();

        // 연속된 기간에 대해 반복
        for (int i = 0; i < allTimes.size() - 1; i++) {
            LocalDateTime startDateTime = allTimes.get(i);
            LocalDateTime endDateTime = allTimes.get(i + 1);

            // 해당 기간에 스케줄이 없는 회원을 필터링하여 상세 정보 수집
            List<Member> availableMembers = Member.filterByDateTime(schedulesByMember, startDateTime, endDateTime);

            // 해당 기간에 스케줄이 없는 회원이 있다면 AvailableTime 객체 추가
            if (!availableMembers.isEmpty()) {
                availableTimes.add(AvailableTime.toDomain(availableMembers, startDateTime, endDateTime));
            }
        }

        return availableTimes;
    }

    private static List<LocalDateTime> getAllTimes(final List<MoimScheduleResponse> moimScheduleResponses) {
        return moimScheduleResponses.stream()
                .flatMap(schedule -> Stream.of(schedule.startDateTime(), schedule.endDateTime()))
                .distinct()
                .sorted()
                .toList();
    }
}
