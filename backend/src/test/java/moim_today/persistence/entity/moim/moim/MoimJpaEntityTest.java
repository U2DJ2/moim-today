package moim_today.persistence.entity.moim.moim;

import moim_today.domain.moim.DisplayStatus;
import moim_today.domain.moim.enums.MoimCategory;
import moim_today.dto.moim.moim.MoimUpdateRequest;
import moim_today.global.error.BadRequestException;
import moim_today.global.error.ForbiddenException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static moim_today.global.constant.MoimConstant.DEFAULT_MOIM_IMAGE_URL;
import static moim_today.global.constant.MoimConstant.DEFAULT_MOIM_PASSWORD;
import static moim_today.global.constant.exception.MeetingExceptionConstant.MEETING_DATE_TIME_BAD_REQUEST_ERROR;
import static moim_today.global.constant.exception.MoimExceptionConstant.*;
import static moim_today.util.TestConstant.*;
import static org.assertj.core.api.Assertions.*;

class MoimJpaEntityTest {

    @DisplayName("회원 id가 호스트 id와 일치하면 검증에 성공한다.")
    @Test
    void validateMemberTest() {
        //given
        long memberId = Long.parseLong(MEMBER_ID.value());

        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .memberId(memberId)
                .build();

        //expected
        assertThatCode(() -> moimJpaEntity.validateHostMember(memberId))
                .doesNotThrowAnyException();
    }

    @DisplayName("회원 id가 호스트 id와 일치하지 않으면 검증에 에러가 발생한다")
    @Test
    void validateMemberThrowsExceptionTest() {
        //given
        long memberId = Long.parseLong(MEMBER_ID.value());
        long forbiddenMemberId = Long.parseLong(MEMBER_ID.value()) + 1L;

        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .memberId(memberId)
                .build();

        //expected
        assertThatCode(() -> moimJpaEntity.validateHostMember(forbiddenMemberId))
                .isInstanceOf(ForbiddenException.class)
                .hasMessage(ORGANIZER_FORBIDDEN_ERROR.message());
    }

    @DisplayName("회원 id가 호스트 id와 일치하지 않으면 검증에 성공한다")
    @Test
    void validateNotHostMember() {
        //given
        long memberId = MEMBER_ID.longValue();
        long availableMemberId = MEMBER_ID.longValue() + 1L;

        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .memberId(memberId)
                .build();

        //expected
        assertThatCode(() -> moimJpaEntity.validateNotHostMember(availableMemberId))
                .doesNotThrowAnyException();
    }

    @DisplayName("회원 id가 호스트 id와 일치하면 검증에 에러가 발생한다")
    @Test
    void validateNotHostMemberFail() {
        //given
        long memberId = MEMBER_ID.longValue();
        long hostId = MEMBER_ID.longValue();

        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .memberId(memberId)
                .build();

        //expected
        assertThatCode(() -> moimJpaEntity.validateNotHostMember(hostId))
                .isInstanceOf(ForbiddenException.class)
                .hasMessage(MOIM_HOST_ERROR.message());
    }

    @DisplayName("모임의 기간에 속하는 미팅 시간대이면 검증에 성공한다 - 시작 시간")
    @Test
    void validateStartDateTime() {
        // given 1
        LocalDate startDate = LocalDate.of(2024, 3, 4);
        LocalDate endDate = LocalDate.of(2024, 6, 30);

        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .startDate(startDate)
                .endDate(endDate)
                .build();

        // given 2
        LocalDateTime meetingStartDateTime = LocalDateTime.of(2024, 3, 4, 0, 0, 0, 0);
        LocalDateTime meetingEndDateTime = LocalDateTime.of(2024, 3, 4, 2, 0, 0, 0);

        // expected
        assertThatCode(() -> moimJpaEntity.validateDateTime(meetingStartDateTime, meetingEndDateTime))
                .doesNotThrowAnyException();
    }

    @DisplayName("모임의 기간에 속하는 미팅 시간대이면 검증에 성공한다 - 종료 시간")
    @Test
    void validateEndDateTime() {
        // given 1
        LocalDate startDate = LocalDate.of(2024, 3, 4);
        LocalDate endDate = LocalDate.of(2024, 6, 30);

        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .startDate(startDate)
                .endDate(endDate)
                .build();

        // given 2
        LocalDateTime meetingStartDateTime = LocalDateTime.of(2024, 6, 30, 22, 0, 0, 0);
        LocalDateTime meetingEndDateTime = LocalDateTime.of(2024, 6, 30, 23, 59, 59, 59);

        // expected
        assertThatCode(() -> moimJpaEntity.validateDateTime(meetingStartDateTime, meetingEndDateTime))
                .doesNotThrowAnyException();
    }

