import { GET, POST, PUT } from "../utils/axios";
import properties from "../config/properties";
import axios from "axios";

export const checkEmailValid = async (data) =>
  await axios.post("https://api.moim.today/api/certification/email", data, {
    headers: {
      "Content-Type": "application/json",
    },
    properties: {
      baseURL: properties.baseURL,
    },
  });

export const checkWriter = async (moimId) => {
  const result = await axios.get(
    `https://api.moim.today/api/members/${moimId}/hosts`
  );
  return result;
};
