import React, { useState, useEffect } from "react";
import MoimContainer from "../PageContainer/MoimContainer";
import DetailedLeft from "../DetailedLeft";
import { fetchMoimInfo } from "../../api/moim";
import { Outlet, useParams } from "react-router";
import axios from "axios";

function MoimLayout({ children }) {
  const [writerInfo, setWriterInfo] = useState([]);
  const [moimInfo, setMoimInfo] = useState([]);
  const { MoimId } = useParams();
  const fetchWriter = async () => {
    try {
      const response = await axios.get(
        `https://api.moim.today/api/members/host-profile/${MoimId}`
      );
      console.log(response);
      setWriterInfo(response.data);
    } catch (e) {
      console.log(e);
    }
  };
  const getInfo = async () => {
    try {
      const result = await fetchMoimInfo(MoimId);
      setMoimInfo(result.data);
    } catch (e) {
      console.log(e);
    }
  };
  useEffect(() => {
    fetchWriter();
    getInfo();
  }, []);
  return (
    <MoimContainer>
      <DetailedLeft
        userName={writerInfo.username}
        title={moimInfo.title}
        currentCount={moimInfo.currentCount}
        capacity={moimInfo.capacity}
        category={moimInfo.category}
        contents={moimInfo.contents}
        image={moimInfo.imageUrl}
        joined={true}
      />
      <div className="flex flex-col h-full w-full bg-white shadow-lg overflow-hidden rounded-t-3xl px-20 pt-8 pb-6 gap-8">
        <Outlet />{" "}
      </div>
    </MoimContainer>
  );
}

export default MoimLayout;