    @DisplayName("모임의 기간에 속하는 미팅 시간대가 아니면 예외가 발생한다 - 시작 시간")
    @Test
    void validateStartDateTimeFail() {
        // given 1
        LocalDate startDate = LocalDate.of(2024, 3, 4);
        LocalDate endDate = LocalDate.of(2024, 6, 30);

        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .startDate(startDate)
                .endDate(endDate)
                .build();

        // given 2
        LocalDateTime meetingStartDateTime = LocalDateTime.of(2024, 3, 3, 23, 59, 59, 59);
        LocalDateTime meetingEndDateTime = LocalDateTime.of(2024, 3, 4, 0, 0, 0, 0);

        // expected
        assertThatThrownBy(() -> moimJpaEntity.validateDateTime(meetingStartDateTime, meetingEndDateTime))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(MEETING_DATE_TIME_BAD_REQUEST_ERROR.message());
    }

    @DisplayName("모임의 기간에 속하는 미팅 시간대가 아니면 예외가 발생한다 - 시작 시간")
    @Test
    void validateEndDateTimeFail() {
        // given 1
        LocalDate startDate = LocalDate.of(2024, 3, 4);
        LocalDate endDate = LocalDate.of(2024, 6, 30);

        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .startDate(startDate)
                .endDate(endDate)
                .build();

        // given 2
        LocalDateTime meetingStartDateTime = LocalDateTime.of(2024, 6, 30, 22, 0, 0, 0);
        LocalDateTime meetingEndDateTime = LocalDateTime.of(2024, 7, 1, 0, 0, 0, 0);

        // expected
        assertThatThrownBy(() -> moimJpaEntity.validateDateTime(meetingStartDateTime, meetingEndDateTime))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(MEETING_DATE_TIME_BAD_REQUEST_ERROR.message());
    }

    @DisplayName("수정시 공개 여부가 PUBLIC이라면 default password가 설정된다.")
    @Test
    void updateMoimDisplayStatusPublicTest() {

        //given
        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .build();

        int capacity = Integer.parseInt(CAPACITY.value());
        LocalDate startDate = LocalDate.of(2024, 3, 1);
        LocalDate endDate = LocalDate.of(2024, 6, 30);

        MoimUpdateRequest moimUpdateRequest = MoimUpdateRequest.builder()
                .title(MOIM_TITLE.value())
                .contents(MOIM_CONTENTS.value())
                .capacity(capacity)
                .imageUrl(MOIM_IMAGE_URL.value())
                .moimCategory(MoimCategory.STUDY)
                .displayStatus(DisplayStatus.PUBLIC)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        //expected
        assertThatCode(() -> moimJpaEntity.updateMoim(moimUpdateRequest))
                .doesNotThrowAnyException();

        assertThat(moimJpaEntity.getTitle()).isEqualTo(MOIM_TITLE.value());
        assertThat(moimJpaEntity.getContents()).isEqualTo(MOIM_CONTENTS.value());
        assertThat(moimJpaEntity.getCapacity()).isEqualTo(capacity);
        assertThat(moimJpaEntity.getImageUrl()).isEqualTo(MOIM_IMAGE_URL.value());
        assertThat(moimJpaEntity.getPassword()).isEqualTo(DEFAULT_MOIM_PASSWORD.value());
        assertThat(moimJpaEntity.getMoimCategory()).isEqualTo(MoimCategory.STUDY);
        assertThat(moimJpaEntity.getDisplayStatus()).isEqualTo(DisplayStatus.PUBLIC);
        assertThat(moimJpaEntity.getStartDate()).isEqualTo(startDate);
        assertThat(moimJpaEntity.getEndDate()).isEqualTo(endDate);
    }

