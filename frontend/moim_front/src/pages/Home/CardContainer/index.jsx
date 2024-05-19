// React
import { useNavigate } from "react-router";

// API
import { GET } from "../../../utils/axios";

// Images
import people from "../../../assets/svg/personIcon.svg";

function CardContainer({
  image,
  category,
  title,
  capacity,
  currentCount,
  moimId,
}) {
  const navigate = useNavigate();

  const onClickHandler = () => {
    isMember();
  };
  const isMember = async () => {
    try {
      await GET(`api/members/${moimId}/joining`).then((res) => {
        if (res.isJoined === true) {
          navigate(`/join/${moimId}`);
        } else {
          navigate(`/detailed/${moimId}`);
        }
        console.log(res);
      });
    } catch (e) {
      console.log(e);
    }
  };

  return (
    <div className=" hover:cursor-pointer w-[360px] " onClick={onClickHandler}>
    <div
      className=" hover:cursor-pointer w-auto"
      onClick={() => navigate(`/detailed/${moimId}`)}
    >
      <img
        src={image}
        alt="cardContainer"
        className="w-full h-[100px] object-cover rounded-t-3xl"
      />
      <div className="bg-white w-auto h-auto drop-shadow rounded-b-3xl flex flex-col gap-5 py-3 px-12 ">
        <div className="flex flex-col gap-5">
          <div className="flex flex-col flex-wrap content-center">
            <div className=" font-Pretendard_Normal text-sm text-[#A1A3A5]">
              {category}
            </div>
            <div className=" font-Pretendard_SemiBold text-xl">{title}</div>
            <div className="flex font-Pretendard_SemiBold text-sm text-[#6F6F6F] pt-5 gap-2">
              <img src={people} />
              <div className="flex">
                <div>{currentCount}</div>
                <div>/</div>
                <div>{capacity}</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
export default CardContainer;
