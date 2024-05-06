package moim_today.dto.member;


public record MemberSimpleResponse(
        long memberId,
        String username,
        String memberProfileImageUrl
) {
}
