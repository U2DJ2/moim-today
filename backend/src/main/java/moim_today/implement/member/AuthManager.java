package moim_today.implement.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import moim_today.domain.member.MemberSession;
import moim_today.dto.auth.MemberLoginRequest;
import moim_today.dto.auth.MemberSignUpRequest;
import moim_today.global.annotation.Implement;
import moim_today.global.error.NotFoundException;
import moim_today.implement.email_subscribe.EmailSubscribeAppender;
import moim_today.persistence.entity.member.MemberJpaEntity;
import moim_today.persistence.repository.member.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static moim_today.global.constant.exception.MemberExceptionConstant.EMAIL_PASSWORD_ERROR;

@Implement
public class AuthManager {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;
    private final EmailSubscribeAppender emailSubscribeAppender;

    public AuthManager(final MemberRepository memberRepository,
                       final PasswordEncoder passwordEncoder,
                       final ObjectMapper objectMapper,
                       final EmailSubscribeAppender emailSubscribeAppender) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.objectMapper = objectMapper;
        this.emailSubscribeAppender = emailSubscribeAppender;
    }

    @Transactional(readOnly = true)
    public void login(final MemberLoginRequest memberLoginRequest,
                      final HttpServletRequest request) {
        MemberJpaEntity memberJpaEntity = memberRepository.getByEmail(memberLoginRequest.email());

        if (passwordEncoder.matches(memberLoginRequest.password(), memberJpaEntity.getPassword())) {
            MemberSession memberSession = MemberSession.from(memberJpaEntity);
            String memberSessionJson = memberSession.toJson(objectMapper);
            memberSession.setSession(request, memberSessionJson, memberLoginRequest.isKeepLogin());
            return;
        }
        throw new NotFoundException(EMAIL_PASSWORD_ERROR.message());
    }

    public void logout(final HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    @Transactional
    public void signUp(final MemberSignUpRequest memberSignUpRequest, final HttpServletRequest request) {
        String encodedPassword = passwordEncode(memberSignUpRequest.password());
        MemberJpaEntity saveMember = memberRepository.save(memberSignUpRequest.toEntity(encodedPassword));
        MemberSession memberSession = MemberSession.from(saveMember);
        String memberSessionJson = memberSession.toJson(objectMapper);
        memberSession.setSession(request, memberSessionJson, false);
        emailSubscribeAppender.saveEmailSubscription(memberSession.id(), true);
    }

    private String passwordEncode(final String password){
        return passwordEncoder.encode(password);
    }
}
