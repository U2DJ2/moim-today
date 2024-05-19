import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { __asyncAuth } from "../redux/modules/userSlice";
import Loading from "../components/Loading";

export default function Auth(SpecificComponent, option, adminRoute = false) {
  /* option
    1. null => 아무나 출입 가능
    2. true => 로그인한 유저만 출입 가능
    3. false => 로그인한 유저는 출입 불가능
  */
  function AuthenticationCheck() {
    const navigation = useNavigate();
    const dispatch = useDispatch();
    const location = useLocation();
    const user = useSelector((state) => state.user);
    const [loading, setLoading] = useState(true);

    const checkRender = (isAuth, isAdmin) => {
      if (!isAuth) {
        // 로그인 X
        if (option === true) return navigation("/login", { replace: true });
        else {
          const delay = location.pathname === "/" ? 1000 : 0;
          setTimeout(() => setLoading(false), delay);
        }
      } else {
        // 로그인 O
        if (adminRoute && !isAdmin) return navigation("/", { replace: true });

        if (option === false) return navigation("/", { replace: true });
        else {
          const delay = location.pathname === "/" ? 1000 : 0;
          setTimeout(() => setLoading(false), delay);
        }
      }
    };

    useEffect(() => {
      window.scrollTo({ top: 0 });

      if (!user.auth && localStorage.getItem("accessToken")) {
        dispatch(__asyncAuth()).then((payload) => {
          checkRender(
            payload.payload.auth,
            payload.payload.role === "ROLE_ADMIN"
          );
        });
      } else checkRender(user.auth, user.isAdmin);
    }, []);

    if (loading) return <Loading full={true} />;
    else return <SpecificComponent />;
  }

  return AuthenticationCheck;
}
