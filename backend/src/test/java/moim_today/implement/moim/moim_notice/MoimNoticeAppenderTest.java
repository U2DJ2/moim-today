package moim_today.implement.moim.moim_notice;

import moim_today.dto.moim.moim_notice.MoimNoticeCreateRequest;
import moim_today.global.error.ForbiddenException;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static moim_today.global.constant.exception.MoimExceptionConstant.MOIM_FORBIDDEN;
import static moim_today.util.TestConstant.*;
import static org.assertj.core.api.Assertions.*;

class MoimNoticeAppenderTest extends ImplementTest {

    @Autowired
    private MoimNoticeAppender moimNoticeAppender;

    @DisplayName("공지를 생성한다.")
    @Test
    void createNoticeTest() {
        //given
        MoimJpaEntity owner = MoimJpaEntity.builder()
                .memberId(MEMBER_ID.longValue())
                .build();

        moimRepository.save(owner);
        long moimId = owner.getId();

        MoimNoticeCreateRequest moimNoticeCreateRequest = new MoimNoticeCreateRequest(
                moimId,
                TITLE.value(),
                CONTENTS.value()
        );

        //when
        assertThatCode(() -> moimNoticeAppender.createMoimNotice(MEMBER_ID.longValue(), moimNoticeCreateRequest))
                .doesNotThrowAnyException();

        //then
        assertThat( moimNoticeRepository.count()).isEqualTo(1);
    }

    @DisplayName("공지 생성에 접근 권한이 없으면 예외가 발생한다.")
    @Test
    void createNoticeThrowsExceptionTest() {
        //given
        long memberId = MEMBER_ID.longValue();
        long forbiddenMemberId = memberId + 1L;

        MoimJpaEntity owner = MoimJpaEntity.builder()
                .memberId(memberId)
                .build();

        moimRepository.save(owner);
        long moimId = owner.getId();

        MoimNoticeCreateRequest moimNoticeCreateRequest = new MoimNoticeCreateRequest(
                moimId,
                TITLE.value(),
                CONTENTS.value()
        );

        //expected
        assertThatThrownBy(() -> moimNoticeAppender.createMoimNotice(forbiddenMemberId, moimNoticeCreateRequest))
                .isInstanceOf(ForbiddenException.class)
                .hasMessage(MOIM_FORBIDDEN.message());
    }
}