import { Routes, Route } from "react-router-dom";
import HomePage from "../pages/Home";
import LoginPage from "../pages/LoginPage";
function Router() {
  return (
    <Routes>
      <Route path="/" element={<HomePage />} />
      <Route path="/login" element={<LoginPage />} />
    </Routes>
  );
}

export default Router;
