function CardComponent({ date, dday, title, btn, clickHandler, isMeeting }) {
  return (
    <div
      className="w-auto p-8 grid font-Pretendard_SemiBold text-xl bg-white shadow-[0px_1px_4px_rgba(0,_0,_0,_0.08)] rounded-3xl hover:cursor-pointer"
      onClick={clickHandler}
    >
      <div className="grid grid-flow-row gap-4">
        <div className="">{date}</div>
        <div className="flex gap-4 items-center w-fit">
          <div className=" font-Pretendard_SemiBoldtext-3xl">{title}</div>
          {isMeeting ? (
            <div className="text-scarlet w-fit grid grid-flow-col">
              <div>D-</div>
              <div>{dday}</div>
            </div>
          ) : null}
        </div>
        {btn && (
          <button className="text-white bg-scarlet p-1.5 rounded-full font-Pretendard_SemiBold">
            참석하기
          </button>
        )}
      </div>
    </div>
  );
}

export default CardComponent;
