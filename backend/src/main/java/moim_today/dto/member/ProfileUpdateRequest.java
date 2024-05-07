package moim_today.dto.member;

public record ProfileUpdateRequest(
        long departmentId,
        String imageUrl
) {
}
