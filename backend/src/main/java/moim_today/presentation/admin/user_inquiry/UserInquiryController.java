package moim_today.presentation.admin.user_inquiry;

import moim_today.application.admin.user_inquiry.UserInquiryService;
import moim_today.domain.member.MemberSession;
import moim_today.dto.admin.user_inquiry.UserInquiryAnswerRequest;
import moim_today.dto.admin.user_inquiry.UserInquiryRequest;
import moim_today.dto.admin.user_inquiry.UserInquiryRespondRequest;
import moim_today.dto.admin.user_inquiry.UserInquiryResponse;
import moim_today.global.annotation.Login;
import moim_today.global.response.CollectionResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/api/admin/user-inquiry")
public class UserInquiryController {

    private final UserInquiryService userInquiryService;

    public UserInquiryController(UserInquiryService userInquiryService) {
        this.userInquiryService = userInquiryService;
    }

    @GetMapping
    public CollectionResponse<List<UserInquiryResponse>> getUserInquiries(@RequestParam final long universityId){
        return CollectionResponse.from(userInquiryService.getAllUserInquiryByUniversityId(universityId));
    }

    @PostMapping
    public void createUserInquiry(@Login final MemberSession memberSession,
                                  @RequestBody final UserInquiryRequest userInquiryRequest){
        userInquiryService.createUserInquiry(memberSession.id(), memberSession.universityId(),
                memberSession.departmentId(), userInquiryRequest);
    }

    @PatchMapping("/answer-complete")
    public void updateUserInquiryAnswer(@RequestBody UserInquiryAnswerRequest userInquiryAnswerRequest){
        userInquiryService.updateUserInquiryAnswer(userInquiryAnswerRequest);
    }

    @PostMapping("/response")
    public void respondUserInquiry(@RequestBody UserInquiryRespondRequest userInquiryRespondRequest){

    }
}
