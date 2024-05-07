package moim_today.implement.moim.joined_moim;

import moim_today.persistence.entity.moim.joined_moim.JoinedMoimJpaEntity;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static moim_today.global.constant.exception.JoinedMoimExceptionConstant.JOINED_MOIM_MEMBER_IS_EMPTY;
import static moim_today.global.constant.exception.JoinedMoimExceptionConstant.JOINED_MOIM_MEMBER_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
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
        assertThat(membersByMoimId.size()).isEqualTo(MOIM_MEMBER_SIZE);
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
                .hasMessage(JOINED_MOIM_MEMBER_IS_EMPTY.message());
    }

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

    @DisplayName("모임에 참여한 멤버가 아닐 경우 에러를 발생한다")
    @Test
    void validateMemberInMoimTest() {
        // given
        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .memberId(1L)
                .build();

        MoimJpaEntity savedMoim = moimRepository.save(moimJpaEntity);

        // expected
        assertThatThrownBy(() -> joinedMoimFinder.validateMemberInMoim(savedMoim.getId(),2L))
                .hasMessageMatching(JOINED_MOIM_MEMBER_NOT_FOUND.message());
    }

    @DisplayName("모임에 참여한 멤버일 경우 에러를 발생하지 않는다")
    @Test
    void validateMemberInMoimNotThrownTest() {
        // given
        JoinedMoimJpaEntity joinedMoimJpaEntity = JoinedMoimJpaEntity.builder()
                .moimId(1L)
                .memberId(1L)
                .build();

        JoinedMoimJpaEntity savedJoined = joinedMoimRepository.save(joinedMoimJpaEntity);

        // expected
        assertThatCode(() ->
                joinedMoimFinder.validateMemberInMoim(savedJoined.getMoimId(), 1L))
                .doesNotThrowAnyException();
    }
}