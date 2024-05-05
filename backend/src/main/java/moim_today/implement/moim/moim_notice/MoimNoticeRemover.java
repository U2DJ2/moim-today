package moim_today.implement.moim.moim_notice;

import moim_today.dto.moim.moim_notice.MoimNoticeDeleteRequest;
import moim_today.global.annotation.Implement;
import moim_today.implement.moim.moim.MoimFinder;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import moim_today.persistence.entity.moim.moim_notice.MoimNoticeJpaEntity;
import moim_today.persistence.repository.moim.moim_notice.MoimNoticeRepository;
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

    @Transactional
    public void deleteMoimNotice(final long memberId, final MoimNoticeDeleteRequest moimNoticeDeleteRequest) {
        MoimNoticeJpaEntity moimNoticeJpaEntity = moimNoticeRepository.getById(moimNoticeDeleteRequest.moimNoticeId());
        MoimJpaEntity moimJpaEntity = moimFinder.getById(moimNoticeJpaEntity.getMoimId());
        moimJpaEntity.validateMember(memberId);
        moimNoticeRepository.deleteById(moimNoticeDeleteRequest.moimNoticeId());
    }
}
