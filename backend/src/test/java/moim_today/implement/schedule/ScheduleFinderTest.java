package moim_today.implement.schedule;

import moim_today.dto.schedule.ScheduleResponse;
import moim_today.persistence.entity.schedule.ScheduleJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ScheduleFinderTest extends ImplementTest {

    @Autowired
    private ScheduleFinder scheduleFinder;

    @DisplayName("다른 회원의 Weekly 스케줄은 조회되지 않는다.")
    @Test
    void findAllByWeeklyOnlyMember() {
        // given
        long memberId = 1L;
        long otherMemberId = 9999L;
        LocalDate startDate = LocalDate.of(2024, 3, 1);

        ScheduleJpaEntity scheduleJpaEntity1 = ScheduleJpaEntity.builder()
                .memberId(memberId)
                .scheduleName("스케줄명 1")
                .startDateTime(LocalDateTime.of(2024, 3, 2, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 2, 12, 0, 0))
                .build();

        ScheduleJpaEntity scheduleJpaEntity2 = ScheduleJpaEntity.builder()
                .memberId(memberId)
                .scheduleName("스케줄명 2")
                .startDateTime(LocalDateTime.of(2024, 3, 3, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 3, 12, 0, 0))
                .build();

        ScheduleJpaEntity scheduleJpaEntity3 = ScheduleJpaEntity.builder()
                .memberId(otherMemberId)
                .scheduleName("스케줄명 3")
                .startDateTime(LocalDateTime.of(2024, 3, 4, 0, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 4, 2, 0, 0))
                .build();

        scheduleRepository.save(scheduleJpaEntity1);
        scheduleRepository.save(scheduleJpaEntity2);
        scheduleRepository.save(scheduleJpaEntity3);

        // when
        List<ScheduleResponse> scheduleResponses = scheduleFinder.findAllByWeekly(memberId, startDate);

        // then
        assertThat(scheduleResponses.size()).isEqualTo(2);
    }

    @DisplayName("Weekly 스케줄 조회는 시작 날짜의 자정부터 조회된다.")
    @Test
    void findAllByWeeklyStartWithMidNight() {
        // given
        long memberId = 1L;
        LocalDate startDate = LocalDate.of(2024, 3, 1);

        ScheduleJpaEntity beforeScheduleJpaEntity = ScheduleJpaEntity.builder()
                .memberId(memberId)
                .scheduleName("스케줄명 1")
                .startDateTime(LocalDateTime.of(2024, 2, 29, 22, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 2, 29, 23, 59, 59))
                .build();

        ScheduleJpaEntity scheduleJpaEntity = ScheduleJpaEntity.builder()
                .memberId(memberId)
                .scheduleName("스케줄명 2")
                .startDateTime(LocalDateTime.of(2024, 3, 1, 0, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 1, 2, 0, 0))
                .build();

        scheduleRepository.save(beforeScheduleJpaEntity);
        scheduleRepository.save(scheduleJpaEntity);

        // when
        List<ScheduleResponse> scheduleResponses = scheduleFinder.findAllByWeekly(memberId, startDate);

        // then
        assertThat(scheduleResponses.size()).isEqualTo(1);
    }

    @DisplayName("Weekly 스케줄 조회는 시작 날짜의 7일 후 자정전까지 조회된다.")
    @Test
    void findAllByWeeklyUntilNextMonthMidNight() {
        // given
        long memberId = 1L;
        LocalDate startDate = LocalDate.of(2024, 3, 1);

        ScheduleJpaEntity scheduleJpaEntity = ScheduleJpaEntity.builder()
                .memberId(memberId)
                .scheduleName("스케줄명 1")
                .startDateTime(LocalDateTime.of(2024, 3, 6, 22, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 6, 23, 59, 59))
                .build();

        ScheduleJpaEntity afterScheduleJpaEntity = ScheduleJpaEntity.builder()
                .memberId(memberId)
                .scheduleName("스케줄명 2")
                .startDateTime(LocalDateTime.of(2024, 3, 7, 0, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 7, 2, 0, 0))
                .build();

        scheduleRepository.save(scheduleJpaEntity);

        // when
        List<ScheduleResponse> scheduleResponses = scheduleFinder.findAllByWeekly(memberId, startDate);

        // then
        assertThat(scheduleResponses.size()).isEqualTo(1);
    }

    @DisplayName("캘린더에 나타낼 Weekly 스케줄을 조회한다.")
    @Test
    void findAllByWeekly() {
        // given
        long memberId = 1L;
        LocalDate startDate = LocalDate.of(2024, 3, 1);

        ScheduleJpaEntity scheduleJpaEntity1 = ScheduleJpaEntity.builder()
                .memberId(memberId)
                .scheduleName("스케줄명 1")
                .startDateTime(LocalDateTime.of(2024, 3, 1, 0, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 1, 2, 0, 0))
                .build();

        ScheduleJpaEntity scheduleJpaEntity2 = ScheduleJpaEntity.builder()
                .memberId(memberId)
                .scheduleName("스케줄명 2")
                .startDateTime(LocalDateTime.of(2024, 3, 3, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 3, 12, 0, 0))
                .build();

        ScheduleJpaEntity scheduleJpaEntity3 = ScheduleJpaEntity.builder()
                .memberId(memberId)
                .scheduleName("스케줄명 3")
                .startDateTime(LocalDateTime.of(2024, 3, 6, 22, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 6, 23, 59, 29))
                .build();

        scheduleRepository.save(scheduleJpaEntity1);
        scheduleRepository.save(scheduleJpaEntity2);
        scheduleRepository.save(scheduleJpaEntity3);

        // when
        List<ScheduleResponse> scheduleResponses = scheduleFinder.findAllByWeekly(memberId, startDate);

        // then
        assertThat(scheduleResponses.size()).isEqualTo(3);
    }

    @DisplayName("다른 회원의 Monthly 스케줄은 조회되지 않는다.")
    @Test
    void findAllByMonthlyOnlyMember() {
        // given
        long memberId = 1L;
        long otherMemberId = 9999L;
        YearMonth yearMonth = YearMonth.of(2024, 3);

        ScheduleJpaEntity scheduleJpaEntity1 = ScheduleJpaEntity.builder()
                .memberId(memberId)
                .scheduleName("스케줄명 1")
                .startDateTime(LocalDateTime.of(2024, 3, 2, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 2, 12, 0, 0))
                .build();

        ScheduleJpaEntity scheduleJpaEntity2 = ScheduleJpaEntity.builder()
                .memberId(memberId)
                .scheduleName("스케줄명 2")
                .startDateTime(LocalDateTime.of(2024, 3, 3, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 3, 12, 0, 0))
                .build();

        ScheduleJpaEntity scheduleJpaEntity3 = ScheduleJpaEntity.builder()
                .memberId(otherMemberId)
                .scheduleName("스케줄명 3")
                .startDateTime(LocalDateTime.of(2024, 3, 4, 0, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 4, 2, 0, 0))
                .build();

        scheduleRepository.save(scheduleJpaEntity1);
        scheduleRepository.save(scheduleJpaEntity2);
        scheduleRepository.save(scheduleJpaEntity3);

        // when
        List<ScheduleResponse> scheduleResponses = scheduleFinder.findAllByMonthly(memberId, yearMonth);

        // then
        assertThat(scheduleResponses.size()).isEqualTo(2);
    }

    @DisplayName("Monthly 스케줄 조회는 당월 1일 자정부터 조회된다.")
    @Test
    void findAllByMonthlyStartWithMidNight() {
        // given
        long memberId = 1L;
        YearMonth yearMonth = YearMonth.of(2024, 3);

        ScheduleJpaEntity befroeScheduleJpaEntity = ScheduleJpaEntity.builder()
                .memberId(memberId)
                .scheduleName("스케줄명 1")
                .startDateTime(LocalDateTime.of(2024, 2, 29, 22, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 2, 29, 23, 59, 59))
                .build();

        ScheduleJpaEntity scheduleJpaEntity = ScheduleJpaEntity.builder()
                .memberId(memberId)
                .scheduleName("스케줄명 2")
                .startDateTime(LocalDateTime.of(2024, 3, 1, 0, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 1, 2, 0, 0))
                .build();

        scheduleRepository.save(befroeScheduleJpaEntity);
        scheduleRepository.save(scheduleJpaEntity);

        // when
        List<ScheduleResponse> scheduleResponses = scheduleFinder.findAllByMonthly(memberId, yearMonth);

        // then
        assertThat(scheduleResponses.size()).isEqualTo(1);
    }

    @DisplayName("한 달 스케줄 조회는 다음달 1일 자정전까지 조회된다.")
    @Test
    void findAllByMonthlyUntilNextMonthMidNight() {
        // given
        long memberId = 1L;
        YearMonth yearMonth = YearMonth.of(2024, 3);

        ScheduleJpaEntity afterScheduleJpaEntity = ScheduleJpaEntity.builder()
                .memberId(memberId)
                .scheduleName("스케줄명 1")
                .startDateTime(LocalDateTime.of(2024, 3, 31, 22, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 31, 23, 59, 59))
                .build();

        ScheduleJpaEntity scheduleJpaEntity = ScheduleJpaEntity.builder()
                .memberId(memberId)
                .scheduleName("스케줄명 1")
                .startDateTime(LocalDateTime.of(2024, 4, 1, 0, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 4, 1, 2, 0, 0))
                .build();


        scheduleRepository.save(afterScheduleJpaEntity);
        scheduleRepository.save(scheduleJpaEntity);

        // when
        List<ScheduleResponse> scheduleResponses = scheduleFinder.findAllByMonthly(memberId, yearMonth);

        // then
        assertThat(scheduleResponses.size()).isEqualTo(1);
    }

    @DisplayName("캘린더에 나타낼 Monthly 스케줄을 조회한다.")
    @Test
    void findAllByMonthly() {
        // given
        long memberId = 1L;
        YearMonth yearMonth = YearMonth.of(2024, 3);

        ScheduleJpaEntity scheduleJpaEntity1 = ScheduleJpaEntity.builder()
                .memberId(memberId)
                .scheduleName("스케줄명 1")
                .startDateTime(LocalDateTime.of(2024, 3, 1, 0, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 1, 2, 0, 0))
                .build();

        ScheduleJpaEntity scheduleJpaEntity2 = ScheduleJpaEntity.builder()
                .memberId(memberId)
                .scheduleName("스케줄명 2")
                .startDateTime(LocalDateTime.of(2024, 3, 15, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 15, 12, 0, 0))
                .build();

        ScheduleJpaEntity scheduleJpaEntity3 = ScheduleJpaEntity.builder()
                .memberId(memberId)
                .scheduleName("스케줄명 3")
                .startDateTime(LocalDateTime.of(2024, 3, 31, 23, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 31, 23, 59, 29))
                .build();

        scheduleRepository.save(scheduleJpaEntity1);
        scheduleRepository.save(scheduleJpaEntity2);
        scheduleRepository.save(scheduleJpaEntity3);

        // when
        List<ScheduleResponse> scheduleResponses = scheduleFinder.findAllByMonthly(memberId, yearMonth);

        // then
        assertThat(scheduleResponses.size()).isEqualTo(3);
    }
}