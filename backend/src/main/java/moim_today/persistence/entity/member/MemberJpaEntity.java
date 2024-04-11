package moim_today.persistence.entity.member;

import moim_today.domain.member.enums.Gender;
import moim_today.global.annotation.Association;
import moim_today.global.base_entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

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
}
