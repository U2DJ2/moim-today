import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import { auth, login, refresh } from "../../api/users";

const initialState = {
  auth: false,
  userId: 0,
  loginId: "",
  email: "",
  name: "",
  birthday: "",
  country: "",
  gender: "",
  nickname: "",
  phoneNumber: "",
  role: "",
  introduction: "",
  profileImg: "",
  isAdmin: false,
  suspended: false,
  deleted: false,
};

const __asyncLogin = createAsyncThunk(
  "userSlice/asyncLogin",
  async (payload) => {
    try {
      const result = await login(payload);

      localStorage.setItem("accessToken", result.payload.accessToken);
      localStorage.setItem("refreshToken", result.payload.refreshToken);
      return true;
    } catch {
      return false;
    }
  }
);

const __asyncAuth = createAsyncThunk("userSlice/asyncAuth", async () => {
  try {
    const auth_result = await auth();
    return { ...auth_result.payload, auth: true };
  } catch {
    try {
      const refresh_result = await refresh();
      localStorage.setItem("accessToken", refresh_result.payload.accessToken);
      localStorage.setItem("refreshToken", refresh_result.payload.refreshToken);

      const auth_result = await auth();
      return { ...auth_result.payload, auth: true };
    } catch {
      localStorage.removeItem("accessToken");
      localStorage.removeItem("refreshToken");

      return { auth: false };
    }
  }
});

const userSlice = createSlice({
  name: "user",
  initialState,
  reducers: {
    logout: (state) => {
      localStorage.removeItem("accessToken");
      localStorage.removeItem("refreshToken");
      state.auth = false;
      state.userId = 0;
      state.loginId = "";
      state.email = "";
      state.name = "";
      state.birthday = "";
      state.country = "";
      state.gender = "";
      state.nickname = "";
      state.phoneNumber = "";
      state.role = "";
      state.introduction = "";
      state.profileImg = "";
      state.isAdmin = false;
      state.suspended = false;
      state.deleted = false;
    },
  },
  extraReducers: (builder) => {
    builder.addCase(__asyncAuth.fulfilled, (state, payload) => {
      state.auth = payload.payload.auth;
      state.userId = payload.payload.userId;
      state.loginId = payload.payload.loginId;
      state.email = payload.payload.email;
      state.name = payload.payload.name;
      state.birthday = payload.payload.birthday;
      state.country = payload.payload.country;
      state.gender = payload.payload.gender;
      state.nickname = payload.payload.nickname;
      state.phoneNumber = payload.payload.phoneNumber;
      state.role = payload.payload.role;
      state.introduction = payload.payload.introduction;
      state.profileImg = payload.payload.profileImg;
      state.isAdmin = payload.payload.role === "ROLE_ADMIN";
      state.suspended = payload.payload.suspended;
      state.deleted = payload.payload.deleted;
    });
  },
});

export default userSlice;
export const { logout } = userSlice.actions;
export { __asyncLogin, __asyncAuth };
