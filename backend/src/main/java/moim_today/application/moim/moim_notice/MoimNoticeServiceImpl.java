package moim_today.application.moim.moim_notice;

import moim_today.dto.moim.moim_notice.MoimNoticeCreateRequest;
import moim_today.implement.moim.moim_notice.MoimNoticeAppender;
import org.springframework.stereotype.Service;

@Service
public class MoimNoticeServiceImpl implements MoimNoticeService{

    private final MoimNoticeAppender moimNoticeAppender;

    public MoimNoticeServiceImpl(final MoimNoticeAppender moimNoticeAppender) {
        this.moimNoticeAppender = moimNoticeAppender;
    }

    @Override
    public void createMoimNotice(final long memberId, final MoimNoticeCreateRequest moimNoticeCreateRequest) {
        moimNoticeAppender.createMoimNotice(memberId, moimNoticeCreateRequest);
    }
}
