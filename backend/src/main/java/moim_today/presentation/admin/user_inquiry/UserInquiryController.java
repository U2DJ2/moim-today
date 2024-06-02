package moim_today.presentation.admin.user_inquiry;

import moim_today.application.admin.user_inquiry.UserInquiryService;
import moim_today.domain.university.AdminSession;
import moim_today.dto.admin.user_inquiry.UserInquiryAnswerRequest;
import moim_today.dto.admin.user_inquiry.UserInquiryRespondRequest;
import moim_today.dto.admin.user_inquiry.UserInquiryResponse;
import moim_today.global.annotation.Login;
import moim_today.global.response.CollectionResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/admin/user-inquiry")
@RestController
public class UserInquiryController {

    private final UserInquiryService userInquiryService;

    public UserInquiryController(UserInquiryService userInquiryService) {
        this.userInquiryService = userInquiryService;
    }

    @GetMapping
    public CollectionResponse<List<UserInquiryResponse>> getUserInquiries(@Login final AdminSession adminSession){
        return CollectionResponse.from(userInquiryService.getAllUserInquiry(adminSession.universityId()));
    }

    @GetMapping("/not-answered")
    public CollectionResponse<List<UserInquiryResponse>> getNotAnsweredUserInquiries(@Login final AdminSession adminSession){
        return CollectionResponse.from(userInquiryService.getAllNotAnsweredUserInquiry(adminSession.universityId()));
    }

    @PatchMapping("/answer-complete")
    public void updateUserInquiryAnswer(@RequestBody final UserInquiryAnswerRequest userInquiryAnswerRequest){
        userInquiryService.updateUserInquiryAnswer(userInquiryAnswerRequest);
    }

    @PostMapping("/response")
    public void respondUserInquiry(@RequestBody final UserInquiryRespondRequest userInquiryRespondRequest){
        userInquiryService.respondUserInquiry(userInquiryRespondRequest);
    }
}
