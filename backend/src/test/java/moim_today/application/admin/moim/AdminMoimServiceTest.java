package moim_today.application.admin.moim;

import moim_today.dto.moim.moim.MoimSimpleResponse;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import moim_today.persistence.repository.moim.moim.MoimRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static moim_today.util.TestConstant.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class AdminMoimServiceTest {

    @Autowired
    private AdminMoimService adminMoimService;

    @Autowired
    private MoimRepository moimRepository;

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
}