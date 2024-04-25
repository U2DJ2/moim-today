// React
import { Routes, Route } from "react-router-dom";

// Pages : Home
import HomePage from "../pages/Home/Home";
import MoimCreationPage from "../pages/Home/MoimCreation";

// Pages : Authentication
import LoginPage from "../pages/Authentication/Login";

// Page : Manage
import Manage from "../pages/Manage/Manage";


/**
 * Basic Router
 * @returns
 */
function Router() {
  return (
    <Routes>
      <Route path="/" element={<HomePage />} />
      <Route path="/login" element={<LoginPage />} />
      <Route path="/creation" element={<MoimCreationPage />} />
      <Route path="/profile" element={<Manage />} />
    </Routes>
  );
}

export default Router;