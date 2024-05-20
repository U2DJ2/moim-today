package moim_today.persistence.repository.email_subscribe;

import moim_today.global.error.NotFoundException;
import moim_today.persistence.entity.email_subscribe.EmailSubscribeJpaEntity;
import org.springframework.stereotype.Repository;

import static moim_today.global.constant.exception.MailExceptionConstant.MAIL_SUBSCRIBE_NOT_FOUND_ERROR;

@Repository
public class EmailSubscribeRepositoryImpl implements EmailSubscribeRepository {

    private final EmailSubscribeJpaRepository emailSubscribeJpaRepository;

    public EmailSubscribeRepositoryImpl(final EmailSubscribeJpaRepository emailSubscribeJpaRepository) {
        this.emailSubscribeJpaRepository = emailSubscribeJpaRepository;
    }

    @Override
    public EmailSubscribeJpaEntity getById(final long emailSubscribeId) {
        return emailSubscribeJpaRepository.findById(emailSubscribeId)
                .orElseThrow(() -> new NotFoundException(MAIL_SUBSCRIBE_NOT_FOUND_ERROR.message()));
    }

    @Override
    public EmailSubscribeJpaEntity getByMemberId(final long memberId) {
        return emailSubscribeJpaRepository.findByMemberId(memberId)
                .orElseThrow(() -> new NotFoundException(MAIL_SUBSCRIBE_NOT_FOUND_ERROR.message()));
    }

    @Override
    public EmailSubscribeJpaEntity save(final EmailSubscribeJpaEntity emailSubscribeJpaEntity) {
        return emailSubscribeJpaRepository.save(emailSubscribeJpaEntity);
    }
}
