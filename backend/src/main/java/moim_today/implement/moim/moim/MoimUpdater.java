package moim_today.implement.moim.moim;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import moim_today.domain.moim.ViewedMoimsCookie;
import moim_today.dto.moim.moim.MoimUpdateRequest;
import moim_today.global.annotation.Implement;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import moim_today.persistence.repository.moim.moim.MoimRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Implement
public class MoimUpdater {

    private final MoimRepository moimRepository;
    private final ObjectMapper objectMapper;
    private final MoimFinder moimFinder;

    public MoimUpdater(final MoimRepository moimRepository,
                       final ObjectMapper objectMapper,
                       final MoimFinder moimFinder) {
        this.moimRepository = moimRepository;
        this.objectMapper = objectMapper;
        this.moimFinder = moimFinder;
    }

    @Transactional
    public void updateMoim(final long memberId, final MoimUpdateRequest moimUpdateRequest) {
        MoimJpaEntity moimJpaEntity = moimRepository.getById(moimUpdateRequest.moimId());
        moimJpaEntity.validateHostMember(memberId);
        moimJpaEntity.updateMoim(moimUpdateRequest);
    }

    @Transactional
    public void updateMoimViews(final long moimId, final String viewedMoimsCookieByUrlEncoded, final HttpServletResponse response) {
        MoimJpaEntity moimJpaEntity = moimFinder.getById(moimId);

        ViewedMoimsCookie viewedMoimsCookie = ViewedMoimsCookie.createViewedMoimsCookieOrDefault(
                viewedMoimsCookieByUrlEncoded, new ArrayList<>(), objectMapper);

        if (!viewedMoimsCookie.existsInViewedMoims(moimId)) {
            moimJpaEntity.updateMoimViews();
            viewedMoimsCookie.createAndAddViewedMoim(moimId);
            viewedMoimsCookie.addViewedMoimsCookieInCookie(response, objectMapper);
        } else if (viewedMoimsCookie.isExpired(moimId)) {
            moimJpaEntity.updateMoimViews();
            viewedMoimsCookie.removeViewedMoim(moimId);
            viewedMoimsCookie.createAndAddViewedMoim(moimId);
            viewedMoimsCookie.addViewedMoimsCookieInCookie(response, objectMapper);
        }
    }
}
