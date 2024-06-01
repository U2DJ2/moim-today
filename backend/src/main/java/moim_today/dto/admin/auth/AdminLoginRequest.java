package moim_today.dto.admin.auth;

public record AdminLoginRequest(
        long universityId,
        String adminPassword
) {
}
