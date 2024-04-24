package moim_today.implement.moim;

import moim_today.domain.moim.DisplayStatus;
import moim_today.domain.moim.enums.MoimCategory;
import moim_today.dto.moim.PrivateMoimAppendRequest;
import moim_today.dto.moim.PublicMoimAppendRequest;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static moim_today.global.constant.MoimConstant.DEFAULT_MOIM_PASSWORD;
import static moim_today.global.constant.NumberConstant.DEFAULT_MOIM_CURRENT_COUNT;
import static moim_today.global.constant.NumberConstant.DEFAULT_MOIM_VIEWS;
import static moim_today.util.TestConstant.*;
import static org.assertj.core.api.Assertions.assertThat;

class MoimAppenderTest extends ImplementTest {

    @Autowired
    private MoimAppender moimAppender;

    @DisplayName("공개 모임 생성 요청을 보내면 정상적으로 모든 필드가 초기화되어 DB에 저장된다.")
    @Test
    void savePublicMoim(){

        //given
        int capacity = Integer.parseInt(CAPACITY.value());
        long memberId = Long.parseLong(MEMBER_ID.value());
        long universityId = Long.parseLong(UNIV_ID.value());
        LocalDate startDate = LocalDate.of(2024, 03, 01);
        LocalDate endDate = LocalDate.of(2024, 06, 30);

        PublicMoimAppendRequest publicMoimAppendRequest = new PublicMoimAppendRequest(
                TITLE.value(),
                CONTENTS.value(),
                capacity,
                MOIM_IMAGE_URL.value(),
                MoimCategory.STUDY,
                startDate,
                endDate
        );

        //when
        MoimJpaEntity moimJpaEntity = moimAppender.createPublicMoim(
                memberId,
                universityId,
                publicMoimAppendRequest
        );

        //then
        long count = moimRepository.count();
        assertThat(count).isEqualTo(1);
        assertThat(moimJpaEntity.getMemberId()).isEqualTo(memberId);
        assertThat(moimJpaEntity.getUniversityId()).isEqualTo(universityId);
        assertThat(moimJpaEntity.getTitle()).isEqualTo(TITLE.value());
        assertThat(moimJpaEntity.getContents()).isEqualTo(CONTENTS.value());
        assertThat(moimJpaEntity.getCapacity()).isEqualTo(capacity);
        assertThat(moimJpaEntity.getCurrentCount()).isEqualTo(DEFAULT_MOIM_CURRENT_COUNT.value());
        assertThat(moimJpaEntity.getImageUrl()).isEqualTo(MOIM_IMAGE_URL.value());
        assertThat(moimJpaEntity.getPassword()).isEqualTo(DEFAULT_MOIM_PASSWORD.value());
        assertThat(moimJpaEntity.getMoimCategory()).isEqualTo(MoimCategory.STUDY);
        assertThat(moimJpaEntity.getDisplayStatus()).isEqualTo(DisplayStatus.PUBLIC);
        assertThat(moimJpaEntity.getViews()).isEqualTo(DEFAULT_MOIM_VIEWS.value());
        assertThat(moimJpaEntity.getStartDate()).isEqualTo(startDate);
        assertThat(moimJpaEntity.getEndDate()).isEqualTo(endDate);
    }

    @DisplayName("비공개 모임 생성 요청을 보내면 정상적으로 모든 필드가 초기화되어 DB에 저장된다.")
    @Test
    void savePrivateMoim(){

        //given
        int capacity = Integer.parseInt(CAPACITY.value());
        long memberId = Long.parseLong(MEMBER_ID.value());
        long universityId = Long.parseLong(UNIV_ID.value());
        LocalDate startDate = LocalDate.of(2024, 03, 01);
        LocalDate endDate = LocalDate.of(2024, 06, 30);

        PrivateMoimAppendRequest privateMoimAppendRequest = new PrivateMoimAppendRequest(
                TITLE.value(),
                CONTENTS.value(),
                capacity,
                PASSWORD.value(),
                MOIM_IMAGE_URL.value(),
                MoimCategory.STUDY,
                startDate,
                endDate
        );

        //when
        MoimJpaEntity moimJpaEntity = moimAppender.createPrivateMoim(
                memberId,
                universityId,
                privateMoimAppendRequest
        );

        //then
        long count = moimRepository.count();
        assertThat(count).isEqualTo(1);
        assertThat(moimJpaEntity.getMemberId()).isEqualTo(memberId);
        assertThat(moimJpaEntity.getUniversityId()).isEqualTo(universityId);
        assertThat(moimJpaEntity.getTitle()).isEqualTo(TITLE.value());
        assertThat(moimJpaEntity.getContents()).isEqualTo(CONTENTS.value());
        assertThat(moimJpaEntity.getCapacity()).isEqualTo(capacity);
        assertThat(moimJpaEntity.getCurrentCount()).isEqualTo(DEFAULT_MOIM_CURRENT_COUNT.value());
        assertThat(moimJpaEntity.getImageUrl()).isEqualTo(MOIM_IMAGE_URL.value());
        assertThat(moimJpaEntity.getPassword()).isEqualTo(PASSWORD.value());
        assertThat(moimJpaEntity.getMoimCategory()).isEqualTo(MoimCategory.STUDY);
        assertThat(moimJpaEntity.getDisplayStatus()).isEqualTo(DisplayStatus.PRIVATE);
        assertThat(moimJpaEntity.getViews()).isEqualTo(DEFAULT_MOIM_VIEWS.value());
        assertThat(moimJpaEntity.getStartDate()).isEqualTo(startDate);
        assertThat(moimJpaEntity.getEndDate()).isEqualTo(endDate);
    }
}
