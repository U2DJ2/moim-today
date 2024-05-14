// React
import { useState, useEffect } from "react";

// Components
import Dropdown from "../../components/Dropdown/Simple";
import Filter from "../../components/Dropdown/Filter";

// Icons
import SearchIcon from "@mui/icons-material/Search";
import CardContainer from "./CardContainer";

//temporary image
import cardImage from "../../assets/svg/card-image.svg";

import { GET } from "../../utils/axios";

import axios from "axios";

/**
 * SearchBar Component
 * @returns {JSX.Element}
 */
function SearchBar() {
  return (
    <div className="flex flex-col justify-center mt-2 text-zinc-950 text-opacity-70 max-md:max-w-full">
      <form className="flex gap-3 px-6 py-3 bg-zinc-50 bg-opacity-90 rounded-[40px] max-md:flex-wrap">
        <label htmlFor="search" className="sr-only">
          Search
        </label>
        <button
          type="submit"
          className="justify-center px-1.5 py-1.5 text-lg font-medium text-center leading-[8px]"
        >
          <SearchIcon />
        </button>
        <input
          type="text"
          id="search"
          placeholder="Search"
          className="flex-1 bg-zinc-50 my-auto text-lg tracking-tight leading-6 max-md:max-w-full focus:outline-none border-none"
          aria-label="Search"
        />
      </form>
    </div>
  );
}

function FilterBar({
  setMoimCategory,
  setMoimSortedFilter,
  selected,
  setSelected,
}) {
  const handleDropdown = (option) => {
    console.log(option);
    if (option === "스터디") {
      setMoimCategory("STUDY");
    } else if (option === "팀 프로젝트") {
      setMoimCategory("TEAM_PROJECT");
    } else if (option === "취미활동") {
      setMoimCategory("HOBBY");
    } else if (option === "운동") {
      setMoimCategory("EXERCISE");
    } else if (option === "전체") {
      setMoimCategory("ALL");
    } else {
      setMoimCategory("OTHERS");
    }
  };

  const handleFilter = (option) => {
    setMoimSortedFilter(option);
  };

  return (
    <div className="flex gap-2.5 justify-between px-12 py-4 mt-9 w-full text-base text-center whitespace-nowrap text-neutral-700 max-md:flex-wrap max-md:px-5 max-md:max-w-full">
      <div className="flex gap-0 justify-center items-center">
        <div>
          <Dropdown
            options={[
              "전체",
              "스터디",
              "팀 프로젝트",
              "취미활동",
              "운동",
              "기타",
            ]}
            onSelect={handleDropdown}
          />
        </div>
      </div>
      <div className="flex justify-center items-center self-start px-16 font-Pretendard_Medium font-normal text-black max-md:px-5 max-md:max-w-full">
        <div className="flex gap-3">
          <div
            className={`justify-center px-9 py-3 rounded-[64px] max-md:px-5 cursor-pointer ${
              selected === "CREATED_AT" ? "bg-gray-200" : ""
            }`}
            onClick={() => setSelected("CREATED_AT")}
          >
            최신순
          </div>
          <div
            className={`justify-center px-6 py-3 rounded-[64px] max-md:px-5 cursor-pointer ${
              selected === "VIEWS" ? "bg-gray-200" : ""
            }`}
            onClick={() => setSelected("VIEWS")}
          >
            조회수 순
          </div>
        </div>
      </div>
      <div>
        <div className="flex gap-0 justify-center items-center">
          <Filter options={["Time", "Distance"]} onSelect={handleFilter} />
        </div>
      </div>
    </div>
  );
}
const getAllMoims = async (moimCategory, moimSortedFilter) => {
  await GET(`api/moims/simple/moimCategory=${moimCategory}/${moimSortedFilter}`)
    .then((res) => {
      console.log(res);
    })
    .then((error) => console.log(error));
};
export default function Home() {
  const [moimCategory, setMoimCategory] = useState("ALL");
  const [moimSortedFilter, setMoimSortedFilter] = useState("CREATED_AT");
  const [selected, setSelected] = useState("CREATED_AT");
  const [moimInfo, setMoimInfo] = useState([]);

  useEffect(() => {
    const fetchMoims = async () => {
      try {
        const params = {
          moimCategoryDto: moimCategory,
          moimSortedFilter: selected,
        };
        const response = await axios.get(
          "https://api.moim.today/api/moims/simple",
          {
            params: params,
          }
        );
        console.log(response.data);
        setMoimInfo(response.data.data);
      } catch (error) {
        console.log(error);
      }
    };
    fetchMoims();
  }, []);
  useEffect(() => {
    const fetchMoims = async () => {
      try {
        const params = {
          moimCategoryDto: moimCategory,
          moimSortedFilter: selected,
        };
        const response = await axios.get(
          "https://api.moim.today/api/moims/simple",
          {
            params: params,
          }
        );
        console.log(response.data);
        setMoimInfo(response.data.data);
        console.log(moimInfo);
      } catch (error) {
        console.log(error);
      }
    };
    fetchMoims();
  }, [moimCategory, selected]);
  useEffect(() => {}, [moimCategory, moimSortedFilter]);
  return (
    <div className="flex flex-col justify-between pb-20 bg-white">
      <main className="flex flex-col self-center px-5 mt-9 max-w-full whitespace-nowrap w-[700px]">
        <h1 className="self-center text-9xl text-center text-scarlet max-md:text-9xl font-Praise sm:text-9xl md:text-9xl lg:text-9xl xl:text-9xl">
          Moim
        </h1>
        <SearchBar />
      </main>
      <FilterBar
        setMoimCategory={setMoimCategory}
        selected={selected}
        setSelected={setSelected}
      />
      <div className="grid grid-cols-1 gap-10 mt-20 mx-auto md:grid-cols-1 lg:grid-cols-2 2xl:grid-cols-3">
        {moimInfo.length != 0 ? (
          moimInfo.map((item) => (
            <CardContainer
              key={item.moimId}
              image={item.imageUrl}
              category={item.moimCategory}
              title={item.title}
              capacity={item.capacity}
              currentCount={item.currentCount}
              moimId={item.moimId}
            />
          ))
        ) : (
          <div className="font-Pretendard_Light flex">
            검색 정보가 없습니다.
          </div>
        )}
      </div>
    </div>
  );
}
