package moim_today.implement.member;

import moim_today.domain.member.enums.Gender;
import moim_today.dto.member.MemberProfileResponse;
import moim_today.dto.member.MemberSimpleResponse;
import moim_today.dto.moim.moim.MoimMemberResponse;
import moim_today.global.error.BadRequestException;
import moim_today.global.error.NotFoundException;
import moim_today.persistence.entity.department.DepartmentJpaEntity;
import moim_today.persistence.entity.member.MemberJpaEntity;
import moim_today.persistence.entity.moim.joined_moim.JoinedMoimJpaEntity;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import moim_today.persistence.entity.university.UniversityJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static moim_today.global.constant.exception.MemberExceptionConstant.*;
import static moim_today.util.TestConstant.*;
import static org.assertj.core.api.Assertions.*;

class MemberFinderTest extends ImplementTest {

    @Autowired
    private MemberFinder memberFinder;

    @DisplayName("해당 이메일을 가진 회원이 존재하지 않으면 예외가 발생한다.")
    @Test
    void validateEmailNotExists() {
        // expected
        assertThatThrownBy(() -> memberFinder.validateEmailExists(EMAIL.value()))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(EMAIL_NOT_FOUND_ERROR.message());
    }

    @DisplayName("해당 이메일을 가진 회원이 존재하면 검증에 성공한다.")
    @Test
    void validateEmailExists() {
        // given
        MemberJpaEntity memberJpaEntity = MemberJpaEntity.builder()
                .email(EMAIL.value())
                .build();

        memberRepository.save(memberJpaEntity);

        // when && then
        memberFinder.validateEmailExists(EMAIL.value());
    }

    @DisplayName("memberId로 프로필 조회 성공시 프로필 정보를 반환한다.")
    @Test
    void getProfileByMemberId() {
        // given1
        UniversityJpaEntity universityJpaEntity = UniversityJpaEntity.builder()
                .universityName(UNIVERSITY_NAME.value())
                .build();

        universityRepository.save(universityJpaEntity);
        long universityId = universityJpaEntity.getId();

        // given2
        DepartmentJpaEntity departmentJpaEntity = DepartmentJpaEntity.builder()
                .departmentName(DEPARTMENT_NAME.value())
                .universityId(universityId)
                .build();

        departmentRepository.save(departmentJpaEntity);
        long departmentId = departmentJpaEntity.getId();

        // given3
        MemberJpaEntity memberJpaEntity = MemberJpaEntity.builder()
                .universityId(universityId)
                .departmentId(departmentId)
                .email(EMAIL.value())
                .username(USERNAME.value())
                .studentId(STUDENT_ID.value())
                .birthDate(LocalDate.of(2000, 12, 18))
                .gender(Gender.MALE)
                .memberProfileImageUrl(PROFILE_IMAGE_URL.value())
                .build();

        memberRepository.save(memberJpaEntity);
        long memberId = memberJpaEntity.getId();

        // when
        MemberProfileResponse memberProfileResponse = memberFinder.getMemberProfile(memberId);

        // then
        assertThat(memberProfileResponse.universityName()).isEqualTo(UNIVERSITY_NAME.value());
        assertThat(memberProfileResponse.departmentName()).isEqualTo(DEPARTMENT_NAME.value());
        assertThat(memberProfileResponse.email()).isEqualTo(EMAIL.value());
        assertThat(memberProfileResponse.username()).isEqualTo(USERNAME.value());
        assertThat(memberProfileResponse.studentId()).isEqualTo(STUDENT_ID.value());
        assertThat(memberProfileResponse.birthDate()).isEqualTo(LocalDate.of(2000, 12, 18));
        assertThat(memberProfileResponse.gender()).isEqualTo(Gender.MALE);
        assertThat(memberProfileResponse.memberProfileImageUrl()).isEqualTo(PROFILE_IMAGE_URL.value());
    }

