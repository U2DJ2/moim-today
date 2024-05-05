package moim_today.fake_class.moim;

import moim_today.application.moim.moim_notice.MoimNoticeService;
import moim_today.dto.moim.moim_notice.MoimNoticeCreateRequest;
import moim_today.dto.moim.moim_notice.SimpleMoimNoticeResponse;

import java.time.LocalDateTime;
import java.util.List;

import static moim_today.util.TestConstant.TITLE;

public class FakeMoimNoticeService implements MoimNoticeService {
    @Override
    public void createMoimNotice(final long memberId, final MoimNoticeCreateRequest moimNoticeCreateRequest) {

    }

    @Override
    public List<SimpleMoimNoticeResponse> findAllMoimNotice(final long memberId, final long moimId) {
        SimpleMoimNoticeResponse simpleMoimNoticeResponse1 = SimpleMoimNoticeResponse.builder()
                .moimNoticeId(1L)
                .title(TITLE.value())
                .createdAt(LocalDateTime.now())
                .build();

        SimpleMoimNoticeResponse simpleMoimNoticeResponse2 = SimpleMoimNoticeResponse.builder()
                .moimNoticeId(2L)
                .title(TITLE.value())
                .createdAt(LocalDateTime.now())
                .build();

        SimpleMoimNoticeResponse simpleMoimNoticeResponse3 = SimpleMoimNoticeResponse.builder()
                .moimNoticeId(3L)
                .title(TITLE.value())
                .createdAt(LocalDateTime.now())
                .build();

        return List.of(simpleMoimNoticeResponse1, simpleMoimNoticeResponse2, simpleMoimNoticeResponse3);
    }
}
