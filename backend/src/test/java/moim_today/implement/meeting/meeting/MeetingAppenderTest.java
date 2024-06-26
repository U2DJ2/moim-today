package moim_today.implement.meeting.meeting;

import moim_today.domain.meeting.enums.MeetingCategory;
import moim_today.dto.meeting.meeting.MeetingCreateRequest;
import moim_today.dto.meeting.meeting.MeetingCreateResponse;
import moim_today.global.error.BadRequestException;
import moim_today.persistence.entity.member.MemberJpaEntity;
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

import static moim_today.global.constant.exception.MeetingExceptionConstant.MEETING_DATE_TIME_BAD_REQUEST_ERROR;
import static moim_today.util.TestConstant.*;
import static org.assertj.core.api.Assertions.*;

class MeetingAppenderTest extends ImplementTest {

    @Autowired
    private MeetingAppender meetingAppender;

    @DisplayName("단일 미팅을 생성한다.")
    @Test
    void createSingleMeeting() {
        // given 1
        MemberJpaEntity memberJpaEntity = MemberJpaEntity.builder()
                .build();

        memberRepository.save(memberJpaEntity);
        long memberId = memberJpaEntity.getId();

        // given 2
        LocalDate startDate = LocalDate.of(2024, 3, 4);
        LocalDate endDate = LocalDate.of(2024, 6, 30);

        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .title(MOIM_TITLE.value())
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
                .meetingCategory(MeetingCategory.SINGLE)
                .build();

        // given 3
        LocalDate currentDate = LocalDate.of(2024, 3, 4);

        // when
        MeetingCreateResponse meetingCreateResponse = meetingAppender.saveMeeting(memberId, meetingCreateRequest, currentDate);

        // then
        assertThat(meetingRepository.count()).isEqualTo(1);
        assertThat(meetingCreateResponse.agenda()).isEqualTo(MEETING_AGENDA.value());
        assertThat(meetingCreateResponse.startDateTime()).isEqualTo(LocalDateTime.of(2024, 3, 4, 10, 0, 0));
        assertThat(meetingCreateResponse.endDateTime()).isEqualTo(LocalDateTime.of(2024, 3, 4, 12, 0, 0));
        assertThat(meetingCreateResponse.place()).isEqualTo(MEETING_PLACE.value());
        assertThat(meetingCreateResponse.meetingCategory()).isEqualTo(MeetingCategory.SINGLE);
    }

    @DisplayName("현재 날짜부터 정기 미팅을 생성한다.")
    @Test
    void createRegularMeetingFromCurrentDate() {
        // given 1
        long memberId = 1;
        LocalDate currentDate = LocalDate.of(2024, 3, 30);
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
        MeetingCreateResponse meetingCreateResponse = meetingAppender.saveMeeting(memberId, meetingCreateRequest, currentDate);

        // then
        long between = ChronoUnit.WEEKS.between(currentDate, endDate) + 1;
        assertThat(meetingRepository.count()).isEqualTo(between);
        assertThat(meetingCreateResponse.agenda()).isEqualTo(MEETING_AGENDA.value());
        assertThat(meetingCreateResponse.startDateTime()).isEqualTo(LocalDateTime.of(2024, 3, 4, 10, 0, 0));
        assertThat(meetingCreateResponse.endDateTime()).isEqualTo(LocalDateTime.of(2024, 3, 4, 12, 0, 0));
        assertThat(meetingCreateResponse.place()).isEqualTo(MEETING_PLACE.value());
        assertThat(meetingCreateResponse.meetingCategory()).isEqualTo(MeetingCategory.REGULAR);
    }

    @DisplayName("미팅 시작 날짜부터 정기 미팅을 생성한다.")
    @Test
    void createRegularMeetingFromStartDate() {
        // given 1
        MemberJpaEntity memberJpaEntity = MemberJpaEntity.builder()
                .build();

        memberRepository.save(memberJpaEntity);
        long memberId = memberJpaEntity.getId();
        LocalDate currentDate = LocalDate.of(2024, 2, 1);
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
        MeetingCreateResponse meetingCreateResponse = meetingAppender.saveMeeting(memberId, meetingCreateRequest, currentDate);

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
        MemberJpaEntity memberJpaEntity = MemberJpaEntity.builder()
                .build();

        memberRepository.save(memberJpaEntity);
        long memberId = memberJpaEntity.getId();
        LocalDate currentDate = LocalDate.of(2024, 3, 30);
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
        meetingAppender.saveMeeting(memberId, meetingCreateRequest, currentDate);

        // then
        assertThat(meetingRepository.count()).isEqualTo(1);
        assertThat(joinedMeetingRepository.count()).isEqualTo(10);
        assertThat(scheduleRepository.count()).isEqualTo(10);
    }

