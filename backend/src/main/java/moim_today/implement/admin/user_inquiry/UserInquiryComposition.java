package moim_today.implement.admin.user_inquiry;

import moim_today.dto.admin.user_inquiry.UserInquiryResponse;
import moim_today.global.annotation.Implement;
import moim_today.persistence.entity.admin.UserInquiryJpaEntity;

import java.util.List;


@Implement
public class UserInquiryComposition {

    private final UserInquiryAppender userInquiryAppender;
    private final UserInquiryFinder userInquiryFinder;

    public UserInquiryComposition(final UserInquiryAppender userInquiryAppender,
                                  final UserInquiryFinder userInquiryFinder) {
        this.userInquiryAppender = userInquiryAppender;
        this.userInquiryFinder = userInquiryFinder;
    }

    public void createUserInquiry(final UserInquiryJpaEntity userInquiryJpaEntity) {
        userInquiryAppender.createUserInquiry(userInquiryJpaEntity);
    }

    public List<UserInquiryResponse> getAllUserInquiry(final long universityId) {
        return userInquiryFinder.getAllUserInquiry(universityId);
    }

    public List<UserInquiryResponse> getAllNotAnsweredUserInquiry(final long universityId) {
        return userInquiryFinder.getAllNotAnsweredUserInquiry(universityId);
    }

    public String makeResponseSubject(final long userInquiryId) {
        return userInquiryFinder.makeResponseSubject(userInquiryId);
    }

    public UserInquiryJpaEntity getById(final long userInquiryId) {
        return userInquiryFinder.getById(userInquiryId);
    }
}
