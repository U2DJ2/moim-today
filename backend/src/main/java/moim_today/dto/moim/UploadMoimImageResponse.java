package moim_today.dto.moim;

public record UploadMoimImageResponse(
        String imageUrl
) {

    public static UploadMoimImageResponse from(String imageUrl) {
        return new UploadMoimImageResponse(imageUrl);
    }
}