    @DisplayName("정기 미팅 생성시 참여 정보, 스케줄 정보를 등록한다.")
    @Test
    void createRegularMeetingWithJoinedAndSchedule() {
        // given 1
        MemberJpaEntity memberJpaEntity = MemberJpaEntity.builder()
                .build();

        memberRepository.save(memberJpaEntity);
        long memberId = memberJpaEntity.getId();
        LocalDate currentDate = LocalDate.of(2024, 3, 30);
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
        meetingAppender.saveMeeting(memberId, meetingCreateRequest, currentDate);

        // then
        long between = ChronoUnit.WEEKS.between(currentDate, endDate) + 1;
        assertThat(meetingRepository.count()).isEqualTo(between);
        assertThat(joinedMeetingRepository.count()).isEqualTo(10 * between);
        assertThat(scheduleRepository.count()).isEqualTo(10 * between);
    }

    @DisplayName("원래 있던 일정과 겹치면 스케줄을 등록하지 않는다.")
    @Test
    void saveMeetingScheduleIfNotExist() {
        // given 1
        MemberJpaEntity memberJpaEntity = MemberJpaEntity.builder()
                .build();

        memberRepository.save(memberJpaEntity);
        long memberId = memberJpaEntity.getId();
        LocalDate currentDate = LocalDate.of(2024, 3, 30);
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
        meetingAppender.saveMeeting(memberId, meetingCreateRequest, currentDate);

        // then
        assertThat(meetingRepository.count()).isEqualTo(1);
        assertThat(joinedMeetingRepository.count()).isEqualTo(1);
        assertThat(scheduleRepository.count()).isEqualTo(1);
    }

    @DisplayName("미팅 시간대가 모임에 포함되면 검증에 통과한다.")
    @Test
    void validateMoimDateTime() {
        // given 1
        MemberJpaEntity memberJpaEntity = MemberJpaEntity.builder()
                .build();

        memberRepository.save(memberJpaEntity);

        // given 2
        LocalDate currentDate = LocalDate.of(2024, 3, 30);
        LocalDate startDate = LocalDate.of(2024, 3, 4);
        LocalDate endDate = LocalDate.of(2024, 6, 30);

        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .memberId(memberJpaEntity.getId())
                .startDate(startDate)
                .endDate(endDate)
                .build();

        moimRepository.save(moimJpaEntity);

        // given 3
        MeetingCreateRequest meetingCreateRequest = MeetingCreateRequest.builder()
                .moimId(moimJpaEntity.getId())
                .agenda(MEETING_AGENDA.value())
                .startDateTime(LocalDateTime.of(2024, 3, 4, 0, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 4, 2, 0, 0))
                .place(MEETING_PLACE.value())
                .meetingCategory(MeetingCategory.SINGLE)
                .build();

        // then
        assertThatCode(() -> meetingAppender.saveMeeting(memberJpaEntity.getId(), meetingCreateRequest, currentDate))
                .doesNotThrowAnyException();
    }

    @DisplayName("미팅 시간대가 모임에 포함되지 않으면 예외가 발생한다.")
    @Test
    void validateMoimDateTimeFail() {
        // given 1
        MemberJpaEntity memberJpaEntity = MemberJpaEntity.builder()
                .build();

        memberRepository.save(memberJpaEntity);

        // given 2
        LocalDate currentDate = LocalDate.of(2024, 3, 30);
        LocalDate startDate = LocalDate.of(2024, 3, 4);
        LocalDate endDate = LocalDate.of(2024, 6, 30);

        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .memberId(memberJpaEntity.getId())
                .startDate(startDate)
                .endDate(endDate)
                .build();

        moimRepository.save(moimJpaEntity);

        // given 3
        MeetingCreateRequest meetingCreateRequest = MeetingCreateRequest.builder()
                .moimId(moimJpaEntity.getId())
                .agenda(MEETING_AGENDA.value())
                .startDateTime(LocalDateTime.of(2024, 3, 3, 23, 59, 59))
                .endDateTime(LocalDateTime.of(2024, 3, 4, 2, 0, 0))
                .place(MEETING_PLACE.value())
                .meetingCategory(MeetingCategory.SINGLE)
                .build();

        // then
        assertThatThrownBy(() -> meetingAppender.saveMeeting(memberJpaEntity.getId(), meetingCreateRequest, currentDate))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(MEETING_DATE_TIME_BAD_REQUEST_ERROR.message());
    }
}