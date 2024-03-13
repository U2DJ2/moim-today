package booki_today.persistence.entity.created_booki;

import booki_today.domain.created_booki.BookiType;
import booki_today.global.annotation.Association;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Table(name = "created_booki")
@Entity
public class CreatedBookiJpaEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "created_booki_id")
    private Long id;

    @Association
    private Long memberId;

    private String title;

    private String contents;

    private int capacity;

    private int currentCount;

    private String place;

    private String imageUrl;

    private String password;

    private long views;

    private int attendanceNumber;

    private LocalDateTime eventStartDateTime;

    private LocalDateTime eventEndDateTime;

    private LocalDateTime bookiStartDateTime;

    private LocalDateTime bookiEndDateTime;

    private LocalDateTime expiredDateTime;

    @Enumerated(EnumType.STRING)
    private BookiType bookiType;

    protected CreatedBookiJpaEntity() {
    }

    @Builder
    private CreatedBookiJpaEntity(final Long memberId, final String title, final String contents, final int capacity,
                                  final int currentCount, final String place, final String imageUrl,
                                  final String password, final long views, final int attendanceNumber,
                                  final LocalDateTime eventStartDateTime, final LocalDateTime eventEndDateTime,
                                  final LocalDateTime bookiStartDateTime, final LocalDateTime bookiEndDateTime,
                                  final LocalDateTime expiredDateTime, final BookiType bookiType) {
        this.memberId = memberId;
        this.title = title;
        this.contents = contents;
        this.capacity = capacity;
        this.currentCount = currentCount;
        this.place = place;
        this.imageUrl = imageUrl;
        this.password = password;
        this.views = views;
        this.attendanceNumber = attendanceNumber;
        this.eventStartDateTime = eventStartDateTime;
        this.eventEndDateTime = eventEndDateTime;
        this.bookiStartDateTime = bookiStartDateTime;
        this.bookiEndDateTime = bookiEndDateTime;
        this.expiredDateTime = expiredDateTime;
        this.bookiType = bookiType;
    }
}
