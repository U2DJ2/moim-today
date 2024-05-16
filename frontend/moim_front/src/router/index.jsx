// React
import { Routes, Route } from "react-router-dom";

import MainLayout from "../components/MainLayout";

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

// Page : Moim - Meeting Page
import MoimMeetingPage from "../pages/MoimMeetingPage";

// Components
import MoimJoinPage from "../pages/MoimJoinPage";

/**
 * Basic Router
 * @returns
 */
function Router() {
  return (
    <Routes>
      <Route element={<MainLayout />}>
        <Route path="/" element={<HomePage />} />
        <Route path="/join/:MoimId" element={<MoimJoinPage />} />
        <Route
          path="/join/meeting/:MoimId/:meetingId"
          element={<MoimMeetingPage />}
        />
        <Route path="/detailed/:MoimId" element={<MoimDetailPage />} />
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
