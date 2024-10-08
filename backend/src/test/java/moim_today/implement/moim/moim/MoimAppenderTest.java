package moim_today.implement.moim.moim;

import moim_today.domain.moim.enums.MoimCategory;
import moim_today.dto.moim.moim.MoimCreateRequest;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static moim_today.global.constant.NumberConstant.DEFAULT_MOIM_CURRENT_COUNT;
import static moim_today.global.constant.NumberConstant.DEFAULT_MOIM_VIEWS;
import static moim_today.util.TestConstant.*;
import static org.assertj.core.api.Assertions.assertThat;

class MoimAppenderTest extends ImplementTest {

    @Autowired
    private MoimAppender moimAppender;

    @DisplayName("모임 생성 요청을 보내면 정상적으로 모든 필드가 초기화되어 DB에 저장된다.")
    @Test
    void savePrivateMoim(){

        //given
        int capacity = Integer.parseInt(CAPACITY.value());
        long memberId = Long.parseLong(MEMBER_ID.value());
        long universityId = Long.parseLong(UNIV_ID.value());
        LocalDate startDate = LocalDate.of(2024, 3, 1);
        LocalDate endDate = LocalDate.of(2024, 6, 30);

        MoimCreateRequest moimCreateRequest = new MoimCreateRequest(
                MOIM_TITLE.value(),
                MOIM_CONTENTS.value(),
                capacity,
                MOIM_IMAGE_URL.value(),
                MoimCategory.STUDY,
                startDate,
                endDate
        );

        //when
        MoimJpaEntity moimJpaEntity = moimAppender.createMoim(
                memberId,
                universityId,
                moimCreateRequest
        );

        //then
        long count = moimRepository.count();
        assertThat(count).isEqualTo(1);
        assertThat(moimJpaEntity.getMemberId()).isEqualTo(memberId);
        assertThat(moimJpaEntity.getUniversityId()).isEqualTo(universityId);
        assertThat(moimJpaEntity.getTitle()).isEqualTo(MOIM_TITLE.value());
        assertThat(moimJpaEntity.getContents()).isEqualTo(MOIM_CONTENTS.value());
        assertThat(moimJpaEntity.getCapacity()).isEqualTo(capacity);
        assertThat(moimJpaEntity.getCurrentCount()).isEqualTo(DEFAULT_MOIM_CURRENT_COUNT.value());
        assertThat(moimJpaEntity.getImageUrl()).isEqualTo(MOIM_IMAGE_URL.value());
        assertThat(moimJpaEntity.getMoimCategory()).isEqualTo(MoimCategory.STUDY);
        assertThat(moimJpaEntity.getViews()).isEqualTo(DEFAULT_MOIM_VIEWS.value());
        assertThat(moimJpaEntity.getStartDate()).isEqualTo(startDate);
        assertThat(moimJpaEntity.getEndDate()).isEqualTo(endDate);
    }
}
