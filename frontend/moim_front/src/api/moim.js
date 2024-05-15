import axios from "axios";

export const fetchNotices = async (moimId) => {
  const result = await axios.get(
    "https://api.moim.today/api/moims/notices/simple",
    {
      params: {
        moimId: moimId,
      },
    }
  );
  return result;
};

export const fetchMoimInfo = async (moimId) => {
  const result = await axios.get(
    `https://api.moim.today/api/moims/detail/${moimId}`
  );
  return result;
};

export const fetchMeetings = async (moimId, meetingStatus) => {
  const result = await axios.get(
    `https://api.moim.today/api/meetings/${moimId}`,
    {
      params: {
        meetingStatus: meetingStatus,
      },
    }
  );
  return result;
};
