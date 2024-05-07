package moim_today.domain.schedule;

import moim_today.dto.schedule.MoimScheduleResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static moim_today.util.TestConstant.*;
import static org.assertj.core.api.Assertions.*;

class AvailableTimeTest {

    @DisplayName("모임의 스케줄 정보를 입력받아 각 시간대별 가용시간 목록을 받는다 - 중복 시간대 없음")
    @Test
    void calculateAvailableTimes() {
        // given 1
        MoimScheduleResponse moimScheduleResponse1 = MoimScheduleResponse.builder()
                .scheduleId(1)
                .meetingId(1)
                .memberId(1)
                .username(USERNAME.value())
                .memberProfileImageUrl(PROFILE_IMAGE_URL.value())
                .scheduleName(SCHEDULE_NAME.value())
                .startDateTime(LocalDateTime.of(2024,3,4,10,0,0))
                .endDateTime(LocalDateTime.of(2024,3,4,12,0,0))
                .build();

        MoimScheduleResponse moimScheduleResponse2 = MoimScheduleResponse.builder()
                .scheduleId(2)
                .meetingId(2)
                .memberId(2)
                .username(USERNAME.value())
                .memberProfileImageUrl(PROFILE_IMAGE_URL.value())
                .scheduleName(SCHEDULE_NAME.value())
                .startDateTime(LocalDateTime.of(2024,3,4,14,0,0))
                .endDateTime(LocalDateTime.of(2024,3,4,16,0,0))
                .build();

        MoimScheduleResponse moimScheduleResponse3 = MoimScheduleResponse.builder()
                .scheduleId(3)
                .meetingId(3)
                .memberId(3)
                .username(USERNAME.value())
                .memberProfileImageUrl(PROFILE_IMAGE_URL.value())
                .scheduleName(SCHEDULE_NAME.value())
                .startDateTime(LocalDateTime.of(2024,3,4,18,0,0))
                .endDateTime(LocalDateTime.of(2024,3,4,20,0,0))
                .build();

        List<MoimScheduleResponse> moimScheduleResponses =
                List.of(moimScheduleResponse3, moimScheduleResponse2, moimScheduleResponse1);

        // when
        List<AvailableTime> availableTimes =
                AvailableTime.calculateAvailableTimes(
                        moimScheduleResponses,
                        LocalDate.of(2024, 3, 3)
                );

        // then
        int startPoint = 1;
        int scheduleCount = 5;
        int endPoint = 1;

        assertThat(availableTimes.size()).isEqualTo(startPoint + scheduleCount + endPoint);
    }

    @DisplayName("모임의 스케줄 정보를 입력받아 각 시간대별 가용시간 목록을 받는다 - 중복 시간대 포함")
    @Test
    void calculateDuplicationAvailableTimes() {
        // given 1
        MoimScheduleResponse moimScheduleResponse1 = MoimScheduleResponse.builder()
                .scheduleId(1)
                .meetingId(1)
                .memberId(1)
                .username(USERNAME.value())
                .memberProfileImageUrl(PROFILE_IMAGE_URL.value())
                .scheduleName(SCHEDULE_NAME.value())
                .startDateTime(LocalDateTime.of(2024,3,4,10,0,0))
                .endDateTime(LocalDateTime.of(2024,3,4,12,0,0))
                .build();

        MoimScheduleResponse moimScheduleResponse2 = MoimScheduleResponse.builder()
                .scheduleId(2)
                .meetingId(2)
                .memberId(2)
                .username(USERNAME.value())
                .memberProfileImageUrl(PROFILE_IMAGE_URL.value())
                .scheduleName(SCHEDULE_NAME.value())
                .startDateTime(LocalDateTime.of(2024,3,4,11,0,0))
                .endDateTime(LocalDateTime.of(2024,3,4,13,0,0))
                .build();

        MoimScheduleResponse moimScheduleResponse3 = MoimScheduleResponse.builder()
                .scheduleId(3)
                .meetingId(3)
                .memberId(3)
                .username(USERNAME.value())
                .memberProfileImageUrl(PROFILE_IMAGE_URL.value())
                .scheduleName(SCHEDULE_NAME.value())
                .startDateTime(LocalDateTime.of(2024,3,4,12,0,0))
                .endDateTime(LocalDateTime.of(2024,3,4,14,0,0))
                .build();

        List<MoimScheduleResponse> moimScheduleResponses =
                List.of(moimScheduleResponse3, moimScheduleResponse2, moimScheduleResponse1);

        // when
        List<AvailableTime> availableTimes =
                AvailableTime.calculateAvailableTimes(
                        moimScheduleResponses,
                        LocalDate.of(2024, 3, 3)
                );

        // then
        int startPoint = 1;
        int scheduleCount = 4;
        int endPoint = 1;

        assertThat(availableTimes.size()).isEqualTo(startPoint + scheduleCount + endPoint);
    }
}