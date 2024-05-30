package moim_today.application.admin.moim;

import moim_today.dto.moim.moim.MoimSimpleResponse;
import moim_today.global.error.ForbiddenException;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import moim_today.persistence.repository.moim.moim.MoimRepository;
import moim_today.util.DatabaseCleaner;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static moim_today.global.constant.exception.MoimExceptionConstant.MOIM_FORBIDDEN_ERROR;
import static moim_today.util.TestConstant.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class AdminMoimServiceTest {

    @Autowired
    private AdminMoimService adminMoimService;

    @Autowired
    private MoimRepository moimRepository;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @BeforeEach
    void setUpDatabase() {
        databaseCleaner.cleanUp();
    }

    @DisplayName("학교에 있는 모든 모임을 조회한다.")
    @Test
    void findAllByUniversityId() {
        // given
        MoimJpaEntity moimJpaEntity1 = MoimJpaEntity.builder()
                .universityId(UNIV_ID.longValue())
                .build();

        MoimJpaEntity moimJpaEntity2 = MoimJpaEntity.builder()
                .universityId(UNIV_ID.longValue())
                .build();

        MoimJpaEntity moimJpaEntity3 = MoimJpaEntity.builder()
                .universityId(UNIV_ID.longValue() + 1)
                .build();

        moimRepository.save(moimJpaEntity1);
        moimRepository.save(moimJpaEntity2);
        moimRepository.save(moimJpaEntity3);

        // when
        List<MoimSimpleResponse> moimSimpleResponses = adminMoimService.findAllByUniversityId(UNIV_ID.longValue());

        // then
        assertThat(moimSimpleResponses.size()).isEqualTo(2);
    }

    @DisplayName("대학교내 모임이 아니면 모임 삭제에 실패한다.")
    @Test
    void deleteMoimForBidden() {
        // given
        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .universityId(UNIV_ID.longValue())
                .build();

        moimRepository.save(moimJpaEntity);

        // when
        Assertions.assertThatThrownBy(() -> adminMoimService.deleteMoim(9999L, moimJpaEntity.getId()))
                .isInstanceOf(ForbiddenException.class)
                .hasMessage(MOIM_FORBIDDEN_ERROR.message());
    }

    @DisplayName("대학교내 모임을 삭제한다.")
    @Test
    void deleteMoim() {
        // given
        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .universityId(UNIV_ID.longValue())
                .build();

        moimRepository.save(moimJpaEntity);

        // when
        adminMoimService.deleteMoim(UNIV_ID.longValue(), moimJpaEntity.getId());

        // then
        assertThat(moimRepository.count()).isEqualTo(0);
    }
}