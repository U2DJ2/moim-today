package moim_today.application.moim.moim_notice;

import moim_today.dto.moim.moim_notice.MoimNoticeCreateRequest;
import moim_today.dto.moim.moim_notice.MoimNoticeDetailResponse;
import moim_today.dto.moim.moim_notice.MoimNoticeSimpleResponse;
import moim_today.dto.moim.moim_notice.MoimNoticeUpdateRequest;

import java.util.List;

public interface MoimNoticeService {

    void createMoimNotice(final long memberId, final MoimNoticeCreateRequest moimNoticeCreateRequest);

    List<MoimNoticeSimpleResponse> findAllMoimNotice(final long memberId, final long moimId);

    MoimNoticeDetailResponse getMoimNoticeDetail(final long memberId, final long moimNoticeId);

    void updateMoimNotice(final long memberId, final MoimNoticeUpdateRequest moimNoticeUpdateRequest);
}
