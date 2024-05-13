package moim_today.domain.moim;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import moim_today.global.error.InternalServerException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockCookie;
import org.springframework.mock.web.MockHttpServletResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static moim_today.global.constant.MoimConstant.VIEWED_MOIM_COOKIE_NAME;
import static moim_today.global.constant.NumberConstant.ONE_DAYS_IN_SECONDS;
import static moim_today.global.constant.exception.MoimExceptionConstant.VIEWED_MOIM_NOT_FOUND_ERROR;
import static moim_today.util.SerializingObjectMapper.serializingObjectMapper;
import static moim_today.util.TestConstant.LOCAL_DATE_TIME_FORMAT;
import static moim_today.util.TestConstant.MOIM_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ViewedMoimsCookieTest {

    private final ObjectMapper objectMapper = serializingObjectMapper();
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_FORMAT.value());


    @DisplayName("URL 인코딩된 쿠키가 없으면 default 값을 생성하여 반환한다.")
    @Test
    void createDefaultCookie() {
        //given
        String nullOfViewedMoimsCookieByUrlEncoded = null;
        List<ViewedMoim> defaultViewedMoims = new ArrayList<>();

        //when
        ViewedMoimsCookie viewedMoimsCookie =
                ViewedMoimsCookie.createViewedMoimsCookieOrDefault(
                        nullOfViewedMoimsCookieByUrlEncoded,
                        defaultViewedMoims,
                        objectMapper
                );

        //then
        List<ViewedMoim> viewedMoims = viewedMoimsCookie.viewedMoims();
        assertThat(viewedMoims.size()).isEqualTo(0);
    }

    @DisplayName("URL 인코딩된 쿠키가 있으면 값을 파싱하여 생성한뒤 반환한다.")
    @Test
    void createByViewedMoimsCookieByUrlEncodedCookie() {
        //given1
        String nullOfViewedMoimsCookieByUrlEncoded = null;
        List<ViewedMoim> defaultViewedMoims = new ArrayList<>();
        String format = LocalDateTime.now().plusDays(1).format(DATETIME_FORMATTER);
        LocalDateTime expiredTime = LocalDateTime.parse(format, DATETIME_FORMATTER);

        ViewedMoimsCookie viewedMoimsCookieParameter =
                ViewedMoimsCookie.createViewedMoimsCookieOrDefault(
                        nullOfViewedMoimsCookieByUrlEncoded,
                        defaultViewedMoims,
                        objectMapper
                );

        //given2
        MockHttpServletResponse response = new MockHttpServletResponse();

        long moimId = MOIM_ID.longValue();
        ViewedMoim viewedMoim = ViewedMoim.of(moimId, expiredTime);
        viewedMoimsCookieParameter.viewedMoims().add(viewedMoim);
        viewedMoimsCookieParameter.addViewedMoimsCookieInCookie(response, objectMapper);
        String viewedMoimsCookieByUrlEncodedParameter = requireNonNull(response.getCookie(VIEWED_MOIM_COOKIE_NAME)).getValue();

        //when
        ViewedMoimsCookie viewedMoimsCookie =
                ViewedMoimsCookie.createViewedMoimsCookieOrDefault(
                        viewedMoimsCookieByUrlEncodedParameter,
                        defaultViewedMoims,
                        objectMapper
                );

        //then
        List<ViewedMoim> viewedMoims = viewedMoimsCookie.viewedMoims();
        assertThat(viewedMoims.size()).isEqualTo(1);
        assertThat(viewedMoims.get(0).moimId()).isEqualTo(moimId);
        assertThat(viewedMoims.get(0).expiredTime()).isEqualTo(expiredTime);
    }

    @DisplayName("모임Id로 만료 기간이 하루 뒤인 ViewedMoim를 생성하고 쿠키 리스트에 추가한다.")
    @Test
    void createAndAddViewedMoimTest() {
        //given1
        LocalDateTime testStartTime = LocalDateTime.now();
        String nullOfViewedMoimsCookieByUrlEncoded = null;
        List<ViewedMoim> defaultViewedMoims = new ArrayList<>();

        ViewedMoimsCookie viewedMoimsCookie =
                ViewedMoimsCookie.createViewedMoimsCookieOrDefault(
                        nullOfViewedMoimsCookieByUrlEncoded,
                        defaultViewedMoims,
                        objectMapper
                );

        //given2
        long moimId = MOIM_ID.longValue();

        //when
        viewedMoimsCookie.createAndAddViewedMoim(moimId);

        //then
        LocalDateTime testEndTime = LocalDateTime.now();
        List<ViewedMoim> viewedMoims = viewedMoimsCookie.viewedMoims();
        assertThat(viewedMoims.size()).isEqualTo(1);
        assertThat(viewedMoims.get(0).moimId()).isEqualTo(moimId);
        assertThat(viewedMoims.get(0).expiredTime()).isAfter(testStartTime.plusDays(1).minusMinutes(1));
        assertThat(viewedMoims.get(0).expiredTime()).isBefore(testEndTime.plusDays(1).plusMinutes(1));
    }

    @DisplayName("모임Id로 ViewedMoim를 쿠키 리스트에서 제거한다.")
    @Test
    void removeViewedMoimTest() {
        //given1
        String nullOfViewedMoimsCookieByUrlEncoded = null;
        List<ViewedMoim> defaultViewedMoims = new ArrayList<>();

        ViewedMoimsCookie viewedMoimsCookie =
                ViewedMoimsCookie.createViewedMoimsCookieOrDefault(
                        nullOfViewedMoimsCookieByUrlEncoded,
                        defaultViewedMoims,
                        objectMapper
                );

        //given2
        long moimId = MOIM_ID.longValue();
        ViewedMoim viewedMoim = ViewedMoim.of(moimId, LocalDateTime.now());
        viewedMoimsCookie.viewedMoims().add(viewedMoim);

        //when
        viewedMoimsCookie.removeViewedMoim(moimId);

        //then
        List<ViewedMoim> viewedMoims = viewedMoimsCookie.viewedMoims();
        assertThat(viewedMoims.size()).isEqualTo(0);
    }

    @DisplayName("모임Id로 ViewedMoim를 제거 할때, 쿠키 리스트에 존재하지 않으면 예외가 발생한다.")
    @Test
    void removeViewedMoimThrowsExceptionTest() {
        //given1
        String nullOfViewedMoimsCookieByUrlEncoded = null;
        List<ViewedMoim> defaultViewedMoims = new ArrayList<>();

        ViewedMoimsCookie viewedMoimsCookie =
                ViewedMoimsCookie.createViewedMoimsCookieOrDefault(
                        nullOfViewedMoimsCookieByUrlEncoded,
                        defaultViewedMoims,
                        objectMapper
                );

        //given2
        long notExistMoimId = MOIM_ID.longValue();

        //expected
        assertThatThrownBy(() -> viewedMoimsCookie.removeViewedMoim(notExistMoimId))
                .isInstanceOf(InternalServerException.class)
                .hasMessage(VIEWED_MOIM_NOT_FOUND_ERROR.message());
    }

    @DisplayName("쿠키 리스트에 모임 Id에 해당하는 ViewedMoim가 존재하는지 확인한다.")
    @Test
    void existsInViewedMoimsTest() {
        //given1
        String nullOfViewedMoimsCookieByUrlEncoded = null;
        List<ViewedMoim> defaultViewedMoims = new ArrayList<>();

        ViewedMoimsCookie viewedMoimsCookie =
                ViewedMoimsCookie.createViewedMoimsCookieOrDefault(
                        nullOfViewedMoimsCookieByUrlEncoded,
                        defaultViewedMoims,
                        objectMapper
                );

        //given2
        long existMoimId = MOIM_ID.longValue();
        long notExistMoimId = MOIM_ID.longValue() + 1;
        ViewedMoim viewedMoim = ViewedMoim.of(existMoimId, LocalDateTime.now());
        viewedMoimsCookie.viewedMoims().add(viewedMoim);

        //when
        boolean existsInViewedMoims = viewedMoimsCookie.existsInViewedMoims(existMoimId);
        boolean notExistsInViewedMoims = viewedMoimsCookie.existsInViewedMoims(notExistMoimId);

        //then
        assertThat(existsInViewedMoims).isTrue();
        assertThat(notExistsInViewedMoims).isFalse();
    }

    @DisplayName("쿠키 리스트에 모임 Id에 해당하는 ViewedMoim가 만료되었는지 확인한다.")
    @Test
    void isExpiredTest() {
        //given1
        String nullOfViewedMoimsCookieByUrlEncoded = null;
        List<ViewedMoim> defaultViewedMoims = new ArrayList<>();

        ViewedMoimsCookie viewedMoimsCookie =
                ViewedMoimsCookie.createViewedMoimsCookieOrDefault(
                        nullOfViewedMoimsCookieByUrlEncoded,
                        defaultViewedMoims,
                        objectMapper
                );

        //given2
        long expiredMoimId = MOIM_ID.longValue();
        long notExpiredMoimId = MOIM_ID.longValue() + 1;

        ViewedMoim expiredViewedMoim = ViewedMoim.of(expiredMoimId, LocalDateTime.now().minusDays(1));
        ViewedMoim notExpiredViewedMoim = ViewedMoim.of(notExpiredMoimId, LocalDateTime.now().plusDays(1));
        viewedMoimsCookie.viewedMoims().add(expiredViewedMoim);
        viewedMoimsCookie.viewedMoims().add(notExpiredViewedMoim);

        //when
        boolean expectedExpiredTrue = viewedMoimsCookie.isExpired(expiredMoimId);
        boolean expectedExpiredFalse = viewedMoimsCookie.isExpired(notExpiredMoimId);

        //then
        assertThat(expectedExpiredTrue).isTrue();
        assertThat(expectedExpiredFalse).isFalse();
    }

    @DisplayName("쿠키 리스트를 응답에 설정한다.")
    @Test
    void addViewedMoimsCookieInCookieTest() {
        //given1
        String nullOfViewedMoimsCookieByUrlEncoded = null;
        List<ViewedMoim> defaultViewedMoims = new ArrayList<>();

        ViewedMoimsCookie viewedMoimsCookie =
                ViewedMoimsCookie.createViewedMoimsCookieOrDefault(
                        nullOfViewedMoimsCookieByUrlEncoded,
                        defaultViewedMoims,
                        objectMapper
                );

        //given2
        MockHttpServletResponse response = new MockHttpServletResponse();

        //when
        viewedMoimsCookie.addViewedMoimsCookieInCookie(response, objectMapper);

        //then
        Cookie cookie = response.getCookie(VIEWED_MOIM_COOKIE_NAME);
        assertThat(cookie).isNotNull();
        assertThat(cookie.getValue()).isNotNull();
        assertThat(cookie.getMaxAge()).isEqualTo(ONE_DAYS_IN_SECONDS.value());
        assertThat(cookie.isHttpOnly()).isTrue();
        assertThat(cookie.getSecure()).isTrue();
    }
}