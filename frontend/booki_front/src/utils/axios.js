import axios from "axios";
import properties from "../config/properties";
// import { handleHttpError } from "./error";

const fetchWrap = async ({ method, url, body, auth }) => {
  try {
    const config = {
      baseURL: properties.baseURL,
      withCredentials: true,
    };

    if (auth === true) {
      if (url === "/refresh-token")
        config.headers = {
          "REFRESH-TOKEN": `Bearer ${localStorage.getItem("refreshToken")}`,
        };
      else
        config.headers = {
          "ACCESS-TOKEN": `Bearer ${localStorage.getItem("accessToken")}`,
        };
    }

    const { data } =
      (method === "get" && (await axios.get(url, config))) ||
      (method === "post" && (await axios.post(url, body, config))) ||
      (method === "put" && (await axios.put(url, body, config))) ||
      (method === "delete" && (await axios.delete(url, config))) ||
      {};

    return data;
  } catch (error) {
    // handleHttpError(error);
    throw error;
  }
};

export const GET = (url, auth = false) =>
  fetchWrap({ method: "get", url, auth });

export const POST = (url, body, auth = false) =>
  fetchWrap({ method: "post", url, body, auth });

export const PUT = (url, body, auth = false) =>
  fetchWrap({ method: "put", url, body, auth });

export const DELETE = (url, auth = false) =>
  fetchWrap({ method: "delete", url, auth });
