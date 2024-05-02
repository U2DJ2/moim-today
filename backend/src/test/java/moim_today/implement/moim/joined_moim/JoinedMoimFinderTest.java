package moim_today.implement.moim.joined_moim;

import moim_today.persistence.entity.moim.joined_moim.JoinedMoimJpaEntity;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import moim_today.util.ImplementTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static moim_today.global.constant.exception.JoinedMoimExceptionConstant.JOINED_MOIM_MEMBER_NOT_FOUND;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class JoinedMoimFinderTest extends ImplementTest {

    @Autowired
    private JoinedMoimFinder joinedMoimFinder;

    private final int MOIM_MEMBER_SIZE = 3;

    @DisplayName("모임에 참여한 멤버들을 조회한다")
    @Test
    void findMembersByMoimId() {

        // given1
        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .memberId(1L)
                .build();

        MoimJpaEntity savedMoim = moimRepository.save(moimJpaEntity);

        // given2
        for(int i  = 0 ; i < MOIM_MEMBER_SIZE; i++){
            JoinedMoimJpaEntity joinedMoimJpaEntity = JoinedMoimJpaEntity.builder()
                    .memberId(i)
                    .moimId(savedMoim.getId())
                    .build();
            joinedMoimRepository.save(joinedMoimJpaEntity);
        }

        // when
        List<JoinedMoimJpaEntity> membersByMoimId = joinedMoimFinder.findByMoimId(savedMoim.getId());

        // then
        Assertions.assertThat(membersByMoimId.size()).isEqualTo(MOIM_MEMBER_SIZE);
    }

    @DisplayName("모임의 멤버를 조회할 때 아무도 없으면 에러를 발생시킨다")
    @Test
    void findMembersByMoimIdNotFound() {

        // given1
        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .memberId(1L)
                .build();

        MoimJpaEntity savedMoim = moimRepository.save(moimJpaEntity);

        // expected
        assertThatThrownBy(() -> joinedMoimFinder.findByMoimId(savedMoim.getId()))
                .hasMessage(JOINED_MOIM_MEMBER_NOT_FOUND.message());
    }
}