package moim_today.domain.member;

import moim_today.dto.schedule.MoimScheduleResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static moim_today.util.TestConstant.*;
import static org.assertj.core.api.Assertions.*;

class MemberTest {

    @DisplayName("회원 id로 모임 스케줄 정보를 그룹 짓는다.")
    @Test
    void groupSchedulesByMember() {
        // given
        long memberId = MEMBER_ID.longValue();
        long otherMemberId = 9999L;

        MoimScheduleResponse moimScheduleResponse1 = MoimScheduleResponse.builder()
                .memberId(memberId)
                .build();

        MoimScheduleResponse moimScheduleResponse2 = MoimScheduleResponse.builder()
                .memberId(memberId)
                .build();

        MoimScheduleResponse moimScheduleResponse3 = MoimScheduleResponse.builder()
                .memberId(otherMemberId)
                .build();

        List<MoimScheduleResponse> moimScheduleResponses =
                List.of(moimScheduleResponse1, moimScheduleResponse2, moimScheduleResponse3);

        // when
        Map<Long, List<MoimScheduleResponse>> schedulesByMember = Member.groupSchedulesByMember(moimScheduleResponses);

        // then
        assertThat(schedulesByMember.get(memberId).size()).isEqualTo(2);
        assertThat(schedulesByMember.get(otherMemberId).size()).isEqualTo(1);
    }

    @DisplayName("해당 시간대에 가용시간이 있으면 목록에 포함된다 - 앞 쪽 시간대에만 존재")
    @Test
    void filterByBeforeDateTime() {
        // given
        long memberId = MEMBER_ID.longValue();

        MoimScheduleResponse moimScheduleResponse = MoimScheduleResponse.builder()
                .memberId(memberId)
                .startDateTime(LocalDateTime.of(2024,3,1,10,0,0))
                .endDateTime(LocalDateTime.of(2024,3,1,12,0,0))
                .build();

        List<MoimScheduleResponse> moimScheduleResponses = List.of(moimScheduleResponse);

        // given 2
        Map<Long, List<MoimScheduleResponse>> schedulesByMember = new HashMap<>();
        schedulesByMember.put(memberId, moimScheduleResponses);

        // given 3
        LocalDateTime startDateTime = LocalDateTime.of(2024, 3, 1, 8, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2024, 3, 1, 10, 0, 0);

        // when
        List<Member> members = Member.filterByDateTime(schedulesByMember, startDateTime, endDateTime);

        // then
        assertThat(members.size()).isEqualTo(1);
    }

    @DisplayName("해당 시간대에 가용시간이 있으면 목록에 포함된다 - 뒤 쪽 시간대에만 존재")
    @Test
    void filterByAfterDateTime() {
        // given
        long memberId = MEMBER_ID.longValue();

        MoimScheduleResponse moimScheduleResponse = MoimScheduleResponse.builder()
                .memberId(memberId)
                .startDateTime(LocalDateTime.of(2024,3,1,10,0,0))
                .endDateTime(LocalDateTime.of(2024,3,1,12,0,0))
                .build();

        List<MoimScheduleResponse> moimScheduleResponses = List.of(moimScheduleResponse);

        // given 2
        Map<Long, List<MoimScheduleResponse>> schedulesByMember = new HashMap<>();
        schedulesByMember.put(memberId, moimScheduleResponses);

        // given 3
        LocalDateTime startDateTime = LocalDateTime.of(2024, 3, 1, 12, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2024, 3, 1, 14, 0, 0);

        // when
        List<Member> members = Member.filterByDateTime(schedulesByMember, startDateTime, endDateTime);

        // then
        assertThat(members.size()).isEqualTo(1);
    }

    @DisplayName("해당 시간대에 가용시간이 없으면 목록에 포함된다 - 앞 쪽 시간대와 겹침")
    @Test
    void filterByBeforeDateTimeNotAvailable() {
        // given
        long memberId = MEMBER_ID.longValue();

        MoimScheduleResponse moimScheduleResponse = MoimScheduleResponse.builder()
                .memberId(memberId)
                .startDateTime(LocalDateTime.of(2024,3,1,10,0,0))
                .endDateTime(LocalDateTime.of(2024,3,1,12,0,0))
                .build();

        List<MoimScheduleResponse> moimScheduleResponses = List.of(moimScheduleResponse);

        // given 2
        Map<Long, List<MoimScheduleResponse>> schedulesByMember = new HashMap<>();
        schedulesByMember.put(memberId, moimScheduleResponses);

        // given 3
        LocalDateTime startDateTime = LocalDateTime.of(2024, 3, 1, 8, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2024, 3, 1, 10, 0, 1);

        // when
        List<Member> members = Member.filterByDateTime(schedulesByMember, startDateTime, endDateTime);

        // then
        assertThat(members.size()).isEqualTo(0);
    }

    @DisplayName("해당 시간대에 가용시간이 없으면 목록에 포함된다 - 뒤 쪽 시간대와 겹침")
    @Test
    void filterByAfterDateTimeNotAvailable() {
        // given
        long memberId = MEMBER_ID.longValue();

        MoimScheduleResponse moimScheduleResponse = MoimScheduleResponse.builder()
                .memberId(memberId)
                .startDateTime(LocalDateTime.of(2024,3,1,10,0,0))
                .endDateTime(LocalDateTime.of(2024,3,1,12,0,0))
                .build();

        List<MoimScheduleResponse> moimScheduleResponses = List.of(moimScheduleResponse);

        // given 2
        Map<Long, List<MoimScheduleResponse>> schedulesByMember = new HashMap<>();
        schedulesByMember.put(memberId, moimScheduleResponses);

        // given 3
        LocalDateTime startDateTime = LocalDateTime.of(2024, 3, 1, 11, 59, 59);
        LocalDateTime endDateTime = LocalDateTime.of(2024, 3, 1, 14, 0, 0);

        // when
        List<Member> members = Member.filterByDateTime(schedulesByMember, startDateTime, endDateTime);

        // then
        assertThat(members.size()).isEqualTo(0);
    }
}