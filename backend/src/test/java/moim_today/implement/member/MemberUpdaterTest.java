package moim_today.implement.member;

import moim_today.persistence.entity.member.MemberJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static moim_today.util.TestConstant.*;
import static org.assertj.core.api.Assertions.*;

class MemberUpdaterTest extends ImplementTest {

    @Autowired
    private MemberUpdater memberUpdater;

    @DisplayName("사용자가 입력한 비밀번호를 암호화하여 수정한다.")
    @Test
    void updatePassword() {
        // given
        MemberJpaEntity memberJpaEntity = MemberJpaEntity.builder()
                .password(PASSWORD.value())
                .build();

        memberRepository.save(memberJpaEntity);
        long memberId = memberJpaEntity.getId();

        // when
        memberUpdater.updatePassword(memberId, "newPassword");

        // then
        MemberJpaEntity findEntity = memberRepository.getById(memberId);
        boolean isMatched = passwordEncoder.matches("newPassword", findEntity.getPassword());
        assertThat(isMatched).isTrue();
    }
}