import CardComponent from "../Home/CardContainer";
import { useEffect, useState } from "react";
import { GET } from "../../utils/axios";
function Overview() {
  const [selected, setSelected] = useState("전체");
  const getCardInfo = async () => {
    try {
      const result = await GET("api/moims");
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  };

  useEffect(() => {
    getCardInfo();
  }, []);

  const data = [
    {
      id: 1,
      status: "신청 중",
      count: 6,
      icon: "https://cdn.builder.io/api/v1/image/assets/TEMP/720c1ca45e519369b2e6a4ef49ba13891dab6bf813521c27420e80ccdd5b6238?apiKey=d805a42ceca34cfc9ccedfe9a24c9a43&",
    },
    {
      id: 2,
      status: "진행 중",
      count: 12,
      icon: "https://cdn.builder.io/api/v1/image/assets/TEMP/9f6d72af111237b40dbc9c87a58fd534ce36155aa7ac714e7ca1e4bd8f683743?apiKey=d805a42ceca34cfc9ccedfe9a24c9a43&",
    },
    {
      id: 3,
      status: "완료 됨",
      count: 8,
      icon: "https://cdn.builder.io/api/v1/image/assets/TEMP/9011c6a31110b1d66dd0ea1aa7077c3c448b229dbff2aa32eca37b71e373b39c?apiKey=d805a42ceca34cfc9ccedfe9a24c9a43&",
    },
  ];

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
            <div
              className={`justify-center px-6 py-3 rounded-[64px] max-md:px-5 cursor-pointer ${
                selected === "참여 모임" ? "bg-black text-white" : ""
              }`}
              onClick={() => setSelected("참여 모임")}
            >
              참여 모임
            </div>
          </div>
        </div>

        <div className="flex gap-5 justify-between px-0.5 py-3 mt-3 w-full max-md:flex-wrap max-md:max-w-full">
          <div className="flex gap-5 justify-between font-semibold">
            {data.map((item) => (
              <div key={item.id} className="flex flex-col px-0.5 py-1.5">
                <div className="text-2xl text-black">{item.count}</div>
                <div className="flex gap-2 px-px mt-1.5 text-sm text-neutral-400">
                  <div>{item.status}</div>
                  <img
                    loading="lazy"
                    src={item.icon}
                    alt=""
                    className="shrink-0 w-4 aspect-square"
                  />
                </div>
              </div>
            ))}
          </div>
          <button className="flex gap-0 justify-center px-2.5 py-3 my-auto text-xs text-center whitespace-nowrap border border-gray-200 border-solid rounded-[30px] text-neutral-700">
            <img
              loading="lazy"
              src="https://cdn.builder.io/api/v1/image/assets/TEMP/232a05a254678eb41d6811e650ccbef200da282b5e461b74e4e6894f5cfcdd11?apiKey=d805a42ceca34cfc9ccedfe9a24c9a43&"
              alt=""
              className="shrink-0 w-4 aspect-square"
            />
            <span>필터</span>
          </button>
        </div>
        <div className="flex flex-col mt-3 max-md:max-w-full">
          {data.map((data, index) => (
            <CardComponent
              key={index}
              title={data.title}
              category={"# category"}
              capacity={12}
              currentCount={3}
              moimId={1}
            />
          ))}
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
