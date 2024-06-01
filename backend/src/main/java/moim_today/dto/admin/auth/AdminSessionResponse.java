package moim_today.dto.admin.auth;

import moim_today.domain.university.AdminSession;

public record AdminSessionResponse(
        long universityId
) {

    public static AdminSessionResponse from(final AdminSession adminSession) {
        return new AdminSessionResponse(adminSession.universityId());
    }
}
