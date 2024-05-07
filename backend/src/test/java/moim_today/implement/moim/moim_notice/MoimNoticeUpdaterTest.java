package moim_today.implement.moim.moim_notice;

import moim_today.dto.moim.moim_notice.MoimNoticeUpdateRequest;
import moim_today.global.error.ForbiddenException;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import moim_today.persistence.entity.moim.moim_notice.MoimNoticeJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;

import static java.util.Objects.requireNonNull;
import static moim_today.global.constant.exception.MoimExceptionConstant.ORGANIZER_FORBIDDEN_ERROR;
import static moim_today.util.TestConstant.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MoimNoticeUpdaterTest extends ImplementTest {

    @Autowired
    private MoimNoticeUpdater moimNoticeUpdater;

    @Autowired
    private MoimNoticeFinder moimNoticeFinder;

    @Autowired
    private CacheManager cacheManager;
    
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
        moimNoticeUpdater.updateMoimNotice(memberId, moimId, updateRequest);

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
        assertThatThrownBy(() -> moimNoticeUpdater.updateMoimNotice(forbiddenMemberId, moimId, updateRequest))
                .isInstanceOf(ForbiddenException.class)
                .hasMessage(ORGANIZER_FORBIDDEN_ERROR.message());
    }

    @DisplayName("공지를 수정하면 캐시가 삭제된다.")
    @Test
    void deleteMoimNoticeCachingTest(){
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

        //given
        clearCache();
        moimNoticeFinder.findAllMoimNotice(moimId);
        moimNoticeFinder.getById(noticeId);
        Object noticesCacheObject = requireNonNull(cacheManager.getCache("moimNotices")).get(moimId, Object.class);
        Object noticeCacheObject = requireNonNull(cacheManager.getCache("moimNotice")).get(noticeId, Object.class);
        assertThat(noticesCacheObject).isNotNull();
        assertThat(noticeCacheObject).isNotNull();

        //when
        moimNoticeUpdater.updateMoimNotice(memberId, moimId, updateRequest);

        //then
        noticesCacheObject = requireNonNull(cacheManager.getCache("moimNotices")).get(moimId, Object.class);
        noticeCacheObject = requireNonNull(cacheManager.getCache("moimNotice")).get(noticeId, Object.class);
        assertThat(noticesCacheObject).isNull();
        assertThat(noticeCacheObject).isNull();
    }
}