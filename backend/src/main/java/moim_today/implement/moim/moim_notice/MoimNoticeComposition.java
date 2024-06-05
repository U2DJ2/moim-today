package moim_today.implement.moim.moim_notice;

import moim_today.dto.moim.moim_notice.MoimNoticeCreateRequest;
import moim_today.dto.moim.moim_notice.MoimNoticeSimpleResponse;
import moim_today.dto.moim.moim_notice.MoimNoticeUpdateRequest;
import moim_today.global.annotation.Implement;
import moim_today.persistence.entity.moim.moim_notice.MoimNoticeJpaEntity;

import java.util.List;

@Implement
public class MoimNoticeComposition {

    private final MoimNoticeAppender moimNoticeAppender;
    private final MoimNoticeFinder moimNoticeFinder;
    private final MoimNoticeUpdater moimNoticeUpdater;
    private final MoimNoticeRemover moimNoticeRemover;

    public MoimNoticeComposition(final MoimNoticeAppender moimNoticeAppender,
                                 final MoimNoticeFinder moimNoticeFinder,
                                 final MoimNoticeUpdater moimNoticeUpdater,
                                 final MoimNoticeRemover moimNoticeRemover) {
        this.moimNoticeAppender = moimNoticeAppender;
        this.moimNoticeFinder = moimNoticeFinder;
        this.moimNoticeUpdater = moimNoticeUpdater;
        this.moimNoticeRemover = moimNoticeRemover;
    }

    public void createMoimNotice(final long memberId, final long moimId, final MoimNoticeCreateRequest moimNoticeCreateRequest) {
        moimNoticeAppender.createMoimNotice(memberId, moimId, moimNoticeCreateRequest);
    }

    public List<MoimNoticeSimpleResponse> findAllMoimNotice(final long moimId) {
        return moimNoticeFinder.findAllMoimNotice(moimId);
    }

    public MoimNoticeJpaEntity getById(final long moimNoticeId) {
        return moimNoticeFinder.getById(moimNoticeId);
    }

    public void updateMoimNotice(final long memberId,
                                 final long moimId,
                                 final MoimNoticeUpdateRequest moimNoticeUpdateRequest) {
        moimNoticeUpdater.updateMoimNotice(memberId, moimId, moimNoticeUpdateRequest);
    }

    public void deleteMoimNotice(final long memberId, final long moimId, final long moimNoticeId) {
        moimNoticeRemover.deleteMoimNotice(memberId, moimId, moimNoticeId);
    }
}
