package moim_today.application.moim.moim_notice;

import moim_today.dto.moim.moim_notice.MoimNoticeCreateRequest;

public interface MoimNoticeService {

    void createMoimNotice(final long memberId, final MoimNoticeCreateRequest moimNoticeCreateRequest);
}
