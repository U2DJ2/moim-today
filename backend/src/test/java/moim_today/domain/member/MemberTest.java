package moim_today.domain.member;

import moim_today.dto.schedule.MoimScheduleResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static moim_today.util.TestConstant.*;
import static org.assertj.core.api.Assertions.*;

class MemberTest {

    @DisplayName("해당 시간대에 가용시간이 있으면 목록에 포함된다 - 앞 쪽 시간대에만 존재")
    @Test
    void filterByBeforeDateTime() {
        // given 1
        long memberId = MEMBER_ID.longValue();

        MoimScheduleResponse moimScheduleResponse = MoimScheduleResponse.builder()
                .memberId(memberId)
                .startDateTime(LocalDateTime.of(2024,3,1,10,0,0))
                .endDateTime(LocalDateTime.of(2024,3,1,12,0,0))
                .build();

        List<MoimScheduleResponse> moimScheduleResponses = List.of(moimScheduleResponse);

        // given 2
        LocalDateTime startDateTime = LocalDateTime.of(2024, 3, 1, 8, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2024, 3, 1, 10, 0, 0);

        // when
        List<Member> members = Member.filterByDateTime(moimScheduleResponses, startDateTime, endDateTime);

        // then
        assertThat(members.size()).isEqualTo(1);
    }

    @DisplayName("해당 시간대에 가용시간이 있으면 목록에 포함된다 - 뒤 쪽 시간대에만 존재")
    @Test
    void filterByAfterDateTime() {
        // given 1
        long memberId = MEMBER_ID.longValue();

        MoimScheduleResponse moimScheduleResponse = MoimScheduleResponse.builder()
                .memberId(memberId)
                .startDateTime(LocalDateTime.of(2024,3,1,10,0,0))
                .endDateTime(LocalDateTime.of(2024,3,1,12,0,0))
                .build();

        List<MoimScheduleResponse> moimScheduleResponses = List.of(moimScheduleResponse);

        // given 2
        LocalDateTime startDateTime = LocalDateTime.of(2024, 3, 1, 12, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2024, 3, 1, 14, 0, 0);

        // when
        List<Member> members = Member.filterByDateTime(moimScheduleResponses, startDateTime, endDateTime);

        // then
        assertThat(members.size()).isEqualTo(1);
    }

    @DisplayName("해당 시간대에 가용시간이 없으면 목록에 포함된다 - 앞 쪽 시간대와 겹침")
    @Test
    void filterByBeforeDateTimeNotAvailable() {
        // given 1
        long memberId = MEMBER_ID.longValue();

        MoimScheduleResponse moimScheduleResponse = MoimScheduleResponse.builder()
                .memberId(memberId)
                .startDateTime(LocalDateTime.of(2024,3,1,10,0,0))
                .endDateTime(LocalDateTime.of(2024,3,1,12,0,0))
                .build();

        List<MoimScheduleResponse> moimScheduleResponses = List.of(moimScheduleResponse);

        // given 2
        LocalDateTime startDateTime = LocalDateTime.of(2024, 3, 1, 8, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2024, 3, 1, 10, 0, 1);

        // when
        List<Member> members = Member.filterByDateTime(moimScheduleResponses, startDateTime, endDateTime);

        // then
        assertThat(members.size()).isEqualTo(0);
    }

    @DisplayName("해당 시간대에 가용시간이 없으면 목록에 포함된다 - 뒤 쪽 시간대와 겹침")
    @Test
    void filterByAfterDateTimeNotAvailable() {
        // given 1
        long memberId = MEMBER_ID.longValue();

        MoimScheduleResponse moimScheduleResponse = MoimScheduleResponse.builder()
                .memberId(memberId)
                .startDateTime(LocalDateTime.of(2024,3,1,10,0,0))
                .endDateTime(LocalDateTime.of(2024,3,1,12,0,0))
                .build();

        List<MoimScheduleResponse> moimScheduleResponses = List.of(moimScheduleResponse);

        // given 3
        LocalDateTime startDateTime = LocalDateTime.of(2024, 3, 1, 11, 59, 59);
        LocalDateTime endDateTime = LocalDateTime.of(2024, 3, 1, 14, 0, 0);

        // when
        List<Member> members = Member.filterByDateTime(moimScheduleResponses, startDateTime, endDateTime);

        // then
        assertThat(members.size()).isEqualTo(0);
    }
}