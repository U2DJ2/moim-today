package moim_today.persistence.entity.admin;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import moim_today.global.annotation.Association;
import moim_today.global.base_entity.BaseTimeEntity;

@Getter
@Table(name = "user_inquiry")
@Entity
public class UserInquiryJpaEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_inquiry_id")
    private long id;

    @Association
    private long memberId;

    @Association
    private long universityId;

    @Association
    private long departmentId;

    private String inquiryTitle;

    private String inquiryContent;

    private boolean answerComplete;

    protected UserInquiryJpaEntity() {
    }

    @Builder
    public UserInquiryJpaEntity(final long memberId,
                                final long universityId,
                                final long departmentId,
                                final String inquiryContent,
                                final String inquiryTitle,
                                final boolean answerComplete) {
        this.memberId = memberId;
        this.universityId = universityId;
        this.departmentId = departmentId;
        this.inquiryTitle = inquiryTitle;
        this.inquiryContent = inquiryContent;
        this.answerComplete = answerComplete;
    }

    public void updateAnswerComplete(final boolean answerComplete){
        this.answerComplete = answerComplete;
    }
}