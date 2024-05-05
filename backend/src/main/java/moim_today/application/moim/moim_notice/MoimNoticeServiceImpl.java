package moim_today.application.moim.moim_notice;

import moim_today.dto.moim.moim_notice.*;
import moim_today.implement.moim.moim_notice.MoimNoticeAppender;
import moim_today.implement.moim.moim_notice.MoimNoticeFinder;
import moim_today.implement.moim.moim_notice.MoimNoticeRemover;
import moim_today.implement.moim.moim_notice.MoimNoticeUpdater;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoimNoticeServiceImpl implements MoimNoticeService{

    private final MoimNoticeAppender moimNoticeAppender;
    private final MoimNoticeFinder moimNoticeFinder;
    private final MoimNoticeUpdater moimNoticeUpdater;
    private final MoimNoticeRemover moimNoticeRemover;


    public MoimNoticeServiceImpl(final MoimNoticeAppender moimNoticeAppender,
                                 final MoimNoticeFinder moimNoticeFinder,
                                 final MoimNoticeUpdater moimNoticeUpdater,
                                 final MoimNoticeRemover moimNoticeRemover) {
        this.moimNoticeAppender = moimNoticeAppender;
        this.moimNoticeFinder = moimNoticeFinder;
        this.moimNoticeUpdater = moimNoticeUpdater;
        this.moimNoticeRemover = moimNoticeRemover;
    }

    @Override
    public void createMoimNotice(final long memberId, final MoimNoticeCreateRequest moimNoticeCreateRequest) {
        moimNoticeAppender.createMoimNotice(memberId, moimNoticeCreateRequest);
    }

    @Override
    public List<MoimNoticeSimpleResponse> findAllMoimNotice(final long memberId, final long moimId) {
        return moimNoticeFinder.findAllMoimNotice(memberId, moimId);
    }

    @Override
    public MoimNoticeDetailResponse getMoimNoticeDetail(final long memberId, final long moimNoticeId) {
        return MoimNoticeDetailResponse.from(moimNoticeFinder.getById(memberId, moimNoticeId));
    }

    @Override
    public void updateMoimNotice(final long memberId, final MoimNoticeUpdateRequest moimNoticeUpdateRequest) {
        moimNoticeUpdater.updateMoimNotice(memberId, moimNoticeUpdateRequest);
    }

    @Override
    public void deleteMoimNotice(final long memberId, final MoimNoticeDeleteRequest moimNoticeDeleteRequest) {
        moimNoticeRemover.deleteMoimNotice(memberId, moimNoticeDeleteRequest);
    }
}
