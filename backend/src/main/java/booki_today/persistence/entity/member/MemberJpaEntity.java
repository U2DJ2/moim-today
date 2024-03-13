package booki_today.persistence.entity.member;

import booki_today.domain.member.Gender;
import booki_today.domain.member.LoginType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Table(name = "member")
@Entity
public class MemberJpaEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String loginId;

    private String password;

    private String email;

    private String username;

    private String nickname;

    private String uniqueName;

    private String userProfileImageUrl;

    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate birthDate;

    protected MemberJpaEntity() {
    }

    @Builder
    private MemberJpaEntity(final String loginId, final String password, final String email,
                            final String username, final String nickname, final String uniqueName,
                            final String userProfileImageUrl, final LoginType loginType,
                            final Gender gender, final LocalDate birthDate) {
        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.username = username;
        this.nickname = nickname;
        this.uniqueName = uniqueName;
        this.userProfileImageUrl = userProfileImageUrl;
        this.loginType = loginType;
        this.gender = gender;
        this.birthDate = birthDate;
    }
}
