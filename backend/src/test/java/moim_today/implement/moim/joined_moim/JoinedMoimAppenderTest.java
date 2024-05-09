package moim_today.implement.moim.joined_moim;

import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static moim_today.util.TestConstant.MEMBER_ID;
import static org.assertj.core.api.Assertions.assertThat;

class JoinedMoimAppenderTest extends ImplementTest {

    @Autowired
    private JoinedMoimAppender joinedMoimAppender;

    @DisplayName("참여한 모임을 생성한다.")
    @Test
    void createJoinedMoimTest() {
        //given
        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .build();

        moimRepository.save(moimJpaEntity);
        long moimId = moimJpaEntity.getId();
        long memberId = MEMBER_ID.longValue();

        //when
        joinedMoimAppender.createJoinedMoim(memberId, moimId);

        //then
        assertThat(joinedMoimRepository.count()).isEqualTo(1L);
        assertThat(joinedMoimRepository.isJoining(moimId, memberId)).isTrue();
    }
}
