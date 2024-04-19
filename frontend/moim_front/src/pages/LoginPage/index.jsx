import AuthRight from "../../components/AuthRight";
import AuthTitle from "../../components/AuthTitle";

function LoginPage() {
  return (
    <div>
      <AuthTitle
        title={"Log In"}
        firstContent={"Welcome back! "}
        secondContent={"Please login to your account."}
      />
      <AuthRight />
    </div>
  );
}

export default LoginPage;
