import axios from "axios";
import { POST } from "../utils/axios";

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

export const fetchMembers = async (moimId) => {
  const result = await axios.get(
    `https://api.moim.today/api/moims/members/${moimId}`
  );
  return result;
};

export const postMeeting = async (data) => {
  const response = await POST("api/meetings", data);
};

export const fetchWriter = async (MoimId) => {
  try {
    const response = await axios.get(
      `https://api.moim.today/api/members/host-profile/${MoimId}`
    );
    return response.data;
  } catch (e) {
    console.log(e);
  }
};