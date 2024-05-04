package moim_today.dto.moim.moim;

import lombok.Builder;

@Builder
public record MoimMemberDeleteRequest (
        long moimId,
        long memberId
){
    public long moimId(){
        return moimId;
    }
    public long memberId(){
        return memberId;
    }
}
