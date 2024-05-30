package moim_today.application.admin.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import moim_today.domain.member.MemberSession;
import moim_today.dto.auth.MemberLoginRequest;
import moim_today.global.error.ForbiddenException;
import moim_today.global.error.NotFoundException;
import moim_today.persistence.entity.member.MemberJpaEntity;
import moim_today.persistence.repository.member.MemberRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static moim_today.global.constant.exception.AdminExceptionConstant.ADMIN_FORBIDDEN_ERROR;
import static moim_today.global.constant.exception.MemberExceptionConstant.EMAIL_PASSWORD_ERROR;


@Service
public class AdminAuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;

    @Value("${admin.email}")
    private String adminEmail;

    public AdminAuthService(final MemberRepository memberRepository,
                            final PasswordEncoder passwordEncoder,
                            final ObjectMapper objectMapper) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.objectMapper = objectMapper;
    }

    @Transactional(readOnly = true)
    public void login(final MemberLoginRequest memberLoginRequest, final HttpServletRequest request) {
        if(isNotAdmin(adminEmail, memberLoginRequest.email())) {
            throw new ForbiddenException(ADMIN_FORBIDDEN_ERROR.message());
        }

        MemberJpaEntity memberJpaEntity = memberRepository.getByEmail(memberLoginRequest.email());

        if (passwordEncoder.matches(memberLoginRequest.password(), memberJpaEntity.getPassword())) {
            MemberSession memberSession = MemberSession.from(memberJpaEntity);
            String memberSessionJson = memberSession.toJson(objectMapper);
            memberSession.setSession(request, memberSessionJson, memberLoginRequest.isKeepLogin());
            return;
        }
        throw new NotFoundException(EMAIL_PASSWORD_ERROR.message());
    }

    public boolean validateAdmin(final long memberId) {
        try {
            MemberJpaEntity member = memberRepository.getById(memberId);
            if(isNotAdmin(adminEmail, member.getEmail())) {
                throw new ForbiddenException(ADMIN_FORBIDDEN_ERROR.message());
            }

            return true;
        } catch (final ForbiddenException e) {
            return false;
        }
    }

    private boolean isNotAdmin(final String adminEmail, final String memberEmail) {
        return !(memberEmail.equals(adminEmail));
    }
}
