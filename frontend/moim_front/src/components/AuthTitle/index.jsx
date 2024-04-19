function AuthTitle(props) {
  const { title, firstContent, secondContent } = props;

  return (
    <div className="flex flex-col items-start gap-2">
      <div className="font-Pretendard_Black font-black text-6xl">{title}</div>
      <div className="font-Pretendard_Normal font-medium text-xl text-[#515151]">
        <div>{firstContent}</div>
        <div>{secondContent}</div>
      </div>
    </div>
  );
}

export default AuthTitle;
