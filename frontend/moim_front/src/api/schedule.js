import { GET } from "../utils/axios";

export const getMemberSchedule = async (memberId) => {
  try {
    const response = await GET(
      `api/schedules/weekly/available-time/members/${memberId}`
    );
    return response;
  } catch (e) {
    console.log(e);
  }
};
