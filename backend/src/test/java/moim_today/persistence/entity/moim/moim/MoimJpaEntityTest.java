package moim_today.persistence.entity.moim.moim;

import moim_today.domain.moim.DisplayStatus;
import moim_today.domain.moim.enums.MoimCategory;
import moim_today.dto.moim.moim.MoimUpdateRequest;
import moim_today.global.error.ForbiddenException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static moim_today.global.constant.MoimConstant.DEFAULT_MOIM_IMAGE_URL;
import static moim_today.global.constant.MoimConstant.DEFAULT_MOIM_PASSWORD;
import static moim_today.global.constant.exception.MoimExceptionConstant.MOIM_HOST_ERROR;
import static moim_today.global.constant.exception.MoimExceptionConstant.ORGANIZER_FORBIDDEN_ERROR;
import static moim_today.util.TestConstant.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class MoimJpaEntityTest {

    @DisplayName("회원 id가 호스트 id와 일치하면 검증에 성공한다.")
    @Test
    void validateMemberTest(){
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
    void validateMemberThrowsExceptionTest(){
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

    @DisplayName("수정시 공개 여부가 PUBLIC이라면 default password가 설정된다.")
    @Test
    void updateMoimDisplayStatusPublicTest(){

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
    void updateMoimDisplayStatusPrivateTest(){

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
    void updateMoimImageUrlIsNullTest(){

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
    void updateMoimImageUrlIsNotNullTest(){

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
}
