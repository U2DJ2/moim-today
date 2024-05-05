package moim_today.implement.moim.moim_notice;

import moim_today.dto.moim.moim_notice.MoimNoticeSimpleResponse;
import moim_today.global.annotation.Implement;
import moim_today.implement.moim.joined_moim.JoinedMoimFinder;
import moim_today.persistence.entity.moim.moim_notice.MoimNoticeJpaEntity;
import moim_today.persistence.repository.moim.moim_notice.MoimNoticeRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Implement
public class MoimNoticeFinder {

    private final MoimNoticeRepository moimNoticeRepository;
    private final JoinedMoimFinder joinedMoimFinder;

    public MoimNoticeFinder(final MoimNoticeRepository moimNoticeRepository,
                            final JoinedMoimFinder joinedMoimFinder) {
        this.moimNoticeRepository = moimNoticeRepository;
        this.joinedMoimFinder = joinedMoimFinder;
    }

    @Transactional(readOnly = true)
    public List<MoimNoticeSimpleResponse> findAllMoimNotice(final long memberId, final long moimId) {
        joinedMoimFinder.validateJoinedMember(memberId, moimId);
        return moimNoticeRepository.findAllMoimNotice(moimId);
    }

    @Transactional(readOnly = true)
    public MoimNoticeJpaEntity getById(final long memberId, final long moimNoticeId) {
        long moimId = moimNoticeRepository.getMoimIdById(moimNoticeId);
        joinedMoimFinder.validateJoinedMember(memberId, moimId);
        return moimNoticeRepository.getById(moimNoticeId);
    }
}
