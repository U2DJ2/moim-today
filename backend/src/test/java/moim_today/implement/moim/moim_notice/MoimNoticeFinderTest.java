package moim_today.implement.moim.moim_notice;

import moim_today.dto.moim.moim_notice.MoimNoticeSimpleResponse;
import moim_today.global.error.NotFoundException;
import moim_today.persistence.entity.moim.moim_notice.MoimNoticeJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static moim_today.global.constant.NumberConstant.MAX_MOIMHONE_NOTICE_DISPLAY_COUNT;
import static moim_today.global.constant.exception.MoimExceptionConstant.NOTICE_NOT_FOUND_ERROR;
import static moim_today.util.TestConstant.*;
import static org.assertj.core.api.Assertions.*;

class MoimNoticeFinderTest extends ImplementTest {

    @Autowired
    private MoimNoticeFinder moimNoticeFinder;

    @Autowired
    private CacheManager cacheManager;

    @DisplayName("모임Id로 모든 공지를 찾는다.")
    @Test
    void findAllMoimNoticeTest() {
        //given
        long lastMoimNoticeId = 0;
        long moimId = MOIM_ID.longValue();

        //given
        MoimNoticeJpaEntity moimNoticeJpaEntity1 = MoimNoticeJpaEntity
                .builder()
                .moimId(moimId)
                .build();

        //given
        MoimNoticeJpaEntity moimNoticeJpaEntity2 = MoimNoticeJpaEntity
                .builder()
                .moimId(moimId)
                .build();

        moimNoticeRepository.save(moimNoticeJpaEntity1);
        moimNoticeRepository.save(moimNoticeJpaEntity2);

        //when
        List<MoimNoticeSimpleResponse> moimNoticeResponses = moimNoticeFinder
                .findAllMoimNotice(moimId, lastMoimNoticeId);

        //then
        assertThat(moimNoticeResponses.size()).isEqualTo(2);
    }

    @DisplayName("공지Id로 공지 정보를 조회한다.")
    @Test
    void getMoimNoticeTest() {
        //given
        long moimId = MOIM_ID.longValue();

        //given
        MoimNoticeJpaEntity moimNoticeJpaEntity = MoimNoticeJpaEntity
                .builder()
                .moimId(moimId)
                .title(NOTICE_TITLE.value())
                .build();
        
        moimNoticeRepository.save(moimNoticeJpaEntity);
        long noticeId = moimNoticeJpaEntity.getId();

        //when
        MoimNoticeJpaEntity findEntity = moimNoticeFinder.getById(noticeId);

        //then
        assertThat(findEntity.getTitle()).isEqualTo(NOTICE_TITLE.value());
    }

    @DisplayName("존재하지 않는 모임을 조회하려고 하면 예외가 발생한다")
    @Test
    void getMoimNoticeNotFoundTest() {
        //given
        long notFoundNoticeId = NOT_FOUND_NOTICE_ID.longValue();

        //expected
        assertThatThrownBy(() -> moimNoticeFinder.getById(notFoundNoticeId))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(NOTICE_NOT_FOUND_ERROR.message());
    }

    @DisplayName("공지 정보를 조회하면 캐시에 저장된다.")
    @Test
    void NoticeCachingTest() {
        //given
        long moimId = MOIM_ID.longValue();

        //given
        MoimNoticeJpaEntity moimNoticeJpaEntity = MoimNoticeJpaEntity
                .builder()
                .moimId(moimId)
                .title(NOTICE_TITLE.value())
                .build();

        moimNoticeRepository.save(moimNoticeJpaEntity);
        long noticeId = moimNoticeJpaEntity.getId();

        //when
        assertThatCode(() -> moimNoticeFinder.getById(noticeId))
                .doesNotThrowAnyException();

        //then
        Object noticeCacheObject = requireNonNull(cacheManager.getCache("moimNotice")).get(noticeId, Object.class);
        assertThat(noticeCacheObject).isNotNull();
    }

