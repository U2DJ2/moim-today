import axios from "axios";
import React, { useEffect, useState } from "react";
import { useParams } from "react-router";

function NoticeDetailPage() {
  const { moimNoticeId } = useParams();
  const [noticeInfo, setNoticeInfo] = useState([]);
  const getNoticeInfo = async () => {
    try {
      const result = await axios.get(
        "https://api.moim.today/api/moims/notices/detail",
        {
          params: {
            moimNoticeId: moimNoticeId,
          },
        }
      );
      console.log(result);
      setNoticeInfo(result.data);
    } catch (e) {
      console.log(e);
    }
  };
  useEffect(() => {
    getNoticeInfo();
  }, []);
  return (
    <div>
      <div className="flex flex-col gap-4">
        <div className=" font-Pretendard_Black text-4xl">
          {noticeInfo.title}
        </div>
        <div className="flex gap-4 pb-4">
          <div className="font-Pretendard_Light">
            생성일 : {noticeInfo.createdAt}
          </div>
          <div>|</div>
          <div className="font-Pretendard_Light">
            마지막으로 수정된 일자 : {noticeInfo.lastModifiedAt}
          </div>
        </div>
      </div>
      <hr className="pb-4" />
      <div className="font-Pretendard_Normal">{noticeInfo.contents}</div>
    </div>
  );
}

export default NoticeDetailPage;
