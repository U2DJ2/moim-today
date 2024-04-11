package moim_today.dto.member;

public record PasswordRecoverRequest(
        String passwordToken,
        String newPassword
) {
}
