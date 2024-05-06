import people from "../../../assets/svg/personIcon.svg";
function CardContainer({ image, category, title, capacity, currentCount }) {
  return (
    <div className="card-container hover:cursor-pointer">
      <img
        src={image}
        alt="cardContainer"
        className=" w-[360px] h-[100px] object-cover rounded-t-3xl"
      />
      <div className="bg-white w-[360px] h-auto drop-shadow rounded-b-3xl flex flex-col gap-5 py-3 px-16 ">
        <div className="flex flex-col gap-5">
          <div className="flex flex-col justify-items-center">
            <div className=" font-Pretendard_Normal text-base text-[#A1A3A5]">
              {category}
            </div>
            <div className=" font-Pretendard_SemiBold text-3xl">{title}</div>
          </div>
          <div className="flex font-Pretendard_SemiBold text-[#6F6F6F] ">
            <img src={people} />
            <div>{currentCount}</div>
            <div>/</div>
            <div>{capacity}</div>
          </div>
        </div>
      </div>
    </div>
  );
}
export default CardContainer;
