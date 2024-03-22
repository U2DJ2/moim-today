package booki_today.persistence.entity.assemble.assemble;

import booki_today.domain.created_booki.AssembleType;
import booki_today.global.annotation.Association;
import booki_today.persistence.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Table(name = "assemble")
@Entity
public class AssembleJpaEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assemble_id")
    private long id;

    @Association
    private long memberId;

    @Association
    private long categoryId;

    private String title;

    private String contents;

    private int capacity;

    private int currentCount;

    private String place;

    private String imageUrl;

    private String password;

    private long views;

    private int attendanceNumber;

    @Enumerated(EnumType.STRING)
    private AssembleType assembleType;

    private LocalDateTime recruitmentStartDateTime;

    private LocalDateTime recruitmentEndDateTime;

    private LocalDateTime assembleStartDateTime;

    private LocalDateTime assembleEndDateTime;

    protected AssembleJpaEntity() {
    }

    @Builder
    private AssembleJpaEntity(final long memberId, final long categoryId, final String title,
                             final String contents, final int capacity, final int currentCount,
                             final String place, final String imageUrl, final String password,
                             final long views, final int attendanceNumber, final AssembleType assembleType,
                             final LocalDateTime recruitmentStartDateTime, final LocalDateTime recruitmentEndDateTime,
                             final LocalDateTime assembleStartDateTime, final LocalDateTime assembleEndDateTime) {
        this.memberId = memberId;
        this.categoryId = categoryId;
        this.title = title;
        this.contents = contents;
        this.capacity = capacity;
        this.currentCount = currentCount;
        this.place = place;
        this.imageUrl = imageUrl;
        this.password = password;
        this.views = views;
        this.attendanceNumber = attendanceNumber;
        this.assembleType = assembleType;
        this.recruitmentStartDateTime = recruitmentStartDateTime;
        this.recruitmentEndDateTime = recruitmentEndDateTime;
        this.assembleStartDateTime = assembleStartDateTime;
        this.assembleEndDateTime = assembleEndDateTime;
    }
}
