package moim_today.dto.moim.moim;

import moim_today.domain.moim.enums.MoimCategory;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static moim_today.global.constant.MoimConstant.DEFAULT_MOIM_IMAGE_URL;
import static moim_today.util.TestConstant.*;
import static org.assertj.core.api.Assertions.assertThat;

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
                .moimCategory(MoimCategory.STUDY)
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
                .moimCategory(MoimCategory.STUDY)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        //when
        MoimJpaEntity entity = moimCreateRequest.toEntity(memberId, universityId);

        //then
        assertThat((entity.getImageUrl())).isEqualTo(MOIM_IMAGE_URL.value());
    }
}
