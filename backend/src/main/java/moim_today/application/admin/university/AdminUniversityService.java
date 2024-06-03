package moim_today.application.admin.university;

import moim_today.dto.university.UniversityNameResponse;
import moim_today.persistence.entity.university.UniversityJpaEntity;
import moim_today.persistence.repository.university.UniversityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AdminUniversityService {

    private final UniversityRepository universityRepository;

    public AdminUniversityService(UniversityRepository universityRepository) {
        this.universityRepository = universityRepository;
    }

    @Transactional(readOnly = true)
    public UniversityNameResponse getUniversityName(final long universityId) {
        UniversityJpaEntity university = universityRepository.findById(universityId).get();
        return new UniversityNameResponse(university.getUniversityName());
    }
}
