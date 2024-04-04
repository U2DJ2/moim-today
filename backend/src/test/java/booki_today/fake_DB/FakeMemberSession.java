package booki_today.fake_DB;

import moim_today.domain.member.MemberSession;

public class FakeMemberSession {

    public static MemberSession createMemberSession(){
        return new MemberSession(1L,1L,1L,"test","testUrl");
    }
}
