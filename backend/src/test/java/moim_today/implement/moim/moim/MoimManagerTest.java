package moim_today.implement.moim.moim;

import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static moim_today.util.TestConstant.MEMBER_ID;
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
                .memberId(MEMBER_ID.longValue())
                .build();

        MoimJpaEntity savedMoim = moimRepository.save(moimJpaEntity);
        long savedMoimId = savedMoim.getId();

        for (int i = 0; i < PARTICIPATION_PEOPLE; i++) {
            final long memberId = i + 1;
            executorService.submit(() -> {
                try {
                    moimManager.appendMemberToMoim(memberId, savedMoimId);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        long joinedMembersCount =  joinedMoimRepository.findAllJoinedMemberId(savedMoimId).size();
        assertThat(joinedMembersCount).isEqualTo(MOIM_MAXIMUM_PEOPLE);
    }
}