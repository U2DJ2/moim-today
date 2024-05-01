package moim_today.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import moim_today.persistence.repository.certification.email.EmailCertificationRepository;
import moim_today.persistence.repository.certification.password.PasswordCertificationRepository;
import moim_today.persistence.repository.department.DepartmentRepository;
import moim_today.persistence.repository.meeting.joined_meeting.JoinedMeetingRepository;
import moim_today.persistence.repository.meeting.meeting.MeetingRepository;
import moim_today.persistence.repository.member.MemberRepository;
import moim_today.persistence.repository.moim.joined_moim.JoinedMoimRepository;
import moim_today.persistence.repository.moim.moim.MoimRepository;
import moim_today.persistence.repository.schedule.ScheduleRepository;
import moim_today.persistence.repository.todo.TodoRepository;
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
    protected ObjectMapper objectMapper;

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
    protected MoimRepository moimRepository;

    @Autowired
    protected ScheduleRepository scheduleRepository;

    @Autowired
    protected JoinedMoimRepository joinedMoimRepository;

    @Autowired
    protected JoinedMeetingRepository joinedMeetingRepository;

    @Autowired
    protected TodoRepository todoRepository;

    @Autowired
    protected MeetingRepository meetingRepository;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUpDatabase() {
        databaseCleaner.cleanUp();
    }
}
