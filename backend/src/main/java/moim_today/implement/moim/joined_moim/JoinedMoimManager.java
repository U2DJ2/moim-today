package moim_today.implement.moim.joined_moim;

import moim_today.global.annotation.Implement;
import moim_today.persistence.entity.moim.joined_moim.JoinedMoimJpaEntity;

import java.util.ArrayList;
import java.util.List;

@Implement
public class JoinedMoimManager {

    public List<Long> extractMemberIds(final List<JoinedMoimJpaEntity> joinedMoimJpaEntities){
        List<Long> memberIds = new ArrayList<>();
        joinedMoimJpaEntities.forEach(e -> memberIds.add(e.getMemberId()));
        return memberIds;
    }
}
