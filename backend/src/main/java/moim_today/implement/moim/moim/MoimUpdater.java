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

    public MoimUpdater(final MoimRepository moimRepository, final ObjectMapper objectMapper) {
        this.moimRepository = moimRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public void updateMoim(final long memberId, final MoimUpdateRequest moimUpdateRequest) {
        MoimJpaEntity moimJpaEntity = moimRepository.getById(moimUpdateRequest.moimId());
        moimJpaEntity.validateHostMember(memberId);
        moimJpaEntity.updateMoim(moimUpdateRequest);
    }

    @Transactional
    public void updateMoimViews(final MoimJpaEntity moimJpaEntity, final String viewedMoimsCookieByUrlEncoded, final HttpServletResponse response) {
        ViewedMoimsCookie viewedMoimsCookie = ViewedMoimsCookie.getViewedMoimsCookieOrDefault(
                viewedMoimsCookieByUrlEncoded, new ArrayList<>(), objectMapper);

        if (!viewedMoimsCookie.existsInViewedMoims(moimJpaEntity.getId())) {
            moimJpaEntity.updateMoimViews();
            viewedMoimsCookie.createAndAddViewedMoim(moimJpaEntity.getId());
            viewedMoimsCookie.addViewedMoimsCookieInCookie(response, objectMapper);
            return;
        }

        if (viewedMoimsCookie.isExpired(moimJpaEntity.getId())) {
            viewedMoimsCookie.removeViewedMoim(moimJpaEntity.getId());
            viewedMoimsCookie.createAndAddViewedMoim(moimJpaEntity.getId());
            viewedMoimsCookie.addViewedMoimsCookieInCookie(response, objectMapper);
        }
    }
}
