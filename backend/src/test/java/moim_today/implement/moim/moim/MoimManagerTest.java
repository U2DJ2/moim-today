package moim_today.implement.moim.moim;

import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class MoimManagerTest extends ImplementTest {

    @Autowired
    private MoimManager moimManager;

    @DisplayName("모임에서 멤버를 삭제한다")
    @Test
    void deleteMemberFromMoim() {

    }

    @DisplayName("모임에 멤버를 추가한다")
    @Test
    void appendMemberToMoim() {

    }

    private void initMoim(){
        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .build();
    }
}