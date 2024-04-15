package moim_today.dto.university;

import lombok.Builder;
import moim_today.persistence.entity.university.UniversityJpaEntity;

@Builder
public record UniversityInfoResponse (
    long universityId,
    String universityName,
    String universityEmail
){
    public static UniversityInfoResponse of(final UniversityJpaEntity universityJpaEntity){
        return UniversityInfoResponse.builder()
                .universityId(universityJpaEntity.getId())
                .universityName(universityJpaEntity.getUniversityName())
                .universityEmail(universityJpaEntity.getUniversityEmail())
                .build();
    }
}