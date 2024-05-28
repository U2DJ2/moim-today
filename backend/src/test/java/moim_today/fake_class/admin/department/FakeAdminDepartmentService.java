package moim_today.fake_class.admin.department;

import moim_today.application.admin.department.AdminDepartmentService;
import moim_today.dto.department.ApproveRequestDepartmentRequest;
import moim_today.dto.department.RequestDepartmentResponse;
import moim_today.util.TestConstant;

import java.util.List;

public class FakeAdminDepartmentService implements AdminDepartmentService {

    @Override
    public List<RequestDepartmentResponse> findAll() {
        RequestDepartmentResponse requestDepartmentResponse1 = RequestDepartmentResponse.builder()
                .requestDepartmentId(1)
                .universityId(TestConstant.UNIV_ID.longValue())
                .requestDepartmentName("소프트웨어학과")
                .build();

        RequestDepartmentResponse requestDepartmentResponse2 = RequestDepartmentResponse.builder()
                .requestDepartmentId(1)
                .universityId(TestConstant.UNIV_ID.longValue())
                .requestDepartmentName("물리학과")
                .build();

        RequestDepartmentResponse requestDepartmentResponse3 = RequestDepartmentResponse.builder()
                .requestDepartmentId(1)
                .universityId(TestConstant.UNIV_ID.longValue())
                .requestDepartmentName("기계공학과")
                .build();

        return List.of(requestDepartmentResponse1, requestDepartmentResponse2, requestDepartmentResponse3);
    }

    @Override
    public void approveRequest(final ApproveRequestDepartmentRequest approveRequestDepartmentRequest) {

    }
}
