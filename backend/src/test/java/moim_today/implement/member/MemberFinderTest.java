package moim_today.implement.member;

import moim_today.global.error.NotFoundException;
import moim_today.persistence.entity.member.MemberJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static moim_today.global.constant.exception.MemberExceptionConstant.EMAIL_NOT_FOUND_ERROR;
import static moim_today.util.TestConstant.EMAIL;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberFinderTest extends ImplementTest {

    @Autowired
    private MemberFinder memberFinder;

    @DisplayName("해당 이메일을 가진 회원이 존재하지 않으면 예외가 발생한다.")
    @Test
    void validateEmailNotExists() {
        // expected
        assertThatThrownBy(() -> memberFinder.validateEmailExists(EMAIL.value()))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(EMAIL_NOT_FOUND_ERROR.message());
    }

    @DisplayName("해당 이메일을 가진 회원이 존재하면 검증에 성공한다.")
    @Test
    void validateEmailExists() {
        // given
        MemberJpaEntity memberJpaEntity = MemberJpaEntity.builder()
                .email(EMAIL.value())
                .build();

        memberRepository.save(memberJpaEntity);

        // when && then
        memberFinder.validateEmailExists(EMAIL.value());
    }
}