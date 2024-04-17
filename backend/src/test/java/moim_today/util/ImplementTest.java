package moim_today.util;

import moim_today.persistence.repository.certification_token.CertificationTokenRepository;
import moim_today.persistence.repository.department.DepartmentRepository;
import moim_today.persistence.repository.member.MemberRepository;
import moim_today.persistence.repository.university.UniversityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.session.jdbc.JdbcIndexedSessionRepository;

@SpringBootTest
public abstract class ImplementTest {

    @Autowired
    protected DatabaseCleaner databaseCleaner;

    @Autowired
    protected MemberRepository memberRepository;

    @Autowired
    protected CertificationTokenRepository certificationTokenRepository;

    @Autowired
    protected UniversityRepository universityRepository;

    @Autowired
    protected DepartmentRepository departmentRepository;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    protected JdbcIndexedSessionRepository jdbcIndexedSessionRepository;

    @BeforeEach
    void setUpDatabase() {
        databaseCleaner.cleanUp();
    }
}