    @DisplayName("모임Id로 모든 공지를 조회할 때, 페이징 처리를 한다.")
    @Test
    void findAllMoimNoticeTestPaging() {
        //given
        long moimId = MOIM_ID.longValue();

        //given1
        MoimNoticeJpaEntity moimNoticeJpaEntity1 = MoimNoticeJpaEntity
                .builder()
                .moimId(moimId)
                .build();

        //given2
        MoimNoticeJpaEntity moimNoticeJpaEntity2 = MoimNoticeJpaEntity
                .builder()
                .moimId(moimId)
                .build();

        //given3
        MoimNoticeJpaEntity moimNoticeJpaEntity3 = MoimNoticeJpaEntity
                .builder()
                .moimId(moimId)
                .build();

        //given4
        MoimNoticeJpaEntity moimNoticeJpaEntity4 = MoimNoticeJpaEntity
                .builder()
                .moimId(moimId)
                .build();


        moimNoticeRepository.save(moimNoticeJpaEntity1);
        moimNoticeRepository.save(moimNoticeJpaEntity2);
        moimNoticeRepository.save(moimNoticeJpaEntity3);
        moimNoticeRepository.save(moimNoticeJpaEntity4);

        long noticeId1 = moimNoticeJpaEntity1.getId();
        long noticeId2 = moimNoticeJpaEntity2.getId();
        long noticeId3 = moimNoticeJpaEntity3.getId();
        long noticeId4 = moimNoticeJpaEntity4.getId();

        List<Long> expectedList = new ArrayList<>();
        expectedList.add(noticeId1);
        expectedList.add(noticeId2);
        expectedList.add(noticeId3);
        expectedList.add(noticeId4);
        expectedList.sort(Comparator.reverseOrder());

        //when
        long lastMoimNoticeId = 0;
        List<MoimNoticeSimpleResponse> moimNoticeResponses1 = moimNoticeFinder
                .findAllMoimNotice(moimId, lastMoimNoticeId);

        lastMoimNoticeId = expectedList.get(MAX_MOIMHONE_NOTICE_DISPLAY_COUNT.value() - 1);
        List<MoimNoticeSimpleResponse> moimNoticeResponses2 = moimNoticeFinder
                .findAllMoimNotice(moimId, lastMoimNoticeId);


        //then
        assertThat(moimNoticeResponses1.size()).isEqualTo(3);
        assertThat(moimNoticeResponses2.size()).isEqualTo(1);
    }

    @DisplayName("모임Id로 모든 공지를 조회할 때, 정렬된 값을 가져온다.")
    @Test
    void findAllMoimNoticeTestOrder() {
        //given
        long moimId = MOIM_ID.longValue();

        //given1
        MoimNoticeJpaEntity moimNoticeJpaEntity1 = MoimNoticeJpaEntity
                .builder()
                .moimId(moimId)
                .build();

        //given2
        MoimNoticeJpaEntity moimNoticeJpaEntity2 = MoimNoticeJpaEntity
                .builder()
                .moimId(moimId)
                .build();

        //given3
        MoimNoticeJpaEntity moimNoticeJpaEntity3 = MoimNoticeJpaEntity
                .builder()
                .moimId(moimId)
                .build();

        //given4
        MoimNoticeJpaEntity moimNoticeJpaEntity4 = MoimNoticeJpaEntity
                .builder()
                .moimId(moimId)
                .build();


        moimNoticeRepository.save(moimNoticeJpaEntity1);
        moimNoticeRepository.save(moimNoticeJpaEntity2);
        moimNoticeRepository.save(moimNoticeJpaEntity3);
        moimNoticeRepository.save(moimNoticeJpaEntity4);

        long noticeId1 = moimNoticeJpaEntity1.getId();
        long noticeId2 = moimNoticeJpaEntity2.getId();
        long noticeId3 = moimNoticeJpaEntity3.getId();
        long noticeId4 = moimNoticeJpaEntity4.getId();

        List<Long> expectedList = new ArrayList<>();
        expectedList.add(noticeId1);
        expectedList.add(noticeId2);
        expectedList.add(noticeId3);
        expectedList.add(noticeId4);
        expectedList.sort(Comparator.reverseOrder());

        //when
        long lastMoimNoticeId = 0;
        List<MoimNoticeSimpleResponse> moimNoticeResponses1 = moimNoticeFinder
                .findAllMoimNotice(moimId, lastMoimNoticeId);

        lastMoimNoticeId = expectedList.get(MAX_MOIMHONE_NOTICE_DISPLAY_COUNT.value() - 1);
        List<MoimNoticeSimpleResponse> moimNoticeResponses2 = moimNoticeFinder
                .findAllMoimNotice(moimId, lastMoimNoticeId);


        //then
        List<Long> actualList = moimNoticeResponses1.stream()
                .map(MoimNoticeSimpleResponse::moimNoticeId)
                .toList();
        assertThat(actualList.get(0)).isEqualTo(expectedList.get(0));
        assertThat(actualList.get(1)).isEqualTo(expectedList.get(1));
        assertThat(actualList.get(2)).isEqualTo(expectedList.get(2));

        assertThat(moimNoticeResponses2.get(0).moimNoticeId()).isEqualTo(expectedList.get(3));
    }
}