package moim_today.dto.moim.moim;

import moim_today.domain.moim.DisplayStatus;
import moim_today.domain.moim.enums.MoimCategory;
import moim_today.global.error.BadRequestException;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static moim_today.global.constant.MoimConstant.DEFAULT_MOIM_IMAGE_URL;
import static moim_today.global.constant.MoimConstant.DEFAULT_MOIM_PASSWORD;
import static moim_today.global.constant.exception.MoimExceptionConstant.PRIVATE_MOIM_NEEDS_PASSWORD_ERROR;
import static moim_today.util.TestConstant.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MoimCreateRequestTest {

    @DisplayName("ImageUrl 값이 없다면 기본 URL을 가진 엔티티가 생성된다.")
    @Test
    void NoneImageUrlCreateEntityWithDefaultUrl() {

        //given
        int capacity = Integer.parseInt(CAPACITY.value());
        long memberId = Long.parseLong(MEMBER_ID.value());
        long universityId = Long.parseLong(UNIV_ID.value());
        LocalDate startDate = LocalDate.of(2024, 3, 1);
        LocalDate endDate = LocalDate.of(2024, 6, 30);

        MoimCreateRequest moimCreateRequest = MoimCreateRequest.builder()
                .title(MOIM_TITLE.value())
                .contents(MOIM_CONTENTS.value())
                .capacity(capacity)
                .password(PASSWORD.value())
                .moimCategory(MoimCategory.STUDY)
                .displayStatus(DisplayStatus.PRIVATE)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        //when
        MoimJpaEntity entity = moimCreateRequest.toEntity(memberId, universityId);

        //then
        assertThat((entity.getImageUrl())).isEqualTo(DEFAULT_MOIM_IMAGE_URL.value());
    }

    @DisplayName("ImageUrl 값이 있다면 엔티티에 정상적으로 들어간다.")
    @Test
    void CreateMoimJpaEntityWithRequestImageUrl() {

        //given
        int capacity = Integer.parseInt(CAPACITY.value());
        long memberId = Long.parseLong(MEMBER_ID.value());
        long universityId = Long.parseLong(UNIV_ID.value());
        LocalDate startDate = LocalDate.of(2024, 3, 1);
        LocalDate endDate = LocalDate.of(2024, 6, 30);

        MoimCreateRequest moimCreateRequest = MoimCreateRequest.builder()
                .title(MOIM_TITLE.value())
                .contents(MOIM_CONTENTS.value())
                .capacity(capacity)
                .imageUrl(MOIM_IMAGE_URL.value())
                .password(PASSWORD.value())
                .moimCategory(MoimCategory.STUDY)
                .displayStatus(DisplayStatus.PRIVATE)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        //when
        MoimJpaEntity entity = moimCreateRequest.toEntity(memberId, universityId);

        //then
        assertThat((entity.getImageUrl())).isEqualTo(MOIM_IMAGE_URL.value());
    }

    @DisplayName("공개 여부가 PUBLIC 이라면 기본 비밀번호를 가진 엔티티가 생성된다.")
    @Test
    void displayStatusPublicCreateEntityWithDefaultPassword() {

        //given
        int capacity = Integer.parseInt(CAPACITY.value());
        long memberId = Long.parseLong(MEMBER_ID.value());
        long universityId = Long.parseLong(UNIV_ID.value());
        LocalDate startDate = LocalDate.of(2024, 3, 1);
        LocalDate endDate = LocalDate.of(2024, 6, 30);

        MoimCreateRequest moimCreateRequest = MoimCreateRequest.builder()
                .title(MOIM_TITLE.value())
                .contents(MOIM_CONTENTS.value())
                .capacity(capacity)
                .password(PASSWORD.value())
                .moimCategory(MoimCategory.STUDY)
                .displayStatus(DisplayStatus.PUBLIC)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        //when
        MoimJpaEntity entity = moimCreateRequest.toEntity(memberId, universityId);

        //then
        assertThat((entity.getPassword())).isEqualTo(DEFAULT_MOIM_PASSWORD.value());
    }

    @DisplayName("공개 여부가 PRIVATE 이라면 해당 비밀번호로 생성된다.")
    @Test
    void displayStatusPrivateCreateEntityWithRequestPassword() {

        //given
        int capacity = Integer.parseInt(CAPACITY.value());
        long memberId = Long.parseLong(MEMBER_ID.value());
        long universityId = Long.parseLong(UNIV_ID.value());
        LocalDate startDate = LocalDate.of(2024, 3, 1);
        LocalDate endDate = LocalDate.of(2024, 6, 30);

        MoimCreateRequest moimCreateRequest = MoimCreateRequest.builder()
                .title(MOIM_TITLE.value())
                .contents(MOIM_CONTENTS.value())
                .capacity(capacity)
                .imageUrl(MOIM_IMAGE_URL.value())
                .password(PASSWORD.value())
                .moimCategory(MoimCategory.STUDY)
                .displayStatus(DisplayStatus.PRIVATE)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        //when
        MoimJpaEntity entity = moimCreateRequest.toEntity(memberId, universityId);

        //then
        assertThat((entity.getPassword())).isEqualTo(PASSWORD.value());
    }

    @DisplayName("공개 여부가 PRIVATE 인데 비밀번호가 null이면 예외가 발생한다.")
    @Test
    void displayStatusPrivateWithNonePasswordCreateEntityWithDefaultPassword() {

        //given
        int capacity = Integer.parseInt(CAPACITY.value());
        long memberId = Long.parseLong(MEMBER_ID.value());
        long universityId = Long.parseLong(UNIV_ID.value());
        LocalDate startDate = LocalDate.of(2024, 3, 1);
        LocalDate endDate = LocalDate.of(2024, 6, 30);

        MoimCreateRequest moimCreateRequest = MoimCreateRequest.builder()
                .title(MOIM_TITLE.value())
                .contents(MOIM_CONTENTS.value())
                .capacity(capacity)
                .imageUrl(MOIM_IMAGE_URL.value())
                .moimCategory(MoimCategory.STUDY)
                .displayStatus(DisplayStatus.PRIVATE)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        //expected
        assertThatThrownBy(() -> moimCreateRequest.toEntity(memberId, universityId))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(PRIVATE_MOIM_NEEDS_PASSWORD_ERROR.message());
    }
}
