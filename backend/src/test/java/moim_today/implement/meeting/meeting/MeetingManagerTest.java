package moim_today.implement.meeting.meeting;

import moim_today.domain.meeting.enums.MeetingCategory;
import moim_today.dto.meeting.MeetingCreateRequest;
import moim_today.dto.meeting.MeetingCreateResponse;
import moim_today.persistence.entity.moim.joined_moim.JoinedMoimJpaEntity;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import moim_today.persistence.entity.schedule.schedule.ScheduleJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static moim_today.util.TestConstant.*;
import static moim_today.util.TestConstant.MEETING_AGENDA;
import static moim_today.util.TestConstant.MEETING_PLACE;
import static org.assertj.core.api.Assertions.*;

class MeetingManagerTest extends ImplementTest {

    @Autowired
    private MeetingManager meetingManager;

    @DisplayName("단일 미팅을 생성한다.")
    @Test
    void createSingleMeeting() {
        // given 1
        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .title(MOIM_TITLE.value())
                .build();

        moimRepository.save(moimJpaEntity);

        // given 2
        MeetingCreateRequest meetingCreateRequest = MeetingCreateRequest.builder()
                .moimId(moimJpaEntity.getId())
                .agenda(MEETING_AGENDA.value())
                .startDateTime(LocalDateTime.of(2024, 3, 4, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 4, 12, 0, 0))
                .place(MEETING_PLACE.value())
                .meetingCategory(MeetingCategory.SINGLE)
                .build();

        // when
        MeetingCreateResponse meetingCreateResponse = meetingManager.createMeeting(meetingCreateRequest);

        // then
        assertThat(meetingRepository.count()).isEqualTo(1);
        assertThat(meetingCreateResponse.agenda()).isEqualTo(MEETING_AGENDA.value());
        assertThat(meetingCreateResponse.startDateTime()).isEqualTo(LocalDateTime.of(2024, 3, 4, 10, 0, 0));
        assertThat(meetingCreateResponse.endDateTime()).isEqualTo(LocalDateTime.of(2024, 3, 4, 12, 0, 0));
        assertThat(meetingCreateResponse.place()).isEqualTo(MEETING_PLACE.value());
        assertThat(meetingCreateResponse.meetingCategory()).isEqualTo(MeetingCategory.SINGLE);
    }

    @DisplayName("정기 미팅을 생성한다.")
    @Test
    void createRegularMeeting() {
        // given 1
        long memberId = 1;
        LocalDate startDate = LocalDate.of(2024, 3, 4);
        LocalDate endDate = LocalDate.of(2024, 6, 30);

        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .memberId(memberId)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        moimRepository.save(moimJpaEntity);

        // given 2
        MeetingCreateRequest meetingCreateRequest = MeetingCreateRequest.builder()
                .moimId(moimJpaEntity.getId())
                .agenda(MEETING_AGENDA.value())
                .startDateTime(LocalDateTime.of(2024, 3, 4, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 4, 12, 0, 0))
                .place(MEETING_PLACE.value())
                .meetingCategory(MeetingCategory.REGULAR)
                .build();

        // when
        MeetingCreateResponse meetingCreateResponse = meetingManager.createMeeting(meetingCreateRequest);

        // then
        long between = ChronoUnit.WEEKS.between(startDate, endDate) + 1;
        assertThat(meetingRepository.count()).isEqualTo(between);
        assertThat(meetingCreateResponse.agenda()).isEqualTo(MEETING_AGENDA.value());
        assertThat(meetingCreateResponse.startDateTime()).isEqualTo(LocalDateTime.of(2024, 3, 4, 10, 0, 0));
        assertThat(meetingCreateResponse.endDateTime()).isEqualTo(LocalDateTime.of(2024, 3, 4, 12, 0, 0));
        assertThat(meetingCreateResponse.place()).isEqualTo(MEETING_PLACE.value());
        assertThat(meetingCreateResponse.meetingCategory()).isEqualTo(MeetingCategory.REGULAR);
    }

