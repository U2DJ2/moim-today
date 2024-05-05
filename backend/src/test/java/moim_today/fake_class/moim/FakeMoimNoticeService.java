package moim_today.fake_class.moim;

import moim_today.application.moim.moim_notice.MoimNoticeService;
import moim_today.dto.moim.moim_notice.*;
import moim_today.global.error.ForbiddenException;

import java.time.LocalDateTime;
import java.util.List;

import static moim_today.global.constant.exception.MoimExceptionConstant.MOIM_FORBIDDEN_ERROR;
import static moim_today.global.constant.exception.MoimExceptionConstant.ORGANIZER_FORBIDDEN_ERROR;
import static moim_today.util.TestConstant.*;

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
        if (moimNoticeId == FORBIDDEN_NOTICE_ID.longValue()) {
            throw new ForbiddenException(MOIM_FORBIDDEN_ERROR.message());
        }

        return MoimNoticeDetailResponse.builder()
                .moimNoticeId(NOTICE_ID.longValue())
                .title(NOTICE_TITLE.value())
                .contents(NOTICE_CONTENTS.value())
                .createdAt(LocalDateTime.now())
                .lastModifiedAt(LocalDateTime.now())
                .build();
    }

    @Override
    public void updateMoimNotice(final long memberId, final MoimNoticeUpdateRequest moimNoticeUpdateRequest) {
        if (moimNoticeUpdateRequest.moimNoticeId() == FORBIDDEN_NOTICE_ID.longValue()) {
            throw new ForbiddenException(ORGANIZER_FORBIDDEN_ERROR.message());
        }
    }

    @Override
    public void deleteMoimNotice(final long memberId, final MoimNoticeDeleteRequest moimNoticeDeleteRequest) {

    }
}
