import React, { useEffect, useState } from "react";
import DetailedLeft from "../../components/DetailedLeft";
import DetailedRight from "../../components/DetailedRight";
import { useParams } from "react-router";
import axios from "axios";

function MoimDetailPage() {
  const { MoimId } = useParams();
  const [moimInfo, setMoimInfo] = useState([]);
  console.log(MoimId);
  useEffect(() => {
    const fetchMoimDetail = async () => {
      try {
        const response = await axios.get(
          `https://api.moim.today/api/moims/detail/${MoimId}`
        );
        console.log(response.data);
        setMoimInfo(response.data);
      } catch (error) {
        console.log(error);
      }
    };
    fetchMoimDetail();
  }, []);
  return (
    <div className="bg-gradient-to-b  justify-center from-white to-[#F6F8FE] h-screen w-full min-h-[800px] px-9">
      <div className=" flex w-fit ">
        <div className="flex gap-9 pt-2 flex-1 overflow-auto">
          <DetailedLeft
            userName={"작성자"}
            title={moimInfo.title}
            currentCount={moimInfo.currentCount}
            capacity={moimInfo.capacity}
            joined={false}
            image={moimInfo.imageUrl}
          />
          <DetailedRight
            category={moimInfo.category}
            title={moimInfo.title}
            currentCount={moimInfo.currentCount}
            capacity={moimInfo.capacity}
            contents={moimInfo.contents}
            className={"pl-3"}
          />
        </div>
      </div>
    </div>
  );
}

export default MoimDetailPage;
