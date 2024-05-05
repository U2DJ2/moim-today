package moim_today.implement.moim.moim_notice;

import moim_today.dto.moim.moim_notice.MoimNoticeCreateRequest;
import moim_today.global.annotation.Implement;
import moim_today.global.error.ForbiddenException;
import moim_today.implement.moim.moim.MoimFinder;
import moim_today.persistence.entity.moim.moim_notice.MoimNoticeJpaEntity;
import moim_today.persistence.repository.moim.moim_notice.MoimNoticeRepository;
import org.springframework.transaction.annotation.Transactional;

import static moim_today.global.constant.exception.MoimExceptionConstant.MOIM_FORBIDDEN;

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
    public void createMoimNotice(final long memberId, final MoimNoticeCreateRequest moimNoticeCreateRequest) {
        long ownerId = moimFinder.getMemberIdById(moimNoticeCreateRequest.moimId());
        if (ownerId != memberId) {
            throw new ForbiddenException(MOIM_FORBIDDEN.message());
        }
        MoimNoticeJpaEntity moimNoticeJpaEntity = moimNoticeCreateRequest.toEntity();
        moimNoticeRepository.save(moimNoticeJpaEntity);
    }
}
