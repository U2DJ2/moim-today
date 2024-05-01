package moim_today.implement.moim;

import moim_today.domain.moim.DisplayStatus;
import moim_today.domain.moim.enums.MoimCategory;
import moim_today.dto.moim.MoimUpdateRequest;
import moim_today.global.error.ForbiddenException;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static moim_today.global.constant.exception.MoimExceptionConstant.MOIM_FORBIDDEN;
import static moim_today.util.TestConstant.*;
import static org.assertj.core.api.Assertions.*;

class MoimUpdaterTest extends ImplementTest {

    @Autowired
    private MoimUpdater moimUpdater;

    @DisplayName("자신이 만든 모임을 수정하려고 시도하면 수정에 성공한다.")
    @Test
    void updateMoimTest(){

        //given
        long memberId = Long.parseLong(MEMBER_ID.value());

        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .memberId(memberId)
                .build();

        moimRepository.save(moimJpaEntity);
        long moimId = moimJpaEntity.getId();

        int capacity = Integer.parseInt(CAPACITY.value());
        LocalDate startDate = LocalDate.of(2024, 3, 1);
        LocalDate endDate = LocalDate.of(2024, 6, 30);

        MoimUpdateRequest moimUpdateRequest = MoimUpdateRequest.builder()
                .moimId(moimId)
                .title(TITLE.value())
                .contents(CONTENTS.value())
                .capacity(capacity)
                .imageUrl(MOIM_IMAGE_URL.value())
                .password(PASSWORD.value())
                .moimCategory(MoimCategory.STUDY)
                .displayStatus(DisplayStatus.PRIVATE)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        //when & //then
        assertThatCode(() -> moimUpdater.updateMoim(memberId, moimUpdateRequest))
                .doesNotThrowAnyException();

        MoimJpaEntity updatedMoim = moimRepository.getById(moimId);
        assertThat(updatedMoim.getTitle()).isEqualTo(TITLE.value());
        assertThat(updatedMoim.getContents()).isEqualTo(CONTENTS.value());
        assertThat(updatedMoim.getCapacity()).isEqualTo(capacity);
        assertThat(updatedMoim.getImageUrl()).isEqualTo(MOIM_IMAGE_URL.value());
        assertThat(updatedMoim.getPassword()).isEqualTo(PASSWORD.value());
        assertThat(updatedMoim.getMoimCategory()).isEqualTo(MoimCategory.STUDY);
        assertThat(updatedMoim.getDisplayStatus()).isEqualTo(DisplayStatus.PRIVATE);
        assertThat(updatedMoim.getStartDate()).isEqualTo(startDate);
        assertThat(updatedMoim.getEndDate()).isEqualTo(endDate);
    }

    @DisplayName("수정 권한이 없는 모임을 수정하려고 시도하면 예외가 발생한다.")
    @Test
    void updateMoimFailureTest(){

        //given
        long memberId = Long.parseLong(MEMBER_ID.value());
        long forbiddenMemberId = memberId + 1;

        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .memberId(forbiddenMemberId)
                .build();

        moimRepository.save(moimJpaEntity);
        long moimId = moimJpaEntity.getId();

        MoimUpdateRequest forbiddenMoimUpdateRequest = MoimUpdateRequest.builder()
                .moimId(moimId)
                .build();

        //expected
        assertThatThrownBy(() -> moimUpdater.updateMoim(memberId, forbiddenMoimUpdateRequest))
                .isInstanceOf(ForbiddenException.class)
                .hasMessage(MOIM_FORBIDDEN.message());
    }
}
