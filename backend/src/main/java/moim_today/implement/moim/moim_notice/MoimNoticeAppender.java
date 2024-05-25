package moim_today.implement.moim.moim_notice;

import moim_today.dto.moim.moim_notice.MoimNoticeCreateRequest;
import moim_today.global.annotation.Implement;
import moim_today.implement.moim.moim.MoimFinder;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import moim_today.persistence.entity.moim.moim_notice.MoimNoticeJpaEntity;
import moim_today.persistence.repository.moim.moim_notice.MoimNoticeRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.transaction.annotation.Transactional;

@Implement
public class MoimNoticeAppender {

    private final MoimNoticeRepository moimNoticeRepository;
    private final MoimFinder moimFinder;

    public MoimNoticeAppender(final MoimNoticeRepository moimNoticeRepository,
                              final MoimFinder moimFinder) {
        this.moimNoticeRepository = moimNoticeRepository;
        this.moimFinder = moimFinder;
    }

    @Transactional
    public void createMoimNotice(final long memberId, final long moimId, final MoimNoticeCreateRequest moimNoticeCreateRequest) {
        MoimJpaEntity moimJpaEntity = moimFinder.getById(moimId);
        moimJpaEntity.validateHostMember(memberId);
        MoimNoticeJpaEntity moimNoticeJpaEntity = moimNoticeCreateRequest.toEntity();
        moimNoticeRepository.save(moimNoticeJpaEntity);
    }
}
