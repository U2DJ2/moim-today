package moim_today.implement.moim.moim_notice;

import moim_today.dto.moim.moim_notice.SimpleMoimNoticeResponse;
import moim_today.global.annotation.Implement;
import moim_today.global.error.ForbiddenException;
import moim_today.implement.moim.joined_moim.JoinedMoimFinder;
import moim_today.persistence.repository.moim.moim_notice.MoimNoticeRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static moim_today.global.constant.exception.MoimExceptionConstant.MOIM_FORBIDDEN;

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
    public List<SimpleMoimNoticeResponse> findAllMoimNotice(final long memberId, final long moimId) {
        List<Long> joinedMemberIds = joinedMoimFinder.findAllJoinedMemberId(moimId);
        if (!joinedMemberIds.contains(memberId)) {
            throw new ForbiddenException(MOIM_FORBIDDEN.message());
        }
        return moimNoticeRepository.findAllMoimNotice(moimId);
    }
}
