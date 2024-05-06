package moim_today.domain.schedule;

import moim_today.dto.member.MemberSimpleResponse;
import moim_today.dto.schedule.AvailableTimeResponse;
import moim_today.dto.schedule.MoimScheduleResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public record AvailableTime(
        List<MemberSimpleResponse> members,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime
) {

    public static List<AvailableTimeResponse> calculateAvailableTimes(final List<MoimScheduleResponse> moimScheduleResponses) {
        // 회원별로 일정을 그룹화하기 위해 Map을 사용
        Map<Long, List<MoimScheduleResponse>> schedulesByMember = moimScheduleResponses.stream()
                .collect(Collectors.groupingBy(MoimScheduleResponse::memberId));

        // 모든 시작 및 종료 시간을 모아 정렬된 고유 리스트 생성
        List<LocalDateTime> allTimes = moimScheduleResponses.stream()
                .flatMap(schedule -> Stream.of(schedule.startDateTime(), schedule.endDateTime()))
                .distinct()
                .sorted()
                .toList();

        List<AvailableTimeResponse> availableTimes = new ArrayList<>();

        // 연속된 기간에 대해 반복
        for (int i = 0; i < allTimes.size() - 1; i++) {
            LocalDateTime start = allTimes.get(i);
            LocalDateTime end = allTimes.get(i + 1);

            // 해당 기간에 스케줄이 없는 회원을 필터링하여 상세 정보 수집
            List<MemberSimpleResponse> availableMembers = schedulesByMember.values().stream()
                    .filter(scheduleResponses -> scheduleResponses.stream().noneMatch(schedule ->
                            schedule.startDateTime().isBefore(end) && schedule.endDateTime().isAfter(start)))
                    .map(scheduleResponses -> {
                        // 해당 회원의 일정으로부터 상세 정보 추출
                        MoimScheduleResponse firstSchedule = scheduleResponses.get(0);
                        return new MemberSimpleResponse(
                                firstSchedule.memberId(), firstSchedule.username(), firstSchedule.memberProfileImageUrl()
                        );
                    })
                    .collect(toList());

            // 해당 기간에 스케줄이 없는 회원이 있다면 AvailableTime 객체 추가
            if (!availableMembers.isEmpty()) {
                availableTimes.add(new AvailableTimeResponse(availableMembers, start, end));
            }
        }

        return availableTimes;
    }
}
