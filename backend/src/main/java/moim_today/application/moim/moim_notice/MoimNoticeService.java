package moim_today.application.moim.moim_notice;

import moim_today.dto.moim.moim_notice.*;

import java.util.List;

public interface MoimNoticeService {

    void createMoimNotice(final long memberId, final MoimNoticeCreateRequest moimNoticeCreateRequest);

    List<MoimNoticeSimpleResponse> findAllMoimNotice(final long memberId, final long moimId, final long lastMoimNoticeId);

    MoimNoticeDetailResponse getMoimNoticeDetail(final long memberId, final long moimNoticeId);

    void updateMoimNotice(final long memberId, final MoimNoticeUpdateRequest moimNoticeUpdateRequest);

    void deleteMoimNotice(final long memberId, final MoimNoticeDeleteRequest moimNoticeDeleteRequest);
}
