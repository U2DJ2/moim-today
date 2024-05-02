package moim_today.implement.moim.joined_moim;

import moim_today.persistence.entity.moim.joined_moim.JoinedMoimJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class JoinedMoimFinderTest extends ImplementTest {

    @Autowired
    private JoinedMoimFinder joinedMoimFinder;

    @DisplayName("특정 모임에 참여한 회원의 id 리스트를 가져온다.")
    @Test
    void findAllJoinedMemberId() {
        // given
        long moimId = 1L;

        for (long i = 0; i < 3; i++) {
            JoinedMoimJpaEntity joinedMoimJpaEntity = JoinedMoimJpaEntity.builder()
                    .memberId(i)
                    .moimId(moimId)
                    .build();

            joinedMoimRepository.save(joinedMoimJpaEntity);
        }

        // when
        List<Long> memberIds = joinedMoimFinder.findAllJoinedMemberId(moimId);

        // then
        assertThat(memberIds.size()).isEqualTo(3);
    }
}