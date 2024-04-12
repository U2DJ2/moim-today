package moim_today.implement.department;

import moim_today.global.annotation.Implement;
import moim_today.global.error.BadRequestException;
import moim_today.persistence.repository.department.DepartmentRepository;
import moim_today.persistence.repository.university.UniversityRepository;

import java.util.List;

import static moim_today.global.constant.exception.UniversityExceptionConstant.UNIVERSITY_SEARCH_CONDITIONS_INSUFFICIENT;

@Implement
public class DepartmentFinder {

    private final DepartmentRepository departmentRepository;
    private final UniversityRepository universityRepository;

    public DepartmentFinder(final DepartmentRepository departmentRepository, final UniversityRepository universityRepository) {
        this.departmentRepository = departmentRepository;
        this.universityRepository = universityRepository;
    }

    public List<String> getAllDepartment(final long universityId, final String universityName){
        if(universityId != -1){
            return getAllDepartmentByUniversityId(universityId);
        }
        if(!universityName.isEmpty()){
            return getAllDepartmentByUniversityName(universityName);
        }
        throw new BadRequestException(UNIVERSITY_SEARCH_CONDITIONS_INSUFFICIENT.message());
    }

    private List<String> getAllDepartmentByUniversityId(final long universityId) {
        return departmentRepository.findAllDepartmentOfUniversity(universityId);
    }

    private List<String> getAllDepartmentByUniversityName(final String universityName) {
        long universityId = universityRepository.getByName(universityName).getId();
        return departmentRepository.findAllDepartmentOfUniversity(universityId);
    }
}
