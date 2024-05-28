package moim_today.implement.moim.moim;

import moim_today.dto.moim.moim.MoimSimpleResponse;
import moim_today.persistence.entity.member.MemberJpaEntity;
import moim_today.persistence.entity.moim.joined_moim.JoinedMoimJpaEntity;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static moim_today.util.TestConstant.MEMBER_ID;
import static moim_today.util.TestConstant.USERNAME;
import static org.assertj.core.api.Assertions.assertThat;

class MoimManagerTest extends ImplementTest {

    @Autowired
    private MoimManager moimManager;

    @DisplayName("모임에 여석이 2자리 일 때 동시에 10명이 참여 요청하는 경우")
    @Test
    void validateConcurrencyTest() throws Exception{
        // given
        final int PARTICIPATION_PEOPLE = 10;
        final int MOIM_MAXIMUM_PEOPLE = 2;

        ExecutorService executorService = Executors.newFixedThreadPool(PARTICIPATION_PEOPLE);
        CountDownLatch latch = new CountDownLatch(PARTICIPATION_PEOPLE);

        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .capacity(MOIM_MAXIMUM_PEOPLE)
                .endDate(LocalDate.of(2024,6,30))
                .memberId(MEMBER_ID.longValue())
                .build();

        MoimJpaEntity savedMoim = moimRepository.save(moimJpaEntity);
        long savedMoimId = savedMoim.getId();

        // when
        for (int i = 0; i < PARTICIPATION_PEOPLE; i++) {
            final long memberId = i + 1;
            executorService.submit(() -> {
                try {
                    moimManager.appendMemberToMoim(memberId, savedMoimId, LocalDate.of(2024,5,27));
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        // then
        long joinedMembersCount =  joinedMoimRepository.findAllJoinedMemberId(savedMoimId).size();
        assertThat(joinedMembersCount).isEqualTo(MOIM_MAXIMUM_PEOPLE);
    }

    @DisplayName("자신이 참여한 모임들 중 완료 여부에 따라 반환한다.")
    @Test
    void findAllJoinedMoimSimpleResponseByEndStatus() {
        // given1
        MemberJpaEntity member1 = MemberJpaEntity.builder()
                .username(USERNAME.value())
                .build();

        memberRepository.save(member1);

        LocalDate localDate1 = LocalDate.of(2023, 5, 12);
        LocalDate localDate2 = LocalDate.of(2024, 5, 16);
        LocalDate localDate3 = LocalDate.of(2025, 6, 5);

        // given2
        MoimJpaEntity moimJpaEntity1 = MoimJpaEntity.builder()
                .memberId(member1.getId())
                .endDate(localDate1)
                .build();
        MoimJpaEntity moimJpaEntity2 = MoimJpaEntity.builder()
                .memberId(member1.getId())
                .endDate(localDate2)
                .build();
        MoimJpaEntity moimJpaEntity3 = MoimJpaEntity.builder()
                .memberId(member1.getId())
                .endDate(localDate3)
                .build();

        moimRepository.save(moimJpaEntity1);
        moimRepository.save(moimJpaEntity2);
        moimRepository.save(moimJpaEntity3);

        // given3
        JoinedMoimJpaEntity j1 = JoinedMoimJpaEntity.builder()
                .memberId(member1.getId())
                .moimId(moimJpaEntity1.getId())
                .build();
        JoinedMoimJpaEntity j2 = JoinedMoimJpaEntity.builder()
                .memberId(member1.getId())
                .moimId(moimJpaEntity2.getId())
                .build();
        JoinedMoimJpaEntity j3 = JoinedMoimJpaEntity.builder()
                .memberId(member1.getId())
                .moimId(moimJpaEntity3.getId())
                .build();

        joinedMoimRepository.save(j1);
        joinedMoimRepository.save(j2);
        joinedMoimRepository.save(j3);

        // when
        List<MoimSimpleResponse> endedMoims = moimManager.findAllJoinedMoimSimpleResponseByEndStatus(
                member1.getId(), LocalDate.of(2024, 5, 16), true);
        List<MoimSimpleResponse> inProgressMoims = moimManager.findAllJoinedMoimSimpleResponseByEndStatus(
                member1.getId(), LocalDate.of(2024, 5, 16), false);

        // then
        assertThat(endedMoims.size()).isEqualTo(1);
        assertThat(inProgressMoims.size()).isEqualTo(2);
    }
    @DisplayName("다른 멤버의 모임들을 제외한 자신이 참여한 모임들 중 완료 여부에 따라 반환한다.")
    @Test
    void findAllJoinedMoimSimpleResponseExceptOtherMembers() {
        // given
        MemberJpaEntity me = MemberJpaEntity.builder()
                .username(USERNAME.value())
                .build();
        MemberJpaEntity other = MemberJpaEntity.builder()
                .username(USERNAME.value()+"1")
                .build();

        memberRepository.save(me);
        memberRepository.save(other);

        LocalDate localDate1 = LocalDate.of(2023, 5, 12);
        LocalDate localDate2 = LocalDate.of(2024, 5, 16);

        MoimJpaEntity myMoim1 = MoimJpaEntity.builder()
                .memberId(me.getId())
                .endDate(localDate1)
                .build();
        MoimJpaEntity myMoim2 = MoimJpaEntity.builder()
                .memberId(me.getId())
                .endDate(localDate2)
                .build();
        MoimJpaEntity otherMoim1 = MoimJpaEntity.builder()
                .memberId(other.getId())
                .endDate(localDate1)
                .build();
        MoimJpaEntity otherMoim2 = MoimJpaEntity.builder()
                .memberId(other.getId())
                .endDate(localDate2)
                .build();

        moimRepository.save(myMoim1);
        moimRepository.save(myMoim2);
        moimRepository.save(otherMoim1);
        moimRepository.save(otherMoim2);

        JoinedMoimJpaEntity j1 = JoinedMoimJpaEntity.builder()
                .memberId(me.getId())
                .moimId(myMoim1.getId())
                .build();
        JoinedMoimJpaEntity j2 = JoinedMoimJpaEntity.builder()
                .memberId(me.getId())
                .moimId(myMoim2.getId())
                .build();
        JoinedMoimJpaEntity j3 = JoinedMoimJpaEntity.builder()
                .memberId(other.getId())
                .moimId(otherMoim1.getId())
                .build();
        JoinedMoimJpaEntity j4 = JoinedMoimJpaEntity.builder()
                .memberId(other.getId())
                .moimId(otherMoim2.getId())
                .build();

        joinedMoimRepository.save(j1);
        joinedMoimRepository.save(j2);
        joinedMoimRepository.save(j3);
        joinedMoimRepository.save(j4);

        // when
        List<MoimSimpleResponse> myEndedMoims = moimManager.findAllJoinedMoimSimpleResponseByEndStatus(
                me.getId(), LocalDate.of(2023,5,12), true);
        List<MoimSimpleResponse> myInProgressMoims = moimManager.findAllJoinedMoimSimpleResponseByEndStatus(
                me.getId(), LocalDate.of(2023, 5, 12), false);

        // then
        assertThat(myEndedMoims.size()).isEqualTo(0);
        assertThat(myInProgressMoims.size()).isEqualTo(2);
    }

    @DisplayName("자신이 호스트인 모임들의 정보만 완료 여부에 따라 가져온다")
    @Test
    void findAllHostMoimSimpleResponsesByEndStatus() {
        // given
        MemberJpaEntity me = MemberJpaEntity.builder()
                .username(USERNAME.value())
                .build();
        MemberJpaEntity other = MemberJpaEntity.builder()
                .username(USERNAME.value()+"1")
                .build();

        memberRepository.save(me);
        memberRepository.save(other);

        LocalDate localDate1 = LocalDate.of(2023, 5, 12);
        LocalDate localDate2 = LocalDate.of(2024, 5, 16);

        MoimJpaEntity myMoim1 = MoimJpaEntity.builder()
                .memberId(me.getId())
                .endDate(localDate1)
                .build();
        MoimJpaEntity myMoim2 = MoimJpaEntity.builder()
                .memberId(me.getId())
                .endDate(localDate2)
                .build();
        MoimJpaEntity otherMoim1 = MoimJpaEntity.builder()
                .memberId(other.getId())
                .endDate(localDate1)
                .build();
        MoimJpaEntity otherMoim2 = MoimJpaEntity.builder()
                .memberId(other.getId())
                .endDate(localDate2)
                .build();

        moimRepository.save(myMoim1);
        moimRepository.save(myMoim2);
        moimRepository.save(otherMoim1);
        moimRepository.save(otherMoim2);

        JoinedMoimJpaEntity j1 = JoinedMoimJpaEntity.builder()
                .memberId(me.getId())
                .moimId(myMoim1.getId())
                .build();
        JoinedMoimJpaEntity j2 = JoinedMoimJpaEntity.builder()
                .memberId(me.getId())
                .moimId(myMoim2.getId())
                .build();
        JoinedMoimJpaEntity j3 = JoinedMoimJpaEntity.builder()
                .memberId(other.getId())
                .moimId(otherMoim1.getId())
                .build();
        JoinedMoimJpaEntity j4 = JoinedMoimJpaEntity.builder()
                .memberId(other.getId())
                .moimId(otherMoim2.getId())
                .build();
        JoinedMoimJpaEntity j5 = JoinedMoimJpaEntity.builder()
                .memberId(me.getId())
                .moimId(otherMoim1.getId())
                .build();
        JoinedMoimJpaEntity j6 = JoinedMoimJpaEntity.builder()
                .memberId(me.getId())
                .moimId(otherMoim2.getId())
                .build();

        joinedMoimRepository.save(j1);
        joinedMoimRepository.save(j2);
        joinedMoimRepository.save(j3);
        joinedMoimRepository.save(j4);
        joinedMoimRepository.save(j5);
        joinedMoimRepository.save(j6);

        // when
        List<MoimSimpleResponse> myEndedMoims = moimManager.findAllHostMoimSimpleResponsesByEndStatus(
                me.getId(), LocalDate.of(2023,5,12), true);
        List<MoimSimpleResponse> myInProgressMoims = moimManager.findAllHostMoimSimpleResponsesByEndStatus(
                me.getId(), LocalDate.of(2023, 5, 12), false);

        // then
        assertThat(myEndedMoims.size()).isEqualTo(0);
        assertThat(myInProgressMoims.size()).isEqualTo(2);
    }
}