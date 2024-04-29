import { GET, POST, PUT } from "../utils/axios";

export const checkEmailValid = async (data) =>
  await POST("/api/certification/email", data);
