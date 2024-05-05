package moim_today.implement.moim.moim_notice;

import moim_today.dto.moim.moim_notice.MoimNoticeUpdateRequest;
import moim_today.global.error.ForbiddenException;
import moim_today.global.error.NotFoundException;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import moim_today.persistence.entity.moim.moim_notice.MoimNoticeJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static moim_today.global.constant.exception.MoimExceptionConstant.NOTICE_NOT_FOUND_ERROR;
import static moim_today.global.constant.exception.MoimExceptionConstant.ORGANIZER_FORBIDDEN_ERROR;
import static moim_today.util.TestConstant.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MoimNoticeUpdaterTest extends ImplementTest {

    @Autowired
    private MoimNoticeUpdater moimNoticeUpdater;
    
    @DisplayName("공지를 수정한다.")
    @Test
    void updateMoimNoticeTest(){
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
        MoimNoticeUpdateRequest updateRequest = MoimNoticeUpdateRequest.builder()
                .moimNoticeId(noticeId)
                .title(NOTICE_TITLE.value())
                .contents(NOTICE_CONTENTS.value())
                .build();

        //when
        moimNoticeUpdater.updateMoimNotice(memberId, updateRequest);

        //then
        MoimNoticeJpaEntity updateNoticeEntity = moimNoticeRepository.getById(noticeId);
        assertThat(updateNoticeEntity.getTitle()).isEqualTo(NOTICE_TITLE.value());
        assertThat(updateNoticeEntity.getContents()).isEqualTo(NOTICE_CONTENTS.value());
    }

    @DisplayName("주최자가 아닌 사람은 공지를 수정하려고 하면 예외가 발생한다.")
    @Test
    void updateMoimNoticeForbiddenTest(){
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
        MoimNoticeUpdateRequest updateRequest = MoimNoticeUpdateRequest.builder()
                .moimNoticeId(noticeId)
                .title(NOTICE_TITLE.value())
                .contents(NOTICE_CONTENTS.value())
                .build();

        //expected
        assertThatThrownBy(() -> moimNoticeUpdater.updateMoimNotice(forbiddenMemberId, updateRequest))
                .isInstanceOf(ForbiddenException.class)
                .hasMessage(ORGANIZER_FORBIDDEN_ERROR.message());
    }

    @DisplayName("존재하지 않는 공지를 수정하려고 하면 예외가 발생한다..")
    @Test
    void updateMoimNoticeNotFoundTest(){
        //given
        long memberId = MEMBER_ID.longValue();

        //given
        MoimNoticeUpdateRequest updateRequest = MoimNoticeUpdateRequest.builder()
                .moimNoticeId(NOTICE_ID.longValue())
                .title(NOTICE_TITLE.value())
                .contents(NOTICE_CONTENTS.value())
                .build();

        //expected
        assertThatThrownBy(() -> moimNoticeUpdater.updateMoimNotice(memberId, updateRequest))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(NOTICE_NOT_FOUND_ERROR.message());
    }
}