package moim_today.implement.moim.moim_notice;

import moim_today.dto.moim.moim_notice.MoimNoticeUpdateRequest;
import moim_today.global.annotation.Implement;
import moim_today.implement.moim.moim.MoimFinder;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import moim_today.persistence.entity.moim.moim_notice.MoimNoticeJpaEntity;
import moim_today.persistence.repository.moim.moim_notice.MoimNoticeRepository;
import org.springframework.transaction.annotation.Transactional;

@Implement
public class MoimNoticeUpdater {

    private final MoimNoticeRepository moimNoticeRepository;
    private final MoimFinder moimFinder;

    public MoimNoticeUpdater(final MoimNoticeRepository moimNoticeRepository,
                             final MoimFinder moimFinder) {
        this.moimNoticeRepository = moimNoticeRepository;
        this.moimFinder = moimFinder;
    }

    @Transactional
    public void updateMoimNotice(final long memberId, final MoimNoticeUpdateRequest moimNoticeUpdateRequest) {
        MoimNoticeJpaEntity moimNoticeJpaEntity = moimNoticeRepository.getById(moimNoticeUpdateRequest.moimNoticeId());
        MoimJpaEntity moimJpaEntity = moimFinder.getById(moimNoticeJpaEntity.getMoimId());
        moimJpaEntity.validateMember(memberId);
        moimNoticeJpaEntity.updateMoimNotice(moimNoticeUpdateRequest);
    }
}
