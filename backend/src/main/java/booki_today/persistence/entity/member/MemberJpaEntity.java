package booki_today.persistence.entity.member;

import booki_today.domain.member.Gender;
import booki_today.global.annotation.Association;
import booki_today.persistence.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Table(name = "member")
@Entity
public class MemberJpaEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Association
    private Long universityId;

    private String loginId;

    private String password;

    private String email;

    private String username;

    private String nickname;

    private String uniqueName;

    private String userProfileImageUrl;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate birthDate;

    protected MemberJpaEntity() {
    }

    @Builder
    private MemberJpaEntity(final Long universityId, final String loginId, final String password,
                            final String email, final String username, final String nickname, final String uniqueName,
                            final String userProfileImageUrl, final Gender gender, final LocalDate birthDate) {
        this.universityId = universityId;
        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.username = username;
        this.nickname = nickname;
        this.uniqueName = uniqueName;
        this.userProfileImageUrl = userProfileImageUrl;
        this.gender = gender;
        this.birthDate = birthDate;
    }
}
