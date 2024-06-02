package moim_today.persistence.entity.member;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import moim_today.domain.member.enums.Gender;
import moim_today.dto.member.ProfileUpdateRequest;
import moim_today.global.annotation.Association;
import moim_today.global.base_entity.BaseTimeEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.UUID;

import static moim_today.global.constant.MemberConstant.*;
import static moim_today.global.constant.NumberConstant.*;

@Getter
@Table(name = "member")
@Entity
public class MemberJpaEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private long id;

    @Association
    private long universityId;

    @Association
    private long departmentId;

    private String email;

    private String password;

    private String username;

    private String studentId;

    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String memberProfileImageUrl;

    protected MemberJpaEntity() {
    }

    @Builder
    private MemberJpaEntity(final long universityId, final long departmentId, final String email,
                            final String password, final String username, final String studentId,
                            final LocalDate birthDate, final Gender gender, final String memberProfileImageUrl) {
        this.universityId = universityId;
        this.departmentId = departmentId;
        this.email = email;
        this.password = password;
        this.username = username;
        this.studentId = studentId;
        this.birthDate = birthDate;
        this.gender = gender;
        this.memberProfileImageUrl = memberProfileImageUrl;
    }

    public void updatePassword(final PasswordEncoder passwordEncoder, final String newPassword) {
        this.password = passwordEncoder.encode(newPassword);
    }

    public void updateProfile(final ProfileUpdateRequest profileUpdateRequest) {
        this.memberProfileImageUrl = profileUpdateRequest.imageUrl();
    }

    public void changeToUnknownMember() {
        this.email = null;
        this.password = UUID.randomUUID().toString()
                .substring(DELETED_MEMBER_PASSWORD_START_POINT.value(), DELETED_MEMBER_PASSWORD_LENGTH.value());
        this.username = DELETED_MEMBER_USERNAME.value();
        this.studentId = DELETED_MEMBER_STUDENT_ID.value();
        this.gender = Gender.UNKNOWN;
        this.memberProfileImageUrl = DEFAULT_PROFILE_URL.value();
        this.birthDate = LocalDate.of(
                DELETED_MEMBER_BIRTH_YEAR.value(),
                DELETED_MEMBER_BIRTH_MONTH.value(),
                DELETED_MEMBER_BIRTH_DAY.value()
        );
    }
}
