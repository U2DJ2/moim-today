package moim_today.implement.moim.moim_notice;

import moim_today.dto.moim.moim_notice.MoimNoticeSimpleResponse;
import moim_today.global.annotation.Implement;
import moim_today.persistence.entity.moim.moim_notice.MoimNoticeJpaEntity;
import moim_today.persistence.repository.moim.moim_notice.MoimNoticeRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Implement
public class MoimNoticeFinder {

    private final MoimNoticeRepository moimNoticeRepository;

    public MoimNoticeFinder(final MoimNoticeRepository moimNoticeRepository) {
        this.moimNoticeRepository = moimNoticeRepository;
    }

    @Transactional(readOnly = true)
    public List<MoimNoticeSimpleResponse> findAllMoimNotice(final long moimId, final long lastMoimNoticeId) {
        return moimNoticeRepository.findAllMoimNotice(moimId, lastMoimNoticeId);
    }

    @Cacheable(value = "moimNotice", key = "#moimNoticeId")
    @Transactional(readOnly = true)
    public MoimNoticeJpaEntity getById(final long moimNoticeId) {
        return moimNoticeRepository.getById(moimNoticeId);
    }
}
