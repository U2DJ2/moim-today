function AuthTitle({ title }) {
  return (
    <div className="flex flex-col items-start gap-2">
      <div className="font-Pretendard_Black font-black text-6xl">Log In</div>
      <div className="font-Pretendard_Normal font-medium text-xl text-[#515151]">
        <div>{title}</div>
        <div>Please login to your account.</div>
      </div>
    </div>
  );
}

export default AuthTitle;
