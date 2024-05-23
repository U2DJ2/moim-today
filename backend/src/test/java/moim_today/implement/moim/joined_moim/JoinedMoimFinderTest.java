package moim_today.implement.moim.joined_moim;

import moim_today.dto.moim.moim.MoimSimpleResponse;
import moim_today.global.error.NotFoundException;
import moim_today.persistence.entity.moim.joined_moim.JoinedMoimJpaEntity;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static moim_today.global.constant.NumberConstant.DEFAULT_JOINED_MOIM_PAGE_SIZE;
import static moim_today.global.constant.exception.JoinedMoimExceptionConstant.JOINED_MOIM_MEMBER_IS_EMPTY;
import static moim_today.global.constant.exception.JoinedMoimExceptionConstant.JOINED_MOIM_MEMBER_NOT_FOUND;
import static moim_today.util.TestConstant.MEMBER_ID;
import static moim_today.util.TestConstant.MOIM_ID;
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

    @DisplayName("모임에 참여한 멤버들을 조회할 때 다른 모임의 멤버는 조회하지 않는다")
    @Test
    void findMembersByMoimIdExcludeAnotherMoimMembers() {

        // given1
        MoimJpaEntity moimJpaEntity1 = MoimJpaEntity.builder()
                .memberId(1L)
                .build();

        MoimJpaEntity moimJpaEntity2 = MoimJpaEntity.builder()
                .memberId(2L)
                .build();

        MoimJpaEntity savedMoim1 = moimRepository.save(moimJpaEntity1);
        MoimJpaEntity savedMoim2 = moimRepository.save(moimJpaEntity2);

        // given2
        for(int i  = 0 ; i < MOIM_MEMBER_SIZE; i++){
            JoinedMoimJpaEntity joinedMoimJpaEntity = JoinedMoimJpaEntity.builder()
                    .memberId(i)
                    .moimId(savedMoim1.getId())
                    .build();
            joinedMoimRepository.save(joinedMoimJpaEntity);
        }

        for(int i  = 0 ; i < MOIM_MEMBER_SIZE; i++){
            JoinedMoimJpaEntity joinedMoimJpaEntity = JoinedMoimJpaEntity.builder()
                    .memberId(i)
                    .moimId(savedMoim2.getId())
                    .build();
            joinedMoimRepository.save(joinedMoimJpaEntity);
        }


        // when
        List<JoinedMoimJpaEntity> membersByMoimId = joinedMoimFinder.findByMoimId(savedMoim1.getId());

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
        assertThatThrownBy(() -> joinedMoimFinder.validateMemberInMoim(2L, savedMoim.getId()))
                .isInstanceOf(NotFoundException.class)
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
                joinedMoimFinder.validateMemberInMoim(1L, savedJoined.getMoimId()))
                .doesNotThrowAnyException();
    }

    @DisplayName("모임에 참가중인 회원이면 검증에 성공한다.")
    @Test
    void validateJoinedMemberTest() {
        //given
        long moimId = MOIM_ID.longValue();
        long memberId = MEMBER_ID.longValue();

        JoinedMoimJpaEntity joinedMoimJpaEntity = JoinedMoimJpaEntity.builder()
                .moimId(moimId)
                .memberId(memberId)
                .build();

        joinedMoimRepository.save(joinedMoimJpaEntity);

        //expected
        assertThatCode(() -> joinedMoimFinder.validateMemberInMoim(memberId, moimId))
                .doesNotThrowAnyException();
    }

    @DisplayName("모임에 참가중인 회원이 아니면 예외가 발생한다.")
    @Test
    void validateJoinedMemberForbiddenTest() {
        //given
        long moimId = MOIM_ID.longValue();
        long memberId = MEMBER_ID.longValue();

        //expected
        assertThatThrownBy(() -> joinedMoimFinder.validateMemberInMoim(memberId, moimId))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(JOINED_MOIM_MEMBER_NOT_FOUND.message());
    }

    @DisplayName("모임에 참여한 회원은 true를 반환한다.")
    @Test
    void isJoiningTrue() {
        // given
        long moimId = MOIM_ID.longValue();
        long memberId = MEMBER_ID.longValue();

        JoinedMoimJpaEntity joinedMoimJpaEntity = JoinedMoimJpaEntity.builder()
                .moimId(moimId)
                .memberId(memberId)
                .build();

        joinedMoimRepository.save(joinedMoimJpaEntity);

        // when
        boolean isJoined = joinedMoimFinder.isJoining(moimId, memberId);

        // then
        assertThat(isJoined).isTrue();
    }

    @DisplayName("모임에 참여하지 않은 회원은 false 반환한다.")
    @Test
    void isJoiningFalse() {
        // given
        long moimId = MOIM_ID.longValue();
        long memberId = MEMBER_ID.longValue();

        // when
        boolean isJoined = joinedMoimFinder.isJoining(moimId, memberId);

        // then
        assertThat(isJoined).isFalse();
    }

    @DisplayName("내가 참여한 모임을 지난모임/진행중인 모임 별로 조회한다.")
    @Test
    void findAllMyJoinedMoimSimpleResponses() {
        // given1
        long lastMoimId = 0;
        long memberId = MEMBER_ID.longValue();
        long otherMemberId = MEMBER_ID.longValue() + 1;

        MoimJpaEntity moimA = MoimJpaEntity.builder()
                .memberId(memberId)
                .endDate(LocalDate.of(2024,5,23))
                .build();

        // given2
        MoimJpaEntity moimB = MoimJpaEntity.builder()
                .memberId(memberId)
                .endDate(LocalDate.of(2024,5,23))
                .build();

        // given3
        MoimJpaEntity moimC = MoimJpaEntity.builder()
                .memberId(otherMemberId)
                .endDate(LocalDate.of(2024,5,23))
                .build();

        moimRepository.save(moimA);
        moimRepository.save(moimB);
        moimRepository.save(moimC);

        // given4
        JoinedMoimJpaEntity jm1 = JoinedMoimJpaEntity.builder()
                .moimId(moimA.getId())
                .memberId(memberId)
                .build();

        // given5
        JoinedMoimJpaEntity jm2 = JoinedMoimJpaEntity.builder()
                .moimId(moimB.getId())
                .memberId(memberId)
                .build();

        // given6
        JoinedMoimJpaEntity jm3 = JoinedMoimJpaEntity.builder()
                .moimId(moimC.getId())
                .memberId(memberId)
                .build();

        // given7
        JoinedMoimJpaEntity jm4 = JoinedMoimJpaEntity.builder()
                .moimId(moimA.getId())
                .memberId(otherMemberId)
                .build();

        joinedMoimRepository.save(jm1);
        joinedMoimRepository.save(jm2);
        joinedMoimRepository.save(jm3);
        joinedMoimRepository.save(jm4);

        //when
        List<MoimSimpleResponse> inProgressAllMyJoinedMoimSimpleResponses = joinedMoimFinder.findAllMyJoinedMoimSimpleResponses(memberId, lastMoimId, LocalDate.of(2024, 5, 24), false);
        List<MoimSimpleResponse> endedAllMyJoinedMoimSimpleResponses = joinedMoimFinder.findAllMyJoinedMoimSimpleResponses(memberId, lastMoimId, LocalDate.of(2024, 5, 24), true);

        //then
        assertThat(inProgressAllMyJoinedMoimSimpleResponses.size()).isEqualTo(0);
        assertThat(endedAllMyJoinedMoimSimpleResponses.size()).isEqualTo(3);
    }

    @DisplayName("내가 참여한 모임을 지난모임/진행중인 모임 별로 조회할때, No Offset 으로 페이징 처리한다.")
    @Test
    void findAllMyJoinedMoimSimpleResponsesPaging() {
        // given1
        long lastMoimId = 0;
        long memberId = MEMBER_ID.longValue();
        long otherMemberId = MEMBER_ID.longValue() + 1;

        MoimJpaEntity moimA = MoimJpaEntity.builder()
                .memberId(memberId)
                .endDate(LocalDate.of(2024,5,23))
                .build();

        // given2
        MoimJpaEntity moimB = MoimJpaEntity.builder()
                .memberId(memberId)
                .endDate(LocalDate.of(2024,5,23))
                .build();

        // given3
        MoimJpaEntity moimC = MoimJpaEntity.builder()
                .memberId(otherMemberId)
                .endDate(LocalDate.of(2024,5,23))
                .build();

        // given4
        MoimJpaEntity moimD = MoimJpaEntity.builder()
                .memberId(otherMemberId)
                .endDate(LocalDate.of(2024,5,23))
                .build();

        // given5
        MoimJpaEntity moimE = MoimJpaEntity.builder()
                .memberId(otherMemberId)
                .endDate(LocalDate.of(2024,5,23))
                .build();

        // given6
        MoimJpaEntity moimF = MoimJpaEntity.builder()
                .memberId(otherMemberId)
                .endDate(LocalDate.of(2024,5,23))
                .build();

        moimRepository.save(moimA);
        moimRepository.save(moimB);
        moimRepository.save(moimC);
        moimRepository.save(moimD);
        moimRepository.save(moimE);
        moimRepository.save(moimF);

        // given7
        JoinedMoimJpaEntity jm1 = JoinedMoimJpaEntity.builder()
                .moimId(moimA.getId())
                .memberId(memberId)
                .build();

        // given8
        JoinedMoimJpaEntity jm2 = JoinedMoimJpaEntity.builder()
                .moimId(moimB.getId())
                .memberId(memberId)
                .build();

        // given9
        JoinedMoimJpaEntity jm3 = JoinedMoimJpaEntity.builder()
                .moimId(moimC.getId())
                .memberId(memberId)
                .build();

        // given10
        JoinedMoimJpaEntity jm4 = JoinedMoimJpaEntity.builder()
                .moimId(moimD.getId())
                .memberId(memberId)
                .build();

        // given11
        JoinedMoimJpaEntity jm5 = JoinedMoimJpaEntity.builder()
                .moimId(moimE.getId())
                .memberId(memberId)
                .build();

        // given12
        JoinedMoimJpaEntity jm6 = JoinedMoimJpaEntity.builder()
                .moimId(moimF.getId())
                .memberId(memberId)
                .build();

        joinedMoimRepository.save(jm1);
        joinedMoimRepository.save(jm2);
        joinedMoimRepository.save(jm3);
        joinedMoimRepository.save(jm4);
        joinedMoimRepository.save(jm5);
        joinedMoimRepository.save(jm6);

        //when
        List<MoimSimpleResponse> endedAllMyJoinedMoimSimpleResponses1 = joinedMoimFinder.findAllMyJoinedMoimSimpleResponses(memberId, lastMoimId, LocalDate.of(2024, 5, 24), true);
        MoimSimpleResponse lastMoim = endedAllMyJoinedMoimSimpleResponses1.get(DEFAULT_JOINED_MOIM_PAGE_SIZE.value() - 1);
        lastMoimId = lastMoim.moimId();
        List<MoimSimpleResponse> endedAllMyJoinedMoimSimpleResponses2 = joinedMoimFinder.findAllMyJoinedMoimSimpleResponses(memberId, lastMoimId, LocalDate.of(2024, 5, 24), true);


        //then
        assertThat(endedAllMyJoinedMoimSimpleResponses1.size()).isEqualTo(4);
        assertThat(endedAllMyJoinedMoimSimpleResponses2.size()).isEqualTo(2);
    }
}