package moim_today.dto.university;

import lombok.Builder;
import moim_today.persistence.entity.university.UniversityJpaEntity;

@Builder
public record UniversityInfoResponse (
        long universityId,
        String name,
        String universityEmail
){

    public static UniversityInfoResponse of(UniversityJpaEntity universityJpaEntity){
        return UniversityInfoResponse.builder()
                .universityId(universityJpaEntity.getId())
                .name(universityJpaEntity.getUniversityName())
                .universityEmail(universityJpaEntity.getUniversityEmail())
                .build();
    }
}