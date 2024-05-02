package moim_today.dto.member;

public record ProfileImageResponse(
        String imageUrl
) {

    public static ProfileImageResponse from(String imageUrl) {
        return new ProfileImageResponse(imageUrl);
    }
}
