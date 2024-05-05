package moim_today.dto.university;

import lombok.Builder;
import moim_today.persistence.entity.university.UniversityJpaEntity;

@Builder
public record UniversityDetailResponse(
    long universityId,
    String universityName,
    String universityEmail
){
    public static UniversityDetailResponse of(final UniversityJpaEntity universityJpaEntity){
        return UniversityDetailResponse.builder()
                .universityId(universityJpaEntity.getId())
                .universityName(universityJpaEntity.getUniversityName())
                .universityEmail(universityJpaEntity.getUniversityEmail())
                .build();
    }
}