package moim_today.persistence.entity.moim.moim;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import moim_today.domain.moim.enums.MoimCategory;
import moim_today.dto.moim.moim.MoimUpdateRequest;
import moim_today.global.annotation.Association;
import moim_today.global.base_entity.BaseTimeEntity;
import moim_today.global.error.BadRequestException;
import moim_today.global.error.ForbiddenException;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static moim_today.global.constant.MoimConstant.DEFAULT_MOIM_IMAGE_URL;
import static moim_today.global.constant.NumberConstant.VIEW_COUNT_OF_ONE;
import static moim_today.global.constant.exception.MeetingExceptionConstant.MEETING_DATE_TIME_BAD_REQUEST_ERROR;
import static moim_today.global.constant.exception.MoimExceptionConstant.*;

@Getter
@Table(name = "moim")
@Entity
public class MoimJpaEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Enumerated(EnumType.STRING)
    private MoimCategory moimCategory;

    private int views;

    private LocalDate startDate;

    private LocalDate endDate;

    protected MoimJpaEntity() {
    }

    @Builder
    private MoimJpaEntity(final long universityId, final long memberId, final String title,
                          final String contents, final int capacity, final int currentCount,
                          final String imageUrl, final MoimCategory moimCategory,
                          final int views, final LocalDate startDate, final LocalDate endDate) {
        this.universityId = universityId;
        this.memberId = memberId;
        this.title = title;
        this.contents = contents;
        this.capacity = capacity;
        this.currentCount = currentCount;
        this.imageUrl = imageUrl;
        this.moimCategory = moimCategory;
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

    public void validateMoimNotEnd(final LocalDate curDate){
        if(endDate.isBefore(curDate)){
            throw new BadRequestException(MOIM_AFTER_END_ERROR.message());
        }
    }

    public void validateDateTime(final LocalDateTime meetingStartDateTime, final LocalDateTime meetingEndDateTime) {
        LocalDate meetingStartDate = meetingStartDateTime.toLocalDate();
        LocalDate meetingEndDate = meetingEndDateTime.toLocalDate();

        if (this.startDate.isAfter(meetingStartDate) || this.endDate.isBefore(meetingEndDate)) {
            throw new BadRequestException(MEETING_DATE_TIME_BAD_REQUEST_ERROR.message());
        }
    }

    public void updateMoim(final MoimUpdateRequest moimUpdateRequest) {
        this.title = moimUpdateRequest.title();
        this.contents = moimUpdateRequest.contents();
        this.capacity = moimUpdateRequest.capacity();
        this.moimCategory = moimUpdateRequest.moimCategory();
        this.startDate = moimUpdateRequest.startDate();
        this.endDate = moimUpdateRequest.endDate();

        updateImageUrl(moimUpdateRequest.imageUrl());
    }

    public void addToCurrentCount(final int plusCount){
        this.currentCount += plusCount;
    }

    private void updateImageUrl(final String updateImageUrl) {
        if (updateImageUrl == null || updateImageUrl.isBlank()) {
            this.imageUrl = DEFAULT_MOIM_IMAGE_URL.value();
        } else {
            this.imageUrl = updateImageUrl;
        }
    }

    public void updateMoimViews() {
        this.views += VIEW_COUNT_OF_ONE.value();
    }

    public boolean checkVacancy(){
        return currentCount < capacity;
    }
}
