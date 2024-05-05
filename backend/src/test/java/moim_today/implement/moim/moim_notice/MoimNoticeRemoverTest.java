package moim_today.implement.moim.moim_notice;

import moim_today.dto.moim.moim_notice.MoimNoticeDeleteRequest;
import moim_today.global.error.ForbiddenException;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import moim_today.persistence.entity.moim.moim_notice.MoimNoticeJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static moim_today.global.constant.exception.MoimExceptionConstant.ORGANIZER_FORBIDDEN_ERROR;
import static moim_today.util.TestConstant.MEMBER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class MoimNoticeRemoverTest extends ImplementTest {

    @Autowired
    private MoimNoticeRemover moimNoticeRemover;
    
    @DisplayName("공지를 삭제한다.")
    @Test
    void deleteMoimNoticeTest(){
        //given
        long memberId = MEMBER_ID.longValue();

        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .memberId(memberId)
                .build();

        moimRepository.save(moimJpaEntity);
        long moimId = moimJpaEntity.getId();

        //given
        MoimNoticeJpaEntity noticeJpaEntity = MoimNoticeJpaEntity.builder()
                .moimId(moimId)
                .build();

        moimNoticeRepository.save(noticeJpaEntity);
        long noticeId = noticeJpaEntity.getId();

        //given
        MoimNoticeDeleteRequest deleteRequest = new MoimNoticeDeleteRequest(noticeId);

        //when
        assertThatCode(() -> moimNoticeRemover.deleteMoimNotice(memberId, deleteRequest))
                .doesNotThrowAnyException();

        //then
        assertThat(moimNoticeRepository.count()).isEqualTo(0);
    }

    @DisplayName("주최자가 아닌 회원이 공지를 삭제하려고 하면 얘외가 발생한다.")
    @Test
    void deleteMoimNoticeForbiddenTest(){
        //given
        long organizerId = MEMBER_ID.longValue();
        long forbiddenMemberId = organizerId + 1;

        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .memberId(organizerId)
                .build();

        moimRepository.save(moimJpaEntity);
        long moimId = moimJpaEntity.getId();

        //given
        MoimNoticeJpaEntity noticeJpaEntity = MoimNoticeJpaEntity.builder()
                .moimId(moimId)
                .build();

        moimNoticeRepository.save(noticeJpaEntity);
        long noticeId = noticeJpaEntity.getId();

        //given
        MoimNoticeDeleteRequest deleteRequest = new MoimNoticeDeleteRequest(noticeId);

        //expected
        assertThatCode(() -> moimNoticeRemover.deleteMoimNotice(forbiddenMemberId, deleteRequest))
                .isInstanceOf(ForbiddenException.class)
                .hasMessage(ORGANIZER_FORBIDDEN_ERROR.message());
    }
}