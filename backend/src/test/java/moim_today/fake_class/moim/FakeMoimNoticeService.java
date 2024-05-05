package moim_today.fake_class.moim;

import moim_today.application.moim.moim_notice.MoimNoticeService;
import moim_today.dto.moim.moim_notice.MoimNoticeCreateRequest;
import moim_today.dto.moim.moim_notice.MoimNoticeDetailResponse;
import moim_today.dto.moim.moim_notice.MoimNoticeSimpleResponse;
import moim_today.global.error.ForbiddenException;

import java.time.LocalDateTime;
import java.util.List;

import static moim_today.global.constant.exception.MoimExceptionConstant.MOIM_FORBIDDEN_ERROR;
import static moim_today.util.TestConstant.FORBIDDEN_MOIM_ID;
import static moim_today.util.TestConstant.NOTICE_TITLE;

public class FakeMoimNoticeService implements MoimNoticeService {
    @Override
    public void createMoimNotice(final long memberId, final MoimNoticeCreateRequest moimNoticeCreateRequest) {
        if (moimNoticeCreateRequest.moimId() == FORBIDDEN_MOIM_ID.longValue()) {
            throw new ForbiddenException(MOIM_FORBIDDEN_ERROR.message());
        }
    }

    @Override
    public List<MoimNoticeSimpleResponse> findAllMoimNotice(final long memberId, final long moimId) {
        if (moimId == FORBIDDEN_MOIM_ID.longValue()) {
            throw new ForbiddenException(MOIM_FORBIDDEN_ERROR.message());
        }

        MoimNoticeSimpleResponse moimNoticeSimpleResponse1 = MoimNoticeSimpleResponse.builder()
                .moimNoticeId(1L)
                .title(NOTICE_TITLE.value())
                .createdAt(LocalDateTime.now())
                .build();

        MoimNoticeSimpleResponse moimNoticeSimpleResponse2 = MoimNoticeSimpleResponse.builder()
                .moimNoticeId(2L)
                .title(NOTICE_TITLE.value())
                .createdAt(LocalDateTime.now())
                .build();

        MoimNoticeSimpleResponse moimNoticeSimpleResponse3 = MoimNoticeSimpleResponse.builder()
                .moimNoticeId(3L)
                .title(NOTICE_TITLE.value())
                .createdAt(LocalDateTime.now())
                .build();

        return List.of(moimNoticeSimpleResponse1, moimNoticeSimpleResponse2, moimNoticeSimpleResponse3);
    }

    @Override
    public MoimNoticeDetailResponse getMoimNoticeDetail(final long memberId, final long moimNoticeId) {
        return null;
    }
}
