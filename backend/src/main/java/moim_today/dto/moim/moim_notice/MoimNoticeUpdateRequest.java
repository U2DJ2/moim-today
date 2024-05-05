package moim_today.dto.moim.moim_notice;

public record MoimNoticeUpdateRequest(
        long moimNoticeId,
        String title,
        String contents
) {
}
