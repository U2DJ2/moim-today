package moim_today.fake_class.moim;

import moim_today.application.moim.moim_notice.MoimNoticeService;
import moim_today.dto.moim.moim_notice.MoimNoticeCreateRequest;
import moim_today.dto.moim.moim_notice.SimpleMoimNoticeResponse;
import moim_today.global.error.ForbiddenException;

import java.time.LocalDateTime;
import java.util.List;

import static moim_today.global.constant.exception.MoimExceptionConstant.MOIM_FORBIDDEN;
import static moim_today.util.TestConstant.FORBIDDEN_MOIM_ID;
import static moim_today.util.TestConstant.NOTICE_TITLE;

public class FakeMoimNoticeService implements MoimNoticeService {
    @Override
    public void createMoimNotice(final long memberId, final MoimNoticeCreateRequest moimNoticeCreateRequest) {
        if (moimNoticeCreateRequest.moimId() == FORBIDDEN_MOIM_ID.longValue()) {
            throw new ForbiddenException(MOIM_FORBIDDEN.message());
        }
    }

    @Override
    public List<SimpleMoimNoticeResponse> findAllMoimNotice(final long memberId, final long moimId) {
        if (moimId == FORBIDDEN_MOIM_ID.longValue()) {
            throw new ForbiddenException(MOIM_FORBIDDEN.message());
        }

        SimpleMoimNoticeResponse simpleMoimNoticeResponse1 = SimpleMoimNoticeResponse.builder()
                .moimNoticeId(1L)
                .title(NOTICE_TITLE.value())
                .createdAt(LocalDateTime.now())
                .build();

        SimpleMoimNoticeResponse simpleMoimNoticeResponse2 = SimpleMoimNoticeResponse.builder()
                .moimNoticeId(2L)
                .title(NOTICE_TITLE.value())
                .createdAt(LocalDateTime.now())
                .build();

        SimpleMoimNoticeResponse simpleMoimNoticeResponse3 = SimpleMoimNoticeResponse.builder()
                .moimNoticeId(3L)
                .title(NOTICE_TITLE.value())
                .createdAt(LocalDateTime.now())
                .build();

        return List.of(simpleMoimNoticeResponse1, simpleMoimNoticeResponse2, simpleMoimNoticeResponse3);
    }
}
