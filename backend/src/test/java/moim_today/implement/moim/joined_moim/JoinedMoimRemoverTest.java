package moim_today.implement.moim.joined_moim;

import moim_today.persistence.entity.moim.joined_moim.JoinedMoimJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static moim_today.util.TestConstant.MOIM_ID;
import static org.assertj.core.api.Assertions.assertThat;

class JoinedMoimRemoverTest extends ImplementTest {

    @Autowired
    private JoinedMoimRemover joinedMoimRemover;
    
    @DisplayName("모임id에 해당하는 참여한 모임을 모두 삭제한다.")
    @Test
    void deleteJoinedMoimsByMoimIdTest(){
        //given
        long moimId1 = Long.parseLong(MOIM_ID.value());
        long moimId2 = Long.parseLong(MOIM_ID.value()) + 1L;

        JoinedMoimJpaEntity joinedMoimJpaEntity1 = JoinedMoimJpaEntity.builder()
                .moimId(moimId1)
                .build();

        JoinedMoimJpaEntity joinedMoimJpaEntity2 = JoinedMoimJpaEntity.builder()
                .moimId(moimId1)
                .build();

        JoinedMoimJpaEntity joinedMoimJpaEntity3 = JoinedMoimJpaEntity.builder()
                .moimId(moimId2)
                .build();

        joinedMoimRepository.save(joinedMoimJpaEntity1);
        joinedMoimRepository.save(joinedMoimJpaEntity2);
        joinedMoimRepository.save(joinedMoimJpaEntity3);

        //when
        joinedMoimRemover.deleteAllByMoimId(moimId1);

        //then
        assertThat(joinedMoimRepository.count()).isEqualTo(1L);
    }
}
