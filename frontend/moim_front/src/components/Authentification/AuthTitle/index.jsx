function AuthTitle({
  title,
  firstContent,
  secondContent,
  titleColor,
  contentColor,
}) {
  return (
    <div className="flex flex-col gap-4">
      <div
        className={`font-Pretendard_Black font-black text-7xl text-${titleColor}`}
      >
        {title}
      </div>
      <div
        className={`font-Pretendard_Normal font-medium text-2xl text-${contentColor}`}
      >
        <div>{firstContent}</div>
        <div>{secondContent}</div>
      </div>
    </div>
  );
}

export default AuthTitle;
