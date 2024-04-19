package moim_today.dto.certification;

public record CompleteEmailCertificationResponse(
        long universityId,
        String universityName
) {

    public static CompleteEmailCertificationResponse of(final long universityId, final String universityName) {
        return new CompleteEmailCertificationResponse(universityId, universityName);
    }
}
