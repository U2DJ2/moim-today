package moim_today.implement.moim.moim_notice;

import moim_today.dto.moim.moim_notice.MoimNoticeCreateRequest;
import moim_today.global.error.ForbiddenException;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import moim_today.persistence.entity.moim.moim_notice.MoimNoticeJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static moim_today.global.constant.exception.MoimExceptionConstant.ORGANIZER_FORBIDDEN_ERROR;
import static moim_today.util.TestConstant.*;
import static org.assertj.core.api.Assertions.*;

class MoimNoticeAppenderTest extends ImplementTest {

    @Autowired
    private MoimNoticeAppender moimNoticeAppender;

    @Autowired
    private MoimNoticeFinder moimNoticeFinder;

    @Autowired
    private CacheManager cacheManager;

    @DisplayName("공지를 생성한다.")
    @Test
    void createNoticeTest() {
        //given
        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .memberId(MEMBER_ID.longValue())
                .build();

        moimRepository.save(moimJpaEntity);
        long moimId = moimJpaEntity.getId();

        MoimNoticeCreateRequest moimNoticeCreateRequest = new MoimNoticeCreateRequest(
                moimId,
                NOTICE_TITLE.value(),
                NOTICE_CONTENTS.value()
        );

        //when
        assertThatCode(() -> moimNoticeAppender.createMoimNotice(MEMBER_ID.longValue(), moimNoticeCreateRequest.moimId(), moimNoticeCreateRequest))
                .doesNotThrowAnyException();

        //then
        assertThat(moimNoticeRepository.count()).isEqualTo(1);
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
                NOTICE_TITLE.value(),
                NOTICE_CONTENTS.value()
        );

        //expected
        assertThatThrownBy(() -> moimNoticeAppender.createMoimNotice(forbiddenMemberId, moimNoticeCreateRequest.moimId(), moimNoticeCreateRequest))
                .isInstanceOf(ForbiddenException.class)
                .hasMessage(ORGANIZER_FORBIDDEN_ERROR.message());
    }

    @DisplayName("모임 공지를 새로 생성하면 공지 리스트 캐시를 삭제한다.")
    @Test
    void createNoticeDeleteCacheTest() {
        //given1
        long memberId = MEMBER_ID.longValue();
        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .memberId(memberId)
                .build();

        moimRepository.save(moimJpaEntity);
        long moimId = moimJpaEntity.getId();

        //caching1
        moimNoticeFinder.findAllMoimNotice(moimId);

        //given2
        MoimNoticeCreateRequest moimNoticeCreateRequest = new MoimNoticeCreateRequest(
                moimId,
                NOTICE_TITLE.value(),
                NOTICE_CONTENTS.value()
        );

        //when
        moimNoticeAppender.createMoimNotice(memberId, moimId, moimNoticeCreateRequest);
        moimNoticeFinder.findAllMoimNotice(moimId);

        //then
        Object noticesCacheObject = requireNonNull(cacheManager.getCache("moimNotices")).get(moimId, Object.class);
        assert noticesCacheObject != null;
        List<?> noticesList = (List<?>) noticesCacheObject;
        assertThat(noticesList.size()).isEqualTo(1);
    }
}