    @DisplayName("memberId로 프로필 조회 실패시 프로필 예외가 발생한다.")
    @Test
    void getProfileByWrongMemberId() {
        // expected
        assertThatThrownBy(() -> memberFinder.getMemberProfile(1L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(MEMBER_NOT_FOUND_ERROR.message());
    }

    @DisplayName("이미 가입한 이메일일 경우 예외가 발생한다.")
    @Test
    void alreadyRegisteredEmailTest() {
        // given
        MemberJpaEntity memberJpaEntity = MemberJpaEntity.builder()
                .email(EMAIL.value())
                .build();
        memberRepository.save(memberJpaEntity);

        // then
        assertThatThrownBy(() -> memberFinder.validateEmailNotExists(EMAIL.value()))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(EMAIL_ALREADY_USED_ERROR.message());
    }

    @DisplayName("가입한 이메일이 없을 경우 예외가 발생하지 않는다.")
    @Test
    void notRegisteredEmailTest() {
        // expected
        assertThatCode(() -> {
            memberFinder.validateEmailNotExists(EMAIL.value());
        }).doesNotThrowAnyException();
    }


    @DisplayName("모임 생성자의 프로필 정보를 가져온다.")
    @Test
    void getHostProfileTest() {
        // given1
        MemberJpaEntity memberJpaEntity = MemberJpaEntity.builder()
                .username(USERNAME.value())
                .memberProfileImageUrl(PROFILE_IMAGE_URL.value())
                .build();

        memberRepository.save(memberJpaEntity);
        long memberId = memberJpaEntity.getId();

        // given2
        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .memberId(memberId)
                .build();

        moimRepository.save(moimJpaEntity);
        long moimId = moimJpaEntity.getId();

        //when
        MemberSimpleResponse memberSimpleResponse = memberFinder.getHostProfileByMoimId(moimId);

        //then
        assertThat(memberSimpleResponse.memberId()).isEqualTo(memberId);
        assertThat(memberSimpleResponse.username()).isEqualTo(USERNAME.value());
        assertThat(memberSimpleResponse.memberProfileImageUrl()).isEqualTo(PROFILE_IMAGE_URL.value());
    }

    @DisplayName("모임이 없으면 모임 생성자를 조회할 때 예외가 발생한다.")
    @Test
    void getHostProfileNotExistMoimTest() {
        // given
        long notFoundMoimId = MOIM_ID.longValue();

        //expected
        assertThatThrownBy(() -> memberFinder.getHostProfileByMoimId(notFoundMoimId))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(HOST_NOT_FOUND_ERROR.message());
    }

    @DisplayName("모임이 있어도 생성자가 존재하지 않으면 조회할 때 예외가 발생한다.")
    @Test
    void getHostProfileNotExistHostTest() {
        // given
        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .memberId(MEMBER_ID.longValue())
                .build();

        moimRepository.save(moimJpaEntity);
        long moimId = moimJpaEntity.getId();

        //expected
        assertThatThrownBy(() -> memberFinder.getHostProfileByMoimId(moimId))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(HOST_NOT_FOUND_ERROR.message());
    }

    @DisplayName("모임에 참여한 멤버들의 id를 가지고 멤버 정보를 조회한다")
    @Test
    void findMoimMembers() {
        // given
        List<String> names = List.of("hostName", "member1", "member2", "member3", "member4");

        MemberJpaEntity host = MemberJpaEntity.builder()
                .username(names.get(0))
                .build();
        MemberJpaEntity moimMember1 = MemberJpaEntity.builder()
                .username(names.get(1))
                .build();
        MemberJpaEntity moimMember2 = MemberJpaEntity.builder()
                .username(names.get(2))
                .build();
        MemberJpaEntity moimMember3 = MemberJpaEntity.builder()
                .username(names.get(3))
                .build();
        MemberJpaEntity moimMember4 = MemberJpaEntity.builder()
                .username(names.get(4))
                .build();

        MemberJpaEntity saveHost = memberRepository.save(host);
        MemberJpaEntity saveMoimMember1 = memberRepository.save(moimMember1);
        MemberJpaEntity saveMoimMember2 = memberRepository.save(moimMember2);
        MemberJpaEntity saveMoimMember3 = memberRepository.save(moimMember3);
        MemberJpaEntity saveMoimMember4 = memberRepository.save(moimMember4);

        List<Long> moimMemberIds = List.of(saveHost.getId(), saveMoimMember1.getId(),
                saveMoimMember2.getId(), saveMoimMember3.getId(), saveMoimMember4.getId());

        MoimJpaEntity moim = MoimJpaEntity.builder()
                .memberId(saveHost.getId())
                .build();

        MoimJpaEntity saveMoim = moimRepository.save(moim);

        JoinedMoimJpaEntity j1 = JoinedMoimJpaEntity.builder()
                .moimId(moim.getId())
                .memberId(host.getId())
                .build();
        JoinedMoimJpaEntity j2 = JoinedMoimJpaEntity.builder()
                .moimId(moim.getId())
                .memberId(saveMoimMember1.getId())
                .build();
        JoinedMoimJpaEntity j3 = JoinedMoimJpaEntity.builder()
                .moimId(moim.getId())
                .memberId(saveMoimMember2.getId())
                .build();
        JoinedMoimJpaEntity j4 = JoinedMoimJpaEntity.builder()
                .moimId(moim.getId())
                .memberId(saveMoimMember3.getId())
                .build();
        JoinedMoimJpaEntity j5 = JoinedMoimJpaEntity.builder()
                .moimId(moim.getId())
                .memberId(saveMoimMember4.getId())
                .build();

        joinedMoimRepository.save(j1);
        joinedMoimRepository.save(j2);
        joinedMoimRepository.save(j3);
        joinedMoimRepository.save(j4);
        joinedMoimRepository.save(j5);

        // when
        List<MoimMemberResponse> moimMembers = memberFinder.findMoimMembers(moimMemberIds, saveHost.getId(), saveMoim.getId());

        // then
        assertThat(moimMembers.size()).isEqualTo(5);
        moimMembers.forEach(moimMemberResponse -> {
            assertThat(moimMemberResponse.memberName()).isIn(names);
        });
    }

    @DisplayName("다른 모임에 참여한 멤버들을 제외하고 모임의 멤버 정보를 조회한다")
    @Test
    void findMoimMembersExcludeAnoterMoimMembers() {
        // given1
        List<String> joinNames = List.of("hostName", "member1", "member2");
        List<String> notJoinNames = List.of("anotherHostName", "member3", "member4");

        MemberJpaEntity host = MemberJpaEntity.builder()
                .username(joinNames.get(0))
                .build();
        MemberJpaEntity moimMember1 = MemberJpaEntity.builder()
                .username(joinNames.get(1))
                .build();
        MemberJpaEntity moimMember2 = MemberJpaEntity.builder()
                .username(joinNames.get(2))
                .build();

        MemberJpaEntity saveHost = memberRepository.save(host);
        MemberJpaEntity saveMoimMember1 = memberRepository.save(moimMember1);
        MemberJpaEntity saveMoimMember2 = memberRepository.save(moimMember2);

        // given2
        MemberJpaEntity moimMember3 = MemberJpaEntity.builder()
                .username(notJoinNames.get(0))
                .build();
        MemberJpaEntity moimMember4 = MemberJpaEntity.builder()
                .username(notJoinNames.get(1))
                .build();
        MemberJpaEntity moimMember5 = MemberJpaEntity.builder()
                .username(notJoinNames.get(2))
                .build();

        MemberJpaEntity saveAnotherHost = memberRepository.save(moimMember3);
        MemberJpaEntity saveMoimMember3 = memberRepository.save(moimMember4);
        MemberJpaEntity saveMoimMember4 = memberRepository.save(moimMember5);

        List<Long> expectMoimMemberIds = List.of(saveHost.getId(), saveMoimMember1.getId(),
                saveMoimMember2.getId());

        // given3
        MoimJpaEntity moim = MoimJpaEntity.builder()
                .memberId(saveHost.getId())
                .build();

        // given4
        MoimJpaEntity anotherMoim = MoimJpaEntity.builder()
                .memberId(saveHost.getId())
                .build();

        MoimJpaEntity saveMoim = moimRepository.save(moim);

        // given5
        JoinedMoimJpaEntity j1 = JoinedMoimJpaEntity.builder()
                .moimId(moim.getId())
                .memberId(host.getId())
                .build();
        JoinedMoimJpaEntity j2 = JoinedMoimJpaEntity.builder()
                .moimId(moim.getId())
                .memberId(saveMoimMember1.getId())
                .build();
        JoinedMoimJpaEntity j3 = JoinedMoimJpaEntity.builder()
                .moimId(moim.getId())
                .memberId(saveMoimMember2.getId())
                .build();

        // given6
        JoinedMoimJpaEntity j4 = JoinedMoimJpaEntity.builder()
                .moimId(anotherMoim.getId())
                .memberId(saveAnotherHost.getId())
                .build();
        JoinedMoimJpaEntity j5 = JoinedMoimJpaEntity.builder()
                .moimId(anotherMoim.getId())
                .memberId(saveMoimMember3.getId())
                .build();
        JoinedMoimJpaEntity j6 = JoinedMoimJpaEntity.builder()
                .moimId(anotherMoim.getId())
                .memberId(saveMoimMember4.getId())
                .build();
        JoinedMoimJpaEntity j7 = JoinedMoimJpaEntity.builder()
                .moimId(anotherMoim.getId())
                .memberId(saveMoimMember1.getId())
                .build();

        joinedMoimRepository.save(j1);
        joinedMoimRepository.save(j2);
        joinedMoimRepository.save(j3);
        joinedMoimRepository.save(j4);
        joinedMoimRepository.save(j5);
        joinedMoimRepository.save(j6);
        joinedMoimRepository.save(j7);

        // when
        List<MoimMemberResponse> moimMembers = memberFinder.findMoimMembers(expectMoimMemberIds, saveHost.getId(), saveMoim.getId());

        // then
        assertThat(moimMembers.size()).isEqualTo(3);
        moimMembers.forEach(moimMemberResponse -> {
            assertThat(moimMemberResponse.memberName()).isIn(joinNames);
            assertThat(moimMemberResponse.memberId()).isIn(expectMoimMemberIds);
        });
    }
}
