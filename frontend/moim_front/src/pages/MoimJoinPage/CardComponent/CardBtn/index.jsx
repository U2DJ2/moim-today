function CardBtn({ name, clickHandler }) {
  return (
    <button
      className={
        "text-white bg-slate-400 p-1.5 rounded-full font-Pretendard_SemiBold hover:bg-scarlet"
      }
      onClick={clickHandler}
    >
      {name}{" "}
    </button>
  );
}

export default CardBtn;
