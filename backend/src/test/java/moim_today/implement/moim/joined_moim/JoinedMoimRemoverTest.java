package moim_today.implement.moim.joined_moim;

import moim_today.persistence.entity.moim.joined_moim.JoinedMoimJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static moim_today.util.TestConstant.MEMBER_ID;
import static moim_today.util.TestConstant.MOIM_ID;
import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

class JoinedMoimRemoverTest extends ImplementTest {

    @Autowired
    private JoinedMoimRemover joinedMoimRemover;
    
    @DisplayName("모임id에 해당하는 참여한 모임을 모두 삭제한다.")
    @Test
    void deleteJoinedMoimsByMoimIdTest(){
        //given
        long moimId1 = MOIM_ID.longValue();
        long moimId2 = MOIM_ID.longValue() + 1L;

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

    @DisplayName("모임에 참여한 멤버 하나만 삭제한다")
    @Test
    void shouldOnlyRemoveSpecifiedMember() {
        // given
        long moimId1 = MOIM_ID.longValue();
        long moimId2 = MOIM_ID.longValue() + 1L;
        long forcedOutMember = MEMBER_ID.longValue();
        long stillJoiningMember = MEMBER_ID.longValue() + 1L;

        JoinedMoimJpaEntity joinedMoimJpaEntity1 = JoinedMoimJpaEntity.builder()
                .moimId(moimId1)
                .memberId(forcedOutMember)
                .build();

        JoinedMoimJpaEntity joinedMoimJpaEntity2 = JoinedMoimJpaEntity.builder()
                .moimId(moimId1)
                .memberId(stillJoiningMember)
                .build();

        JoinedMoimJpaEntity joinedMoimJpaEntity3 = JoinedMoimJpaEntity.builder()
                .moimId(moimId2)
                .memberId(forcedOutMember)
                .build();

        joinedMoimRepository.save(joinedMoimJpaEntity1);
        joinedMoimRepository.save(joinedMoimJpaEntity2);
        joinedMoimRepository.save(joinedMoimJpaEntity3);

        // when
        joinedMoimRemover.deleteMoimMember(moimId1, forcedOutMember);

        // then
        assertThat(joinedMoimRepository.isJoining(moimId1,forcedOutMember)).isFalse();
        assertThat(joinedMoimRepository.isJoining(moimId1, stillJoiningMember)).isTrue();
    }

    @DisplayName("어느 모임에서 멤버를 삭제할 때, 다른 모임에서는 삭제되지 않는다")
    @Test
    void shouldOnlyRemoveSpecifiedJoinedMoim(){
        // given
        long moimId1 = MOIM_ID.longValue();
        long moimId2 = MOIM_ID.longValue() + 1L;
        long forcedOutMember = MEMBER_ID.longValue();

        JoinedMoimJpaEntity joinedMoimJpaEntity1 = JoinedMoimJpaEntity.builder()
                .moimId(moimId1)
                .memberId(forcedOutMember)
                .build();

        JoinedMoimJpaEntity joinedMoimJpaEntity3 = JoinedMoimJpaEntity.builder()
                .moimId(moimId2)
                .memberId(forcedOutMember)
                .build();

        joinedMoimRepository.save(joinedMoimJpaEntity1);
        joinedMoimRepository.save(joinedMoimJpaEntity3);

        // when
        joinedMoimRemover.deleteMoimMember(moimId1, forcedOutMember);

        // then
        assertThat(joinedMoimRepository.isJoining(moimId2, forcedOutMember)).isTrue();
    }
}
