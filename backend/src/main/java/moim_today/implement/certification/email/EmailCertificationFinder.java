package moim_today.implement.certification.email;

import moim_today.domain.certification.Certification;
import moim_today.domain.certification.EmailCertification;
import moim_today.dto.certification.CompleteEmailCertificationResponse;
import moim_today.global.annotation.Implement;
import moim_today.implement.university.UniversityFinder;
import moim_today.persistence.entity.certification.email.EmailCertificationJpaEntity;
import moim_today.persistence.entity.university.UniversityJpaEntity;
import moim_today.persistence.repository.certification.email.EmailCertificationRepository;
import org.springframework.transaction.annotation.Transactional;


@Implement
public class EmailCertificationFinder {

    private final EmailCertificationRepository emailCertificationRepository;
    private final UniversityFinder universityFinder;

    public EmailCertificationFinder(final EmailCertificationRepository emailCertificationRepository,
                                    final UniversityFinder universityFinder) {
        this.emailCertificationRepository = emailCertificationRepository;
        this.universityFinder = universityFinder;
    }

    @Transactional(readOnly = true)
    public CompleteEmailCertificationResponse completeCertification(final String email) {
        EmailCertificationJpaEntity emailCertificationJpaEntity = emailCertificationRepository.getByEmail(email);
        String emailDomain = parseEmailDomain(emailCertificationJpaEntity);
        UniversityJpaEntity universityJpaEntity = universityFinder.getByUniversityEmail(emailDomain);

        return CompleteEmailCertificationResponse.of(
                universityJpaEntity.getId(), universityJpaEntity.getUniversityName()
        );
    }

    private String parseEmailDomain(final EmailCertificationJpaEntity emailCertificationJpaEntity) {
        Certification certification = Certification.toDomain(emailCertificationJpaEntity);
        EmailCertification emailCertification =
                EmailCertification.toDomain(certification, emailCertificationJpaEntity.isCertificationStatus());
        emailCertification.validateCertificationStatus();

        return emailCertification.parseEmailDomain();
    }
}
