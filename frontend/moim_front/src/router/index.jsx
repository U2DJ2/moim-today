// React
import { useEffect } from "react";
import { Routes, Route, useNavigate } from "react-router-dom";

// Pages : Home
import HomePage from "../pages/Home/Home";
import MoimCreationPage from "../pages/Home/MoimCreation";

// Pages : Authentication
import LoginPage from "../pages/Login";

// Page : Manage
import Manage from "../pages/Manage/Manage";
import RegisterPage from "../pages/RegisterPage";
import Schedule from "../pages/Schedule";

// Page : Moim Detail Page
import MoimDetailPage from "../pages/MoimDetailPage";

// Components
import MoimJoinPage from "../pages/MoimJoinPage";
import MeetingCreation from "../pages/MoimJoinPage/MoimHome/MeetingCreation";

// API
import axios from "axios";

// Layout
import MainLayout from "../components/MainLayout";

/**
 * Basic Router
 * @returns
 */
function Router() {
  const navigate = useNavigate();

  // useEffect(() => {
  //   const checkSession = async () => {
  //     try {
  //       const response = await axios.get(
  //         "https://api.moim.today/api/session-validation"
  //       );

  //       if (!response.data.isValidateMemberSession) {
  //         navigate("/login");
  //       }
  //     } catch (error) {
  //       navigate("/login");
  //     }
  //   };

  //   const pathname = window.location.pathname;
  //   // register 페이지에 대한 예외처리
  //   if (pathname !== "/register") {
  //     checkSession();
  //   }
  // }, [navigate]);

  return (
    <Routes>
      <Route element={<MainLayout />}>
        <Route path="/" element={<HomePage />} />
        <Route path="/join/:MoimId" element={<MoimJoinPage />} />

        <Route path="/detailed/:MoimId" element={<MoimDetailPage />} />
        <Route path="/meeting/:MoimId" element={<MeetingCreation />} />
      </Route>
      <Route path="/login" element={<LoginPage />} />
      <Route path="/register" element={<RegisterPage />} />
      <Route path="/creation" element={<MoimCreationPage />} />
      <Route path="/manage" element={<Manage />} />
      <Route path="/schedule" element={<Schedule />} />
    </Routes>
  );
}

export default Router;
