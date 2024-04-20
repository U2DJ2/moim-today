package moim_today.fake_class.certification;

import moim_today.application.certification.email.EmailCertificationService;
import moim_today.dto.certification.CompleteEmailCertificationResponse;
import moim_today.global.error.BadRequestException;
import moim_today.global.error.NotFoundException;

import static moim_today.global.constant.exception.CertificationConstant.EMAIL_NOT_YET_CERTIFICATION_ERROR;
import static moim_today.global.constant.exception.MemberExceptionConstant.ALREADY_EXIST_EMAIL_ERROR;
import static moim_today.global.constant.exception.UniversityExceptionConstant.UNIVERSITY_EMAIL_NOT_FOUND;
import static moim_today.util.TestConstant.*;

public class FakeEmailCertificationService implements EmailCertificationService {

    @Override
    public void sendCertificationEmail(final String email) {
        // 이미 가입된 이메일로 인증할 수 없음
        if (email.equals(ALREADY_EXIST_EMAIL.value())) {
            throw new BadRequestException(ALREADY_EXIST_EMAIL_ERROR.message());
        }

        // 잘못된 형식의 이메일로는 가입할 수 없음
        if (email.equals(WRONG_EMAIL.value())) {
            throw new NotFoundException(UNIVERSITY_EMAIL_NOT_FOUND.message());
        }
    }

    // 백엔드 내부 API 문서화 X
    @Override
    public void certifyEmail(final String certificationToken) {

    }

    @Override
    public CompleteEmailCertificationResponse completeCertification(final String email) {
        if (!email.equals(AJOU_EMAIL.value())) {
            throw new BadRequestException(EMAIL_NOT_YET_CERTIFICATION_ERROR.message());
        }

        return new CompleteEmailCertificationResponse(1, "아주대학교");
    }
}
