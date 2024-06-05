package moim_today.implement.moim.moim;

import jakarta.servlet.http.Cookie;
import moim_today.domain.moim.ViewedMoim;
import moim_today.domain.moim.ViewedMoimsCookie;
import moim_today.domain.moim.enums.MoimCategory;
import moim_today.dto.moim.moim.MoimUpdateRequest;
import moim_today.global.error.ForbiddenException;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.Objects.requireNonNull;
import static moim_today.global.constant.MoimConstant.VIEWED_MOIM_COOKIE_NAME;
import static moim_today.global.constant.exception.MoimExceptionConstant.ORGANIZER_FORBIDDEN_ERROR;
import static moim_today.util.TestConstant.*;
import static org.assertj.core.api.Assertions.*;

class MoimUpdaterTest extends ImplementTest {

    @Autowired
    private MoimUpdater moimUpdater;

    @DisplayName("자신이 만든 모임을 수정하려고 시도하면 수정에 성공한다.")
    @Test
    void updateMoimTest(){
        //given
        long memberId = Long.parseLong(MEMBER_ID.value());

        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .memberId(memberId)
                .build();

        moimRepository.save(moimJpaEntity);
        long moimId = moimJpaEntity.getId();

        int capacity = Integer.parseInt(CAPACITY.value());
        LocalDate startDate = LocalDate.of(2024, 3, 1);
        LocalDate endDate = LocalDate.of(2024, 6, 30);

        MoimUpdateRequest moimUpdateRequest = MoimUpdateRequest.builder()
                .moimId(moimId)
                .title(MOIM_TITLE.value())
                .contents(MOIM_CONTENTS.value())
                .capacity(capacity)
                .imageUrl(MOIM_IMAGE_URL.value())
                .moimCategory(MoimCategory.STUDY)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        //when & //then
        assertThatCode(() -> moimUpdater.updateMoim(memberId, moimUpdateRequest))
                .doesNotThrowAnyException();

        MoimJpaEntity updatedMoim = moimRepository.getById(moimId);
        assertThat(updatedMoim.getTitle()).isEqualTo(MOIM_TITLE.value());
        assertThat(updatedMoim.getContents()).isEqualTo(MOIM_CONTENTS.value());
        assertThat(updatedMoim.getCapacity()).isEqualTo(capacity);
        assertThat(updatedMoim.getImageUrl()).isEqualTo(MOIM_IMAGE_URL.value());
        assertThat(updatedMoim.getMoimCategory()).isEqualTo(MoimCategory.STUDY);
        assertThat(updatedMoim.getStartDate()).isEqualTo(startDate);
        assertThat(updatedMoim.getEndDate()).isEqualTo(endDate);
    }

    @DisplayName("수정 권한이 없는 모임을 수정하려고 시도하면 예외가 발생한다.")
    @Test
    void updateMoimFailureTest(){
        //given
        long memberId = Long.parseLong(MEMBER_ID.value());
        long forbiddenMemberId = memberId + 1;

        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .memberId(forbiddenMemberId)
                .build();

        moimRepository.save(moimJpaEntity);
        long moimId = moimJpaEntity.getId();

        MoimUpdateRequest forbiddenMoimUpdateRequest = MoimUpdateRequest.builder()
                .moimId(moimId)
                .build();

        //expected
        assertThatThrownBy(() -> moimUpdater.updateMoim(memberId, forbiddenMoimUpdateRequest))
                .isInstanceOf(ForbiddenException.class)
                .hasMessage(ORGANIZER_FORBIDDEN_ERROR.message());
    }

    @DisplayName("쿠키가(VIEWEDMOIMS) 없는 요청이면 모임의 조회수를 1증가시킨다.")
    @Test
    void updateMoimViewsTest(){
        //given1
        String nullOfViewedMoimsCookieByUrlEncoded = null;

        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .views(0)
                .build();

        //given2
        MockHttpServletResponse response = new MockHttpServletResponse();

        moimRepository.save(moimJpaEntity);
        long moimId = moimJpaEntity.getId();

        //when
        assertThatCode(() -> moimUpdater.updateMoimViews(moimId, nullOfViewedMoimsCookieByUrlEncoded, response))
                .doesNotThrowAnyException();

        // then
        Cookie cookie = response.getCookie(VIEWED_MOIM_COOKIE_NAME);
        MoimJpaEntity updatedMoim = moimRepository.getById(moimId);
        assertThat(updatedMoim.getViews()).isEqualTo(1);
        assertThat(cookie).isNotNull();
    }

