function AuthTitle({ title, firstContent, secondContent, white }) {
  return (
    <div className="flex flex-col items-start gap-2">
      <div
        className={`font-Pretendard_Black font-black text-7xl ${
          white ? "text-white" : "text-black"
        }`}
      >
        {title}
      </div>
      <div
        className={`"font-Pretendard_Normal font-medium text-xl ${
          white ? "text-white" : "text-[#515151]"
        }`}
      >
        <div>{firstContent}</div>
        <div>{secondContent}</div>
      </div>
    </div>
  );
}

export default AuthTitle;
