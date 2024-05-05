package moim_today.dto.moim.moim_notice;

import lombok.Builder;

@Builder
public record MoimNoticeUpdateRequest(
        long moimNoticeId,
        String title,
        String contents
) {
}
