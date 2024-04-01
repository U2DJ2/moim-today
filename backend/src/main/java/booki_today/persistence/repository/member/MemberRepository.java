package booki_today.persistence.repository.member;

import booki_today.persistence.entity.member.MemberJpaEntity;

public interface MemberRepository {
    void save(MemberJpaEntity entity);
    MemberJpaEntity getByEmail(final String email);
}
