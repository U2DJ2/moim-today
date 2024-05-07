package moim_today.implement.moim.moim_notice;

import moim_today.global.annotation.Implement;
import moim_today.implement.moim.moim.MoimFinder;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import moim_today.persistence.entity.moim.moim_notice.MoimNoticeJpaEntity;
import moim_today.persistence.repository.moim.moim_notice.MoimNoticeRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.transaction.annotation.Transactional;

@Implement
public class MoimNoticeRemover {

    private final MoimNoticeRepository moimNoticeRepository;
    private final MoimFinder moimFinder;

    public MoimNoticeRemover(final MoimNoticeRepository moimNoticeRepository,
                             final MoimFinder moimFinder) {
        this.moimNoticeRepository = moimNoticeRepository;
        this.moimFinder = moimFinder;
    }

    @Caching(evict = {
            @CacheEvict(value = "moimNotices", key = "#moimId"),
            @CacheEvict(value = "moimNotice", key = "#moimNoticeId")
    })
    @Transactional
    public void deleteMoimNotice(final long memberId, final long moimId, final long moimNoticeId) {
        MoimNoticeJpaEntity moimNoticeJpaEntity = moimNoticeRepository.getById(moimNoticeId);
        MoimJpaEntity moimJpaEntity = moimFinder.getById(moimNoticeJpaEntity.getMoimId());
        moimJpaEntity.validateMember(memberId);
        moimNoticeRepository.deleteById(moimNoticeId);
    }
}
