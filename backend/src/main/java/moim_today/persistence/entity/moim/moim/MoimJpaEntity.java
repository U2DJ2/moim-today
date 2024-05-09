package moim_today.persistence.entity.moim.moim;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import moim_today.domain.moim.DisplayStatus;
import moim_today.domain.moim.enums.MoimCategory;
import moim_today.dto.moim.moim.MoimUpdateRequest;
import moim_today.global.annotation.Association;
import moim_today.global.base_entity.BaseTimeEntity;
import moim_today.global.error.ForbiddenException;

import java.time.LocalDate;

import static moim_today.global.constant.MoimConstant.DEFAULT_MOIM_IMAGE_URL;
import static moim_today.global.constant.MoimConstant.DEFAULT_MOIM_PASSWORD;
import static moim_today.global.constant.exception.MoimExceptionConstant.MOIM_HOST_ERROR;
import static moim_today.global.constant.exception.MoimExceptionConstant.ORGANIZER_FORBIDDEN_ERROR;

@Getter
@Table(name = "moim")
@Entity
public class MoimJpaEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "moim_id")
    private long id;

    @Association
    private long universityId;

    @Association
    private long memberId;

    private String title;

    private String contents;

    private int capacity;

    private int currentCount;

    private String imageUrl;

    private String password;

    @Enumerated(EnumType.STRING)
    private MoimCategory moimCategory;

    @Enumerated(EnumType.STRING)
    private DisplayStatus displayStatus;

    private int views;

    private LocalDate startDate;

    private LocalDate endDate;

    protected MoimJpaEntity() {
    }

    @Builder
    private MoimJpaEntity(final long universityId, final long memberId, final String title,
                          final String contents, final int capacity, final int currentCount,
                          final String imageUrl, final String password, final MoimCategory moimCategory,
                          final DisplayStatus displayStatus, final int views,
                          final LocalDate startDate, final LocalDate endDate) {
        this.universityId = universityId;
        this.memberId = memberId;
        this.title = title;
        this.contents = contents;
        this.capacity = capacity;
        this.currentCount = currentCount;
        this.imageUrl = imageUrl;
        this.password = password;
        this.moimCategory = moimCategory;
        this.displayStatus = displayStatus;
        this.views = views;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void validateHostMember(final long memberId) {
        if (this.memberId != memberId) {
            throw new ForbiddenException(ORGANIZER_FORBIDDEN_ERROR.message());
        }
    }

    public void validateNotHostMember(final long requestMemberId) {
        if (this.memberId == requestMemberId) {
            throw new ForbiddenException(MOIM_HOST_ERROR.message());
        }
    }

    public void updateMoim(final MoimUpdateRequest moimUpdateRequest) {
        this.title = moimUpdateRequest.title();
        this.contents = moimUpdateRequest.contents();
        this.capacity = moimUpdateRequest.capacity();
        this.moimCategory = moimUpdateRequest.moimCategory();
        this.startDate = moimUpdateRequest.startDate();
        this.endDate = moimUpdateRequest.endDate();
        this.displayStatus = moimUpdateRequest.displayStatus();

        updatePasswordByDisplayStatus(moimUpdateRequest.password());
        updateImageUrl(moimUpdateRequest.imageUrl());
    }

    public void updateCurrentCount(final int plusCount){
        this.currentCount += plusCount;
    }

    private void updateImageUrl(final String updateImageUrl) {
        if (updateImageUrl == null) {
            this.imageUrl = DEFAULT_MOIM_IMAGE_URL.value();
        } else {
            this.imageUrl = updateImageUrl;
        }
    }

    private void updatePasswordByDisplayStatus(final String updatePassword) {
        if (displayStatus.equals(DisplayStatus.PUBLIC)) {
            this.password = DEFAULT_MOIM_PASSWORD.value();
        } else {
            if (updatePassword != null) {
                this.password = updatePassword;
            }
        }
    }
}
