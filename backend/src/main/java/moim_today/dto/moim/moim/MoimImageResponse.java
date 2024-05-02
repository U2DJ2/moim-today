package moim_today.dto.moim.moim;

public record MoimImageResponse(
        String imageUrl
) {

    public static MoimImageResponse from(String imageUrl) {
        return new MoimImageResponse(imageUrl);
    }
}
