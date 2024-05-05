package moim_today.application.moim.moim_notice;

import moim_today.dto.moim.moim_notice.MoimNoticeCreateRequest;
import moim_today.dto.moim.moim_notice.SimpleMoimNoticeResponse;
import moim_today.implement.moim.moim_notice.MoimNoticeAppender;
import moim_today.implement.moim.moim_notice.MoimNoticeFinder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoimNoticeServiceImpl implements MoimNoticeService{

    private final MoimNoticeAppender moimNoticeAppender;
    private final MoimNoticeFinder moimNoticeFinder;

    public MoimNoticeServiceImpl(final MoimNoticeAppender moimNoticeAppender,
                                 final MoimNoticeFinder moimNoticeFinder) {
        this.moimNoticeAppender = moimNoticeAppender;
        this.moimNoticeFinder = moimNoticeFinder;
    }

    @Override
    public void createMoimNotice(final long memberId, final MoimNoticeCreateRequest moimNoticeCreateRequest) {
        moimNoticeAppender.createMoimNotice(memberId, moimNoticeCreateRequest);
    }

    @Override
    public List<SimpleMoimNoticeResponse> findAllMoimNotice(final long memberId, final long moimId) {
        return moimNoticeFinder.findAllMoimNotice(memberId, moimId);
    }
}
