package moim_today.implement.moim.moim;

import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class MoimRemoverTest extends ImplementTest {

    @Autowired
    private MoimRemover moimRemover;

    @DisplayName("모임을 삭제한다.")
    @Test
    void deleteMoimTest(){
        //given
        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .build();

        moimRepository.save(moimJpaEntity);
        long moimId = moimJpaEntity.getId();

        //when
        moimRemover.deleteById(moimId);

        //then
        assertThat(moimRepository.count()).isEqualTo(0);
    }
}