    @DisplayName("수정시 공개 여부가 PRIVATE이라면 입력한 password가 설정된다.")
    @Test
    void updateMoimDisplayStatusPrivateTest() {

        //given
        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .build();

        int capacity = Integer.parseInt(CAPACITY.value());
        LocalDate startDate = LocalDate.of(2024, 3, 1);
        LocalDate endDate = LocalDate.of(2024, 6, 30);

        MoimUpdateRequest moimUpdateRequest = MoimUpdateRequest.builder()
                .title(MOIM_TITLE.value())
                .contents(MOIM_CONTENTS.value())
                .capacity(capacity)
                .password(PASSWORD.value())
                .imageUrl(MOIM_IMAGE_URL.value())
                .moimCategory(MoimCategory.STUDY)
                .displayStatus(DisplayStatus.PRIVATE)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        //expected
        assertThatCode(() -> moimJpaEntity.updateMoim(moimUpdateRequest))
                .doesNotThrowAnyException();

        assertThat(moimJpaEntity.getTitle()).isEqualTo(MOIM_TITLE.value());
        assertThat(moimJpaEntity.getContents()).isEqualTo(MOIM_CONTENTS.value());
        assertThat(moimJpaEntity.getCapacity()).isEqualTo(capacity);
        assertThat(moimJpaEntity.getImageUrl()).isEqualTo(MOIM_IMAGE_URL.value());
        assertThat(moimJpaEntity.getPassword()).isEqualTo(PASSWORD.value());
        assertThat(moimJpaEntity.getMoimCategory()).isEqualTo(MoimCategory.STUDY);
        assertThat(moimJpaEntity.getDisplayStatus()).isEqualTo(DisplayStatus.PRIVATE);
        assertThat(moimJpaEntity.getStartDate()).isEqualTo(startDate);
        assertThat(moimJpaEntity.getEndDate()).isEqualTo(endDate);
    }

    @DisplayName("수정시 imageUrl값이 없다면 default imageURL 값이 들어간다.")
    @Test
    void updateMoimImageUrlIsNullTest() {

        //given
        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .build();

        int capacity = Integer.parseInt(CAPACITY.value());
        LocalDate startDate = LocalDate.of(2024, 3, 1);
        LocalDate endDate = LocalDate.of(2024, 6, 30);

        MoimUpdateRequest moimUpdateRequest = MoimUpdateRequest.builder()
                .title(MOIM_TITLE.value())
                .contents(MOIM_CONTENTS.value())
                .capacity(capacity)
                .password(PASSWORD.value())
                .moimCategory(MoimCategory.STUDY)
                .displayStatus(DisplayStatus.PRIVATE)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        //expected
        assertThatCode(() -> moimJpaEntity.updateMoim(moimUpdateRequest))
                .doesNotThrowAnyException();

        assertThat(moimJpaEntity.getTitle()).isEqualTo(MOIM_TITLE.value());
        assertThat(moimJpaEntity.getContents()).isEqualTo(MOIM_CONTENTS.value());
        assertThat(moimJpaEntity.getCapacity()).isEqualTo(capacity);
        assertThat(moimJpaEntity.getImageUrl()).isEqualTo(DEFAULT_MOIM_IMAGE_URL.value());
        assertThat(moimJpaEntity.getPassword()).isEqualTo(PASSWORD.value());
        assertThat(moimJpaEntity.getMoimCategory()).isEqualTo(MoimCategory.STUDY);
        assertThat(moimJpaEntity.getDisplayStatus()).isEqualTo(DisplayStatus.PRIVATE);
        assertThat(moimJpaEntity.getStartDate()).isEqualTo(startDate);
        assertThat(moimJpaEntity.getEndDate()).isEqualTo(endDate);
    }

