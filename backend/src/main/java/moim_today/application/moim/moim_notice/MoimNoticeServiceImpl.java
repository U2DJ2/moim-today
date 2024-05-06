package moim_today.application.moim.moim_notice;

import moim_today.dto.moim.moim_notice.*;
import moim_today.implement.moim.joined_moim.JoinedMoimFinder;
import moim_today.implement.moim.moim.MoimFinder;
import moim_today.implement.moim.moim_notice.MoimNoticeAppender;
import moim_today.implement.moim.moim_notice.MoimNoticeFinder;
import moim_today.implement.moim.moim_notice.MoimNoticeRemover;
import moim_today.implement.moim.moim_notice.MoimNoticeUpdater;
import moim_today.persistence.entity.moim.moim_notice.MoimNoticeJpaEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoimNoticeServiceImpl implements MoimNoticeService{

    private final MoimNoticeAppender moimNoticeAppender;
    private final MoimNoticeFinder moimNoticeFinder;
    private final MoimNoticeUpdater moimNoticeUpdater;
    private final MoimNoticeRemover moimNoticeRemover;
    private final JoinedMoimFinder joinedMoimFinder;
    private final MoimFinder moimFinder;


    public MoimNoticeServiceImpl(final MoimNoticeAppender moimNoticeAppender,
                                 final MoimNoticeFinder moimNoticeFinder,
                                 final MoimNoticeUpdater moimNoticeUpdater,
                                 final MoimNoticeRemover moimNoticeRemover,
                                 final JoinedMoimFinder joinedMoimFinder,
                                 final MoimFinder moimFinder) {
        this.moimNoticeAppender = moimNoticeAppender;
        this.moimNoticeFinder = moimNoticeFinder;
        this.moimNoticeUpdater = moimNoticeUpdater;
        this.moimNoticeRemover = moimNoticeRemover;
        this.joinedMoimFinder = joinedMoimFinder;
        this.moimFinder = moimFinder;
    }

    @Override
    public void createMoimNotice(final long memberId, final MoimNoticeCreateRequest moimNoticeCreateRequest) {
        moimNoticeAppender.createMoimNotice(memberId, moimNoticeCreateRequest);
    }

    @Override
    public List<MoimNoticeSimpleResponse> findAllMoimNotice(final long memberId, final long moimId) {
        joinedMoimFinder.validateJoinedMember(memberId, moimId);
        return moimNoticeFinder.findAllMoimNotice(moimId);
    }

    @Override
    public MoimNoticeDetailResponse getMoimNoticeDetail(final long memberId, final long moimNoticeId) {
        MoimNoticeJpaEntity moimNoticeJpaEntity = moimNoticeFinder.getById(moimNoticeId);
        joinedMoimFinder.validateJoinedMember(memberId, moimNoticeJpaEntity.getMoimId());
        return MoimNoticeDetailResponse.from(moimNoticeJpaEntity);
    }

    @Override
    public void updateMoimNotice(final long memberId, final MoimNoticeUpdateRequest moimNoticeUpdateRequest) {
        MoimNoticeJpaEntity moimNoticeJpaEntity = moimNoticeFinder.getById(moimNoticeUpdateRequest.moimNoticeId());
        moimNoticeUpdater.updateMoimNotice(memberId, moimNoticeJpaEntity.getMoimId(), moimNoticeUpdateRequest);
    }

    @Override
    public void deleteMoimNotice(final long memberId, final MoimNoticeDeleteRequest moimNoticeDeleteRequest) {
        MoimNoticeJpaEntity moimNoticeJpaEntity = moimNoticeFinder.getById(moimNoticeDeleteRequest.moimNoticeId());
        moimNoticeRemover.deleteMoimNotice(memberId, moimNoticeJpaEntity.getMoimId(), moimNoticeDeleteRequest.moimNoticeId());
    }
}
