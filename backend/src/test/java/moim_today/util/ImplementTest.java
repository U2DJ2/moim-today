package moim_today.util;

import moim_today.persistence.repository.certification.email.EmailCertificationRepository;
import moim_today.persistence.repository.certification.password.PasswordCertificationRepository;
import moim_today.persistence.repository.department.DepartmentRepository;
import moim_today.persistence.repository.member.MemberRepository;
import moim_today.persistence.repository.university.UniversityRepository;
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
    protected PasswordCertificationRepository passwordCertificationRepository;

    @Autowired
    protected EmailCertificationRepository emailCertificationRepository;

    @Autowired
    protected UniversityRepository universityRepository;

    @Autowired
    protected DepartmentRepository departmentRepository;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUpDatabase() {
        databaseCleaner.cleanUp();
    }
}
