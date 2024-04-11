package moim_today.util;

import moim_today.persistence.repository.certification_token.CertificationTokenRepository;
import moim_today.persistence.repository.member.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public abstract class ImplementTest {

    @Autowired
    protected DatabaseCleaner databaseCleaner;

    @Autowired
    protected MemberRepository memberRepository;

    @Autowired
    protected CertificationTokenRepository certificationTokenRepository;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUpDatabase() {
        databaseCleaner.cleanUp();
    }
}