    @DisplayName("수정시 imageUrl값이 있다면 값이 그대로 들어간다.")
    @Test
    void updateMoimImageUrlIsNotNullTest() {

        //given
        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .build();

        int capacity = Integer.parseInt(CAPACITY.value());
        LocalDate startDate = LocalDate.of(2024, 3, 1);
        LocalDate endDate = LocalDate.of(2024, 6, 30);

        MoimUpdateRequest moimUpdateRequest = MoimUpdateRequest.builder()
                .title(MOIM_TITLE.value())
                .contents(MOIM_CONTENTS.value())
                .capacity(capacity)
                .password(PASSWORD.value())
                .imageUrl(MOIM_IMAGE_URL.value())
                .moimCategory(MoimCategory.STUDY)
                .displayStatus(DisplayStatus.PRIVATE)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        //expected
        assertThatCode(() -> moimJpaEntity.updateMoim(moimUpdateRequest))
                .doesNotThrowAnyException();

        assertThat(moimJpaEntity.getTitle()).isEqualTo(MOIM_TITLE.value());
        assertThat(moimJpaEntity.getContents()).isEqualTo(MOIM_CONTENTS.value());
        assertThat(moimJpaEntity.getCapacity()).isEqualTo(capacity);
        assertThat(moimJpaEntity.getImageUrl()).isEqualTo(MOIM_IMAGE_URL.value());
        assertThat(moimJpaEntity.getPassword()).isEqualTo(PASSWORD.value());
        assertThat(moimJpaEntity.getMoimCategory()).isEqualTo(MoimCategory.STUDY);
        assertThat(moimJpaEntity.getDisplayStatus()).isEqualTo(DisplayStatus.PRIVATE);
        assertThat(moimJpaEntity.getStartDate()).isEqualTo(startDate);
        assertThat(moimJpaEntity.getEndDate()).isEqualTo(endDate);
    }

    @DisplayName("조회수를 1 올린다.")
    @Test
    void updateViewsTest() {
        //given
        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .views(0)
                .build();

        //when
        moimJpaEntity.updateMoimViews();

        //then
        assertThat(moimJpaEntity.getViews()).isEqualTo(1);
    }

    @DisplayName("자리가 있는지 검사한다")
    @Test
    void checkVacancy() {
        // given
        MoimJpaEntity vacancyMoim = MoimJpaEntity.builder()
                .capacity(5)
                .currentCount(4)
                .build();

        MoimJpaEntity noVacancyMoim = MoimJpaEntity.builder()
                .capacity(5)
                .currentCount(5)
                .build();

        // expected
        assertThat(vacancyMoim.checkVacancy()).isTrue();
        assertThat(noVacancyMoim.checkVacancy()).isFalse();
    }

    @DisplayName("끝난 모임이면 에러를 발생시킨다")
    @Test
    void validateMoimNotEnd() {
        // given
        MoimJpaEntity notEndMoim = MoimJpaEntity.builder()
                .endDate(LocalDate.of(2024, 5, 26))
                .build();

        MoimJpaEntity endMoim = MoimJpaEntity.builder()
                .endDate(LocalDate.of(2024, 5, 25))
                .build();

        LocalDate curDate = LocalDate.of(2024, 5, 26);

        // expected
        assertThatCode(() -> notEndMoim.validateMoimNotEnd(curDate)).doesNotThrowAnyException();
        assertThatThrownBy(() -> endMoim.validateMoimNotEnd(curDate))
                .hasMessage(MOIM_AFTER_END_ERROR.message())
                .isInstanceOf(BadRequestException.class);
    }

    @DisplayName("비밀번호가 일치하는지 검사한다.")
    @Test
    void validatePassword() {
        // given
        String password = "1234";

        MoimJpaEntity privateMoim = MoimJpaEntity.builder()
                .password(password)
                .build();

        // expected
        assertThatCode(() -> privateMoim.validatePassword(password)).doesNotThrowAnyException();
    }

    @DisplayName("비밀번호가 일치하지 않으면 에러가 난다.")
    @Test
    void validatePasswordError() {
        // given
        String password = "1234";
        String wrongPassword = "123";

        MoimJpaEntity privateMoim = MoimJpaEntity.builder()
                .password(password)
                .build();

        // expected
        assertThatThrownBy(() -> privateMoim.validatePassword(wrongPassword))
                .hasMessage(MOIM_PASSWORD_NOT_MATCHED_ERROR.message())
                .isInstanceOf(BadRequestException.class);
    }

    @DisplayName("모임이 public 인지 검사한다.")
    @Test
    void validatePublic() {
        // given
        MoimJpaEntity publicMoim = MoimJpaEntity.builder()
                .displayStatus(DisplayStatus.PUBLIC)
                .build();
        MoimJpaEntity privateMoim = MoimJpaEntity.builder()
                .displayStatus(DisplayStatus.PRIVATE)
                .build();

        // expected
        assertThatCode(publicMoim::validatePublic).doesNotThrowAnyException();
        assertThatThrownBy(privateMoim::validatePublic)
                .hasMessage(MOIM_NOT_PUBLIC_ERROR.message())
                .isInstanceOf(ForbiddenException.class);
    }
}
