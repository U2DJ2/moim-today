package moim_today.application.admin.member;

import moim_today.domain.member.enums.Gender;
import moim_today.dto.member.MemberResponse;
import moim_today.global.constant.NumberConstant;
import moim_today.global.error.ForbiddenException;
import moim_today.persistence.entity.department.DepartmentJpaEntity;
import moim_today.persistence.entity.meeting.joined_meeting.JoinedMeetingJpaEntity;
import moim_today.persistence.entity.meeting.meeting.MeetingJpaEntity;
import moim_today.persistence.entity.meeting.meeting_comment.MeetingCommentJpaEntity;
import moim_today.persistence.entity.member.MemberJpaEntity;
import moim_today.persistence.entity.moim.joined_moim.JoinedMoimJpaEntity;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import moim_today.persistence.entity.schedule.schedule.ScheduleJpaEntity;
import moim_today.persistence.entity.todo.TodoJpaEntity;
import moim_today.persistence.entity.university.UniversityJpaEntity;
import moim_today.persistence.repository.department.department.DepartmentRepository;
import moim_today.persistence.repository.meeting.joined_meeting.JoinedMeetingJpaRepository;
import moim_today.persistence.repository.meeting.meeting.MeetingJpaRepository;
import moim_today.persistence.repository.meeting.meeting_comment.MeetingCommentJpaRepository;
import moim_today.persistence.repository.member.MemberRepository;
import moim_today.persistence.repository.moim.joined_moim.JoinedMoimJpaRepository;
import moim_today.persistence.repository.moim.moim.MoimJpaRepository;
import moim_today.persistence.repository.schedule.schedule.ScheduleJpaRepository;
import moim_today.persistence.repository.todo.TodoJpaRepository;
import moim_today.persistence.repository.university.UniversityRepository;
import moim_today.util.DatabaseCleaner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static moim_today.global.constant.MemberConstant.*;
import static moim_today.global.constant.NumberConstant.*;
import static moim_today.global.constant.exception.AdminExceptionConstant.ADMIN_FORBIDDEN_ERROR;
import static moim_today.util.TestConstant.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class AdminMemberServiceTest {

    @Autowired
    private AdminMemberService adminMemberService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private UniversityRepository universityRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private MoimJpaRepository moimJpaRepository;
    @Autowired
    private MeetingJpaRepository meetingJpaRepository;
    @Autowired
    private MeetingCommentJpaRepository meetingCommentJpaRepository;
    @Autowired
    private JoinedMoimJpaRepository joinedMoimJpaRepository;
    @Autowired
    private JoinedMeetingJpaRepository joinedMeetingJpaRepository;
    @Autowired
    private TodoJpaRepository todoJpaRepository;
    @Autowired
    private ScheduleJpaRepository scheduleJpaRepository;
    @Autowired
    private DatabaseCleaner databaseCleaner;

    @BeforeEach
    void setUpDatabase() {
        databaseCleaner.cleanUp();
    }

    @DisplayName("어드민 - 대학교Id, 학과Id로 모든 멤버를 조회한다.")
    @Test
    void findAllMembers() {
        //given
        UniversityJpaEntity university = UniversityJpaEntity.builder()
                .build();

        UniversityJpaEntity otherUniversity = UniversityJpaEntity.builder()
                .build();

        universityRepository.save(university);
        universityRepository.save(otherUniversity);

        DepartmentJpaEntity department = DepartmentJpaEntity.builder()
                .build();

        DepartmentJpaEntity otherDepartment = DepartmentJpaEntity.builder()
                .build();

        departmentRepository.save(department);
        departmentRepository.save(otherDepartment);

        String studentId = STUDENT_ID.value();

        MemberJpaEntity member1 = MemberJpaEntity.builder()
                .universityId(university.getId())
                .departmentId(department.getId())
                .studentId(studentId)
                .build();

        MemberJpaEntity member2 = MemberJpaEntity.builder()
                .universityId(university.getId())
                .departmentId(department.getId())
                .studentId(studentId)
                .build();

        MemberJpaEntity member3 = MemberJpaEntity.builder()
                .universityId(university.getId())
                .departmentId(otherDepartment.getId())
                .studentId(studentId)
                .build();

        MemberJpaEntity member4 = MemberJpaEntity.builder()
                .universityId(otherUniversity.getId())
                .departmentId(otherDepartment.getId())
                .studentId(studentId)
                .build();

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        memberRepository.save(member4);

        //when
        List<MemberResponse> allMembers1 = adminMemberService.findAllMembers(university.getId(), department.getId());
        List<MemberResponse> allMembers2 = adminMemberService.findAllMembers(otherUniversity.getId(), otherDepartment.getId());

        //then
        assertThat(allMembers1.size()).isEqualTo(2);
        assertThat(allMembers2.size()).isEqualTo(1);
    }

    @DisplayName("어드민 - 회원을 삭제한다.")
    @Test
    void deleteMember() {
        // given1
        UniversityJpaEntity university = UniversityJpaEntity.builder()
                .build();

        universityRepository.save(university);

        MemberJpaEntity member = MemberJpaEntity.builder()
                .universityId(university.getId())
                .build();

        MemberJpaEntity hostMember = MemberJpaEntity.builder()
                .build();

        memberRepository.save(member);
        memberRepository.save(hostMember);

        // given2
        MoimJpaEntity moim1 = MoimJpaEntity.builder()
                .memberId(hostMember.getId())
                .build();

        MoimJpaEntity moim2 = MoimJpaEntity.builder()
                .memberId(hostMember.getId())
                .build();

        moimJpaRepository.save(moim1);
        moimJpaRepository.save(moim2);

        JoinedMoimJpaEntity joinedMoimJpaEntity1 = JoinedMoimJpaEntity.builder()
                .moimId(moim1.getId())
                .memberId(member.getId())
                .build();

        JoinedMoimJpaEntity joinedMoimJpaEntity2 = JoinedMoimJpaEntity.builder()
                .moimId(moim2.getId())
                .memberId(member.getId())
                .build();

        joinedMoimJpaRepository.save(joinedMoimJpaEntity1);
        joinedMoimJpaRepository.save(joinedMoimJpaEntity2);

        // given3
        MeetingJpaEntity meetingJpaEntity1 = MeetingJpaEntity.builder()
                .moimId(moim1.getId())
                .build();
        MeetingJpaEntity meetingJpaEntity2 = MeetingJpaEntity.builder()
                .moimId(moim2.getId())
                .build();

        meetingJpaRepository.save(meetingJpaEntity1);
        meetingJpaRepository.save(meetingJpaEntity2);

        JoinedMeetingJpaEntity jm1 = JoinedMeetingJpaEntity.builder()
                .meetingId(meetingJpaEntity1.getId())
                .memberId(hostMember.getId())
                .build();
        JoinedMeetingJpaEntity jm2 = JoinedMeetingJpaEntity.builder()
                .meetingId(meetingJpaEntity1.getId())
                .memberId(member.getId())
                .build();
        JoinedMeetingJpaEntity jm3 = JoinedMeetingJpaEntity.builder()
                .meetingId(meetingJpaEntity2.getId())
                .memberId(hostMember.getId())
                .build();
        JoinedMeetingJpaEntity jm4 = JoinedMeetingJpaEntity.builder()
                .meetingId(meetingJpaEntity2.getId())
                .memberId(member.getId())
                .build();

        joinedMeetingJpaRepository.save(jm1);
        joinedMeetingJpaRepository.save(jm2);
        joinedMeetingJpaRepository.save(jm3);
        joinedMeetingJpaRepository.save(jm4);

        // given4
        MeetingCommentJpaEntity mc1 = MeetingCommentJpaEntity.builder()
                .meetingId(meetingJpaEntity1.getId())
                .memberId(member.getId())
                .build();
        MeetingCommentJpaEntity mc2 = MeetingCommentJpaEntity.builder()
                .meetingId(meetingJpaEntity1.getId())
                .memberId(hostMember.getId())
                .build();
        MeetingCommentJpaEntity mc3 = MeetingCommentJpaEntity.builder()
                .meetingId(meetingJpaEntity2.getId())
                .memberId(member.getId())
                .build();

        meetingCommentJpaRepository.save(mc1);
        meetingCommentJpaRepository.save(mc2);
        meetingCommentJpaRepository.save(mc3);

        // given5
        TodoJpaEntity todo = TodoJpaEntity.builder()
                .moimId(moim1.getId())
                .memberId(member.getId())
                .build();

        todoJpaRepository.save(todo);

        // given6
        ScheduleJpaEntity s1 = ScheduleJpaEntity.builder()
                .memberId(member.getId())
                .build();

        ScheduleJpaEntity s2 = ScheduleJpaEntity.builder()
                .memberId(member.getId())
                .build();

        scheduleJpaRepository.save(s1);
        scheduleJpaRepository.save(s2);

        // when
        adminMemberService.deleteMember(university.getId(), member.getId());

        // then1
        MemberJpaEntity deletedMember = memberRepository.getById(member.getId());
        assertThat(deletedMember.getEmail()).isNull();
        assertThat(deletedMember.getPassword().length()).isEqualTo(DELETED_MEMBER_PASSWORD_LENGTH.value());
        assertThat(deletedMember.getUsername()).isEqualTo(DELETED_MEMBER_USERNAME.value());
        assertThat(deletedMember.getStudentId()).isEqualTo(DELETED_MEMBER_STUDENT_ID.value());
        assertThat(deletedMember.getGender()).isEqualTo(Gender.UNKNOWN);
        assertThat(deletedMember.getMemberProfileImageUrl()).isEqualTo(DEFAULT_PROFILE_URL.value());
        assertThat(deletedMember.getBirthDate().getYear()).isEqualTo(DELETED_MEMBER_BIRTH_YEAR.value());
        assertThat(deletedMember.getBirthDate().getMonthValue()).isEqualTo(DELETED_MEMBER_BIRTH_MONTH.value());
        assertThat(deletedMember.getBirthDate().getDayOfMonth()).isEqualTo(DELETED_MEMBER_BIRTH_DAY.value());

        // then2
        assertThat(joinedMoimJpaRepository.findAllByMemberId(deletedMember.getId())).isEmpty();
        assertThat(joinedMeetingJpaRepository.findByMemberIdAndMeetingId(deletedMember.getId(), meetingJpaEntity1.getId()))
                .isEmpty();
        assertThat(joinedMeetingJpaRepository.findByMemberIdAndMeetingId(deletedMember.getId(), meetingJpaEntity2.getId()))
                .isEmpty();
        assertThat(meetingCommentJpaRepository.findById(mc1.getId()).isPresent()).isTrue();
        assertThat(meetingCommentJpaRepository.findById(mc1.getId()).get().getMemberId()).isEqualTo(UNKNOWN_MEMBER.longValue());
        assertThat(todoJpaRepository.findById(todo.getId())).isEmpty();
        assertThat(scheduleJpaRepository.findAllByMemberId(deletedMember.getId())).isEmpty();
    }

    @DisplayName("어드민 - 다른 학교의 회원은 삭제할 수 없다.")
    @Test
    void deleteOtherUniversityMember() {
        //given
        UniversityJpaEntity university = UniversityJpaEntity.builder()
                .build();

        UniversityJpaEntity otherUniversity = UniversityJpaEntity.builder()
                .build();

        universityRepository.save(university);

        MemberJpaEntity member = MemberJpaEntity.builder()
                .universityId(otherUniversity.getId())
                .build();

        memberRepository.save(member);

        //expected
        assertThatThrownBy(() -> adminMemberService.deleteMember(university.getId(), member.getId()))
                .isInstanceOf(ForbiddenException.class)
                .hasMessage(ADMIN_FORBIDDEN_ERROR.message());
    }
}