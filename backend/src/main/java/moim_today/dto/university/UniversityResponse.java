package moim_today.dto.university;

import lombok.Builder;
import moim_today.persistence.entity.university.UniversityJpaEntity;

@Builder
public record UniversityResponse(
    long universityId,
    String universityName,
    String universityEmail
){
    public static UniversityResponse of(final UniversityJpaEntity universityJpaEntity){
        return UniversityResponse.builder()
                .universityId(universityJpaEntity.getId())
                .universityName(universityJpaEntity.getUniversityName())
                .universityEmail(universityJpaEntity.getUniversityEmail())
                .build();
    }
}