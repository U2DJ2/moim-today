package moim_today.domain.certification;

import moim_today.global.error.BadRequestException;

import static moim_today.global.constant.exception.CertificationConstant.EMAIL_NOT_YET_CERTIFICATION_ERROR;


public record EmailCertification(
        Certification certification,
        boolean certificationStatus
) {

    public static EmailCertification toDomain(final Certification certification, final boolean certificationStatus) {
        return new EmailCertification(certification, certificationStatus);
    }

    public void validateCertificationStatus() {
        if (!certificationStatus) {
            throw new BadRequestException(EMAIL_NOT_YET_CERTIFICATION_ERROR.message());
        }
    }

    public String parseEmailDomain() {
        return certification.parseEmailDomain();
    }
}
