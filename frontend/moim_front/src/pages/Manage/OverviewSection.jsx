import CardComponent from "../Home/CardContainer";
import { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router";
function Overview() {
  const [selected, setSelected] = useState("전체");
  const [moims, setMoims] = useState([]);

  const navigate = useNavigate();
  const getCardInfo = async () => {
    try {
      const result = await axios.get(
        "https://api.moim.today/api/moims/joined/simple",
        {
          params: {
            onlyHost: selected === "전체" ? false : true,
            ended: false,
          },
        }
      );
      console.log(result);
      setMoims(result.data.data);
    } catch (e) {
      console.log(e);
    }
  };

  useEffect(() => {
    getCardInfo();
  }, []);

  useEffect(() => {
    getCardInfo();
  }, [selected]);

  return (
    <section className="flex flex-col w-full gap-3 pt-12 pb-7 bg-slate-50 rounded-[64px_64px_0px_0px] max-md:px-5">
      <div className=" px-12 gap-3">
        <h1 className="text-6xl font-black pb-3 font-Pretendard_Black text-black max-md:max-w-full max-md:text-4xl">
          Overview
        </h1>
        <div className="flex items-center self-start font-Pretendard_Medium font-normal text-black max-md:px-5 max-md:max-w-full">
          <div className="flex gap-3">
            <div
              className={`justify-center px-9 py-3 rounded-[64px] max-md:px-5 cursor-pointer ${
                selected === "전체" ? "bg-black text-white" : ""
              }`}
              onClick={() => setSelected("전체")}
            >
              전체
            </div>
            <div
              className={`justify-center px-6 py-3 rounded-[64px] max-md:px-5 cursor-pointer ${
                selected === "만든 모임" ? "bg-black text-white" : ""
              }`}
              onClick={() => setSelected("만든 모임")}
            >
              만든 모임
            </div>
          </div>
        </div>

        <div className="flex gap-5 justify-between px-0.5 py-3 mt-3 w-full max-md:flex-wrap max-md:max-w-full">
          <div className="flex gap-5 justify-between font-semibold"></div>
        </div>
        <div className="grid grid-cols-2 gap-8 auto-rows-auto mt-3 max-md:max-w-full">
          {moims.length != 0 ? (
            moims.map((data, index) => (
              <CardComponent
                key={data.moimId}
                title={data.title}
                category={data.moimCategory}
                capacity={data.capacity}
                currentCount={data.currentCount}
                moimId={data.moimId}
                image={data.imageUrl}
              />
            ))
          ) : (
            <div className="pl-9 grid gap-2">
              <div className="font-Pretendard_Light text-lg">
                모임이 없습니다.
              </div>
              <div
                className="font-Pretendard_Light text-sm hover:text-scarlet hover:cursor-pointer"
                onClick={() => navigate("/creation")}
              >
                모임 만들러 가기
              </div>
            </div>
          )}
        </div>
      </div>
    </section>
  );
}

function Card() {
  return (
    <div className="flex flex-col w-[33%] max-md:ml-0 max-md:w-full">
      <div className="shrink-0 mx-auto w-80 h-80 bg-white shadow-sm aspect-square rounded-[36px] max-md:mt-10" />
    </div>
  );
}

export default Overview;
