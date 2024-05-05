package moim_today.application.moim.moim_notice;

import moim_today.dto.moim.moim_notice.MoimNoticeCreateRequest;
import moim_today.dto.moim.moim_notice.SimpleMoimNoticeResponse;

import java.util.List;

public interface MoimNoticeService {

    void createMoimNotice(final long memberId, final MoimNoticeCreateRequest moimNoticeCreateRequest);

    List<SimpleMoimNoticeResponse> findAllMoimNotice(final long memberId, final long moimId);
}
