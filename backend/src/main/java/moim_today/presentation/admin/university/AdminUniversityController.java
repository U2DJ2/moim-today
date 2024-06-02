package moim_today.presentation.admin.university;

import moim_today.application.admin.university.AdminUniversityService;
import moim_today.domain.university.AdminSession;
import moim_today.dto.university.UniversityNameResponse;
import moim_today.global.annotation.Login;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/admin/universities")
@RestController
public class AdminUniversityController {

    private final AdminUniversityService adminUniversityService;

    public AdminUniversityController(AdminUniversityService adminUniversityService) {
        this.adminUniversityService = adminUniversityService;
    }

    @GetMapping("/university-name")
    public UniversityNameResponse adminUniversityName(@Login final AdminSession adminSession){
        return adminUniversityService.getUniversityName(adminSession.universityId());
    }
}
