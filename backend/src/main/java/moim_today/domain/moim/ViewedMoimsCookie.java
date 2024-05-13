package moim_today.domain.moim;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import moim_today.global.error.InternalServerException;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static moim_today.global.constant.MoimConstant.VIEWED_MOIM_COOKIE_NAME;
import static moim_today.global.constant.NumberConstant.ONE_DAY;
import static moim_today.global.constant.NumberConstant.ONE_DAYS_IN_SECONDS;
import static moim_today.global.constant.exception.MoimExceptionConstant.VIEWED_MOIM_JSON_PROCESSING_ERROR;
import static moim_today.global.constant.exception.MoimExceptionConstant.VIEWED_MOIM_NOT_FOUND_ERROR;

public record ViewedMoimsCookie(
        List<ViewedMoim> viewedMoims
) {

    public static ViewedMoimsCookie createViewedMoimsCookieOrDefault(final String viewedMoimsCookieByUrlEncoded,
                                                                     final List<ViewedMoim> defaultViewedMoims,
                                                                     final ObjectMapper objectMapper) {
        if (viewedMoimsCookieByUrlEncoded != null) {
            return parseViewedMoimsCookie(viewedMoimsCookieByUrlEncoded, objectMapper);
        } else {
            return new ViewedMoimsCookie(defaultViewedMoims);
        }
    }

    public void createAndAddViewedMoim(final long moimId) {
        ViewedMoim viewedMoim = ViewedMoim.of(moimId, LocalDateTime.now().plusDays(ONE_DAY.value()));
        viewedMoims.add(viewedMoim);
    }

    public void removeViewedMoim(final long moimId) {
        ViewedMoim viewedMoim = getViewedMoim(moimId);
        viewedMoims.remove(viewedMoim);
    }

    public boolean existsInViewedMoims(final long moimId) {
        return viewedMoims.stream()
                .map(ViewedMoim::moimId)
                .toList()
                .contains(moimId);
    }

    public boolean isExpired(final long moimId) {
        ViewedMoim viewedMoim = getViewedMoim(moimId);
        return viewedMoim.isExpired();
    }

    public void addViewedMoimsCookieInCookie(final HttpServletResponse response, final ObjectMapper objectMapper) {
        String viewedMoimsCookieJson = this.toJson(objectMapper);
        Cookie cookie = new Cookie(VIEWED_MOIM_COOKIE_NAME, URLEncoder.encode(viewedMoimsCookieJson, UTF_8));
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(ONE_DAYS_IN_SECONDS.value());
        response.addCookie(cookie);
    }

    private static ViewedMoimsCookie parseViewedMoimsCookie(final String viewedMoimsCookieByUrlEncoded, final ObjectMapper objectMapper) {
        String viewedMoimsCookieStringValue = URLDecoder.decode(viewedMoimsCookieByUrlEncoded, UTF_8);

        try {
            List<ViewedMoim> viewedMoims = objectMapper.readValue(viewedMoimsCookieStringValue, new TypeReference<>() {});
            return new ViewedMoimsCookie(viewedMoims);
        } catch (JsonProcessingException e) {
            throw new InternalServerException(VIEWED_MOIM_JSON_PROCESSING_ERROR.message());
        }
    }

    private String toJson(final ObjectMapper objectMapper) {
        try {
            return objectMapper.writeValueAsString(viewedMoims);
        } catch (JsonProcessingException e) {
            throw new InternalServerException(VIEWED_MOIM_JSON_PROCESSING_ERROR.message());
        }
    }

    private ViewedMoim getViewedMoim(final long moimId) {
        return viewedMoims.stream()
                .filter(viewedMoim -> viewedMoim.moimId() == moimId)
                .findFirst()
                .orElseThrow(() -> new InternalServerException(VIEWED_MOIM_NOT_FOUND_ERROR.message()));
    }
}