    @DisplayName("일회 미팅 생성시 참여 정보, 스케줄 정보를 등록한다.")
    @Test
    void createSingleMeetingWithJoinedAndSchedule() {
        // given 1
        long memberId = 1;
        LocalDate startDate = LocalDate.of(2024, 3, 4);
        LocalDate endDate = LocalDate.of(2024, 6, 30);

        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .memberId(memberId)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        moimRepository.save(moimJpaEntity);

        // given 2
        for (int i = 1; i < 11; i++) {
            JoinedMoimJpaEntity joinedMoimJpaEntity = JoinedMoimJpaEntity.builder()
                    .moimId(moimJpaEntity.getId())
                    .memberId(i)
                    .build();

            joinedMoimRepository.save(joinedMoimJpaEntity);
        }

        // given 3
        MeetingCreateRequest meetingCreateRequest = MeetingCreateRequest.builder()
                .moimId(moimJpaEntity.getId())
                .agenda(MEETING_AGENDA.value())
                .startDateTime(LocalDateTime.of(2024, 3, 4, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 4, 12, 0, 0))
                .place(MEETING_PLACE.value())
                .meetingCategory(MeetingCategory.SINGLE)
                .build();

        // when
        meetingManager.createMeeting(meetingCreateRequest);

        // then
        assertThat(meetingRepository.count()).isEqualTo(1);
        assertThat(joinedMeetingRepository.count()).isEqualTo(10);
        assertThat(scheduleRepository.count()).isEqualTo(10);
    }

    @DisplayName("정기 미팅 생성시 참여 정보, 스케줄 정보를 등록한다.")
    @Test
    void createRegularMeetingWithJoinedAndSchedule() {
        // given 1
        long memberId = 1;
        LocalDate startDate = LocalDate.of(2024, 3, 4);
        LocalDate endDate = LocalDate.of(2024, 6, 30);

        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .memberId(memberId)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        moimRepository.save(moimJpaEntity);

        // given 2
        for (int i = 1; i < 11; i++) {
            JoinedMoimJpaEntity joinedMoimJpaEntity = JoinedMoimJpaEntity.builder()
                    .moimId(moimJpaEntity.getId())
                    .memberId(i)
                    .build();

            joinedMoimRepository.save(joinedMoimJpaEntity);
        }

        // given 3
        MeetingCreateRequest meetingCreateRequest = MeetingCreateRequest.builder()
                .moimId(moimJpaEntity.getId())
                .agenda(MEETING_AGENDA.value())
                .startDateTime(LocalDateTime.of(2024, 3, 4, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 4, 12, 0, 0))
                .place(MEETING_PLACE.value())
                .meetingCategory(MeetingCategory.REGULAR)
                .build();

        // when
        meetingManager.createMeeting(meetingCreateRequest);

        // then
        long between = ChronoUnit.WEEKS.between(startDate, endDate) + 1;
        assertThat(meetingRepository.count()).isEqualTo(between);
        assertThat(joinedMeetingRepository.count()).isEqualTo(10 * between);
        assertThat(scheduleRepository.count()).isEqualTo(10 * between);
    }

    @DisplayName("원래 있던 일정과 겹치면 스케줄을 등록하지 않는다.")
    @Test
    void createMeetingScheduleIfNotExist() {
        // given 1
        long memberId = 1;
        LocalDate startDate = LocalDate.of(2024, 3, 4);
        LocalDate endDate = LocalDate.of(2024, 6, 30);

        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .memberId(memberId)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        moimRepository.save(moimJpaEntity);

        // given 2
        JoinedMoimJpaEntity joinedMoimJpaEntity = JoinedMoimJpaEntity.builder()
                    .moimId(moimJpaEntity.getId())
                    .memberId(memberId)
                    .build();

        joinedMoimRepository.save(joinedMoimJpaEntity);


        ScheduleJpaEntity scheduleJpaEntity = ScheduleJpaEntity.builder()
                .memberId(memberId)
                .startDateTime(LocalDateTime.of(2024, 3, 4, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 4, 12, 0, 0))
                .build();

        scheduleRepository.save(scheduleJpaEntity);

        // given 3
        MeetingCreateRequest meetingCreateRequest = MeetingCreateRequest.builder()
                .moimId(moimJpaEntity.getId())
                .agenda(MEETING_AGENDA.value())
                .startDateTime(LocalDateTime.of(2024, 3, 4, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 4, 12, 0, 0))
                .place(MEETING_PLACE.value())
                .meetingCategory(MeetingCategory.SINGLE)
                .build();

        // when
        meetingManager.createMeeting(meetingCreateRequest);

        // then
        assertThat(meetingRepository.count()).isEqualTo(1);
        assertThat(joinedMeetingRepository.count()).isEqualTo(1);
        assertThat(scheduleRepository.count()).isEqualTo(1);
    }
}