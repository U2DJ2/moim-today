package booki_today.implement.member;

import booki_today.domain.member.MemberSession;
import booki_today.dto.auth.MemberLoginRequest;
import booki_today.global.annotation.Implement;
import booki_today.global.error.NotFoundException;
import booki_today.persistence.entity.member.MemberJpaEntity;
import booki_today.persistence.repository.member.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static booki_today.global.constant.MemberExceptionConstant.EMAIL_PASSWORD_ERROR;
import static booki_today.global.constant.MemberSessionConstant.MEMBER_SESSION;

@Implement
public class MemberAuthentication {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;

    public MemberAuthentication(final MemberRepository memberRepository,
                                final PasswordEncoder passwordEncoder,
                                final ObjectMapper objectMapper) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.objectMapper = objectMapper;
    }

    @Transactional(readOnly = true)
    public void login(final MemberLoginRequest memberLoginRequest,
                      final HttpServletRequest request) {
        MemberJpaEntity memberJpaEntity = memberRepository.getByEmail(memberLoginRequest.email());

        if (passwordEncoder.matches(memberLoginRequest.password(), memberJpaEntity.getPassword())) {
            MemberSession memberSession = MemberSession.from(memberJpaEntity);
            String memberSessionJson = memberSession.toJson(objectMapper);
            HttpSession session = request.getSession(true);
            session.setAttribute(MEMBER_SESSION.value(), memberSessionJson);
            return;
        }
        throw new NotFoundException(EMAIL_PASSWORD_ERROR.message());
    }
}