    @DisplayName("쿠키(VIEWEDMOIMS)의 조회한 모임(ViewedMoim)의 만료시간(하루)이 지나지 않았으면 조회수가 증가하지 않는다.")
    @Test
    void updateMoimViewsWithNotExpiredViewedMoimTest(){
        //given1
        String nullOfViewedMoimsCookieByUrlEncoded = null;
        ArrayList<ViewedMoim> viewedMoims = new ArrayList<>();

        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .views(0)
                .build();

        //given2
        MockHttpServletResponse response = new MockHttpServletResponse();

        //given3
        ViewedMoimsCookie viewedMoimsCookie =
                ViewedMoimsCookie.createViewedMoimsCookieOrDefault(
                        nullOfViewedMoimsCookieByUrlEncoded,
                        viewedMoims,
                        objectMapper
                );

        moimRepository.save(moimJpaEntity);
        long moimId = moimJpaEntity.getId();
        viewedMoimsCookie.createAndAddViewedMoim(moimId);
        viewedMoimsCookie.addViewedMoimsCookieInCookie(response, objectMapper);
        String viewedMoimsCookieByUrlEncoded = requireNonNull(response.getCookie(VIEWED_MOIM_COOKIE_NAME)).getValue();

        //when
        assertThatCode(() -> moimUpdater.updateMoimViews(moimId, viewedMoimsCookieByUrlEncoded, response))
                .doesNotThrowAnyException();

        // then
        MoimJpaEntity updatedMoim = moimRepository.getById(moimId);
        assertThat(updatedMoim.getViews()).isEqualTo(0);
    }

    @DisplayName("쿠키(VIEWEDMOIMS)의 조회한 모임(ViewedMoim)의 만료시간(하루)이 지났으면 조회수가 1증가한다.")
    @Test
    void updateMoimViewsWithExpiredViewedMoimTest(){
        //given1
        String nullOfViewedMoimsCookieByUrlEncoded = null;
        ArrayList<ViewedMoim> viewedMoims = new ArrayList<>();

        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .views(0)
                .build();

        //given2
        MockHttpServletResponse response = new MockHttpServletResponse();

        //given3
        ViewedMoimsCookie viewedMoimsCookie =
                ViewedMoimsCookie.createViewedMoimsCookieOrDefault(
                        nullOfViewedMoimsCookieByUrlEncoded,
                        viewedMoims,
                        objectMapper
                );

        moimRepository.save(moimJpaEntity);
        long moimId = moimJpaEntity.getId();
        ViewedMoim expiredViewedMoim = ViewedMoim.of(moimId, LocalDateTime.now().minusDays(2));
        viewedMoimsCookie.viewedMoims().add(expiredViewedMoim);
        viewedMoimsCookie.addViewedMoimsCookieInCookie(response, objectMapper);
        String viewedMoimsCookieByUrlEncoded = requireNonNull(response.getCookie(VIEWED_MOIM_COOKIE_NAME)).getValue();

        //when
        assertThatCode(() -> moimUpdater.updateMoimViews(moimId, viewedMoimsCookieByUrlEncoded, response))
                .doesNotThrowAnyException();

        // then
        MoimJpaEntity updatedMoim = moimRepository.getById(moimId);
        assertThat(updatedMoim.getViews()).isEqualTo(1);
    }

    @DisplayName("모임의 현재 인원을 업데이트한다")
    @Test
    void updateMoimCurrentCount() {
        // given
        MoimJpaEntity moim = MoimJpaEntity.builder()
                .currentCount(0)
                .build();

        moimRepository.save(moim);

        // when
        moimUpdater.updateMoimCurrentCount(moim.getId(), 1);
        MoimJpaEntity m = moimRepository.getById(moim.getId());

        // then
        assertThat(m.getCurrentCount()).isEqualTo(1);
    }

    @DisplayName("모임의 현재 인원을 5명이 동시에 1씩 증가시켜도 5로 업데이트 되어야한다.")
    @Test
    void updateConcurrentMoimCurrentCount() throws Exception{
        // given
        final int FIVE_PEOPLE = 5;
        final int MOIM_MAXIMUM_PEOPLE = 10;

        ExecutorService executorService = Executors.newFixedThreadPool(FIVE_PEOPLE);
        CountDownLatch latch = new CountDownLatch(FIVE_PEOPLE);

        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .capacity(MOIM_MAXIMUM_PEOPLE)
                .memberId(MEMBER_ID.longValue())
                .build();

        MoimJpaEntity savedMoim = moimRepository.save(moimJpaEntity);

        // when
        for (int i = 0; i < FIVE_PEOPLE; i++) {
            executorService.submit(() -> {
                try {
                    moimUpdater.updateMoimCurrentCount(savedMoim.getId(), 1);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        MoimJpaEntity resultMoim = moimRepository.getById(savedMoim.getId());

        // then
        assertThat(resultMoim.getCurrentCount()).isEqualTo(FIVE_PEOPLE);
    }
}
