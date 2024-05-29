package moim_today.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import moim_today.persistence.repository.certification.email.EmailCertificationRepository;
import moim_today.persistence.repository.certification.password.PasswordCertificationRepository;
import moim_today.persistence.repository.department.department.DepartmentRepository;
import moim_today.persistence.repository.department.request_department.RequestDepartmentRepository;
import moim_today.persistence.repository.email_subscribe.EmailSubscribeRepository;
import moim_today.persistence.repository.meeting.joined_meeting.JoinedMeetingRepository;
import moim_today.persistence.repository.meeting.meeting.MeetingRepository;
import moim_today.persistence.repository.meeting.meeting_comment.MeetingCommentRepository;
import moim_today.persistence.repository.member.MemberRepository;
import moim_today.persistence.repository.moim.joined_moim.JoinedMoimRepository;
import moim_today.persistence.repository.moim.moim.MoimRepository;
import moim_today.persistence.repository.moim.moim_notice.MoimNoticeRepository;
import moim_today.persistence.repository.schedule.schedule.ScheduleRepository;
import moim_today.persistence.repository.schedule.schedule_color.ScheduleColorRepository;
import moim_today.persistence.repository.todo.TodoRepository;
import moim_today.persistence.repository.university.UniversityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
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
    protected JoinedMoimRepository joinedMoimRepository;

    @Autowired
    protected MoimRepository moimRepository;

    @Autowired
    protected ScheduleRepository scheduleRepository;

    @Autowired
    protected ScheduleColorRepository scheduleColorRepository;

    @Autowired
    protected JoinedMeetingRepository joinedMeetingRepository;

    @Autowired
    protected TodoRepository todoRepository;

    @Autowired
    protected MeetingRepository meetingRepository;

    @Autowired
    protected MoimNoticeRepository moimNoticeRepository;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    protected MeetingCommentRepository meetingCommentRepository;

    @Autowired
    protected EmailSubscribeRepository emailSubscribeRepository;

    @Autowired
    protected RequestDepartmentRepository requestDepartmentRepository;

    @BeforeEach
    void setUpDatabase() {
        databaseCleaner.cleanUp();
    }

    @Caching(evict = {
            @CacheEvict(value = "moimNotices", allEntries = true),
            @CacheEvict(value = "moimNotice", allEntries = true)
    })
    protected void clearCache() {

    }
}
