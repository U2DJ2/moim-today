// React
import { Routes, Route } from "react-router-dom";

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
import Calendar from "../components/Calendar";
import TimeTable from "../pages/RegisterPage/TimeTable";
import MoimJoinPage from "../pages/MoimJoinPage";

/**
 * Basic Router
 * @returns
 */
function Router() {
  return (
    <Routes>
      <Route path="/" element={<HomePage />} />
      <Route path="/login" element={<LoginPage />} />
      <Route path="/register" element={<RegisterPage />} />
      <Route path="/creation" element={<MoimCreationPage />} />
      <Route path="/manage" element={<Manage />} />
      <Route path="/calendar" element={<Calendar />} />
      <Route path="/schedule" element={<Schedule />} />
      <Route path="/detailed/:MoimId" element={<MoimDetailPage />} />
      <Route path="/join/:MoimId" element={<MoimJoinPage />} />
    </Routes>
  );
}

export default Router;
