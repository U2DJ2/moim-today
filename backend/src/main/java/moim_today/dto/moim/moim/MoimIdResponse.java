package moim_today.dto.moim.moim;

public record MoimIdResponse(
        long moimId
) {

    public static MoimIdResponse from(final long moimId) {
        return new MoimIdResponse(moimId);
    }
}
