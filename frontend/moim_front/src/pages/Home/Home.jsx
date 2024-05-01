// React
import { useState } from "react";

// Components
import Header from "../../components/Header";
import Dropdown from "../../components/Dropdown/Simple";
import Filter from "../../components/Dropdown/Filter";

// Icons
import SearchIcon from "@mui/icons-material/Search";

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
          className="justify-center px-1.5 py-1.5 text-lg font-medium text-center leading-[8px]">
          <SearchIcon />
        </button>
        <input
          type="text"
          id="search"
          placeholder="Search"
          className="flex-1 bg-zinc-50 my-auto text-lg tracking-tight leading-6 max-md:max-w-full outline-none"
          aria-label="Search"
        />
      </form>
    </div>
  );
}

function FilterBar() {
  const [selected, setSelected] = useState("전체");

  const handleDropdown = (option) => {
    console.log(option);
  }

  const handleFilter = (option) => {
    console.log(option);
  }

  return (
    <div className="flex gap-2.5 justify-between px-12 py-4 mt-9 w-full text-base text-center whitespace-nowrap text-neutral-700 max-md:flex-wrap max-md:px-5 max-md:max-w-full">
      <div className="flex gap-0 justify-center items-center">
        <div>
          <Dropdown
            options={["Latest", "Views"]}
            onSelect={handleDropdown}
          />
        </div>
      </div>
      <div className="flex justify-center items-center self-start px-16 font-Pretendard_Medium font-normal text-black max-md:px-5 max-md:max-w-full">
        <div className="flex gap-3">
          <div
            className={`justify-center px-9 py-3 rounded-[64px] max-md:px-5 cursor-pointer ${selected === "전체" ? "bg-gray-200" : ""
              }`}
            onClick={() => setSelected("전체")}>
            전체
          </div>
          <div
            className={`justify-center px-6 py-3 rounded-[64px] max-md:px-5 cursor-pointer ${selected === "추천순" ? "bg-gray-200" : ""
              }`}
            onClick={() => setSelected("추천순")}>
            추천순
          </div>
          <div
            className={`justify-center px-6 py-3 rounded-[64px] max-md:px-5 cursor-pointer ${selected === "최신순" ? "bg-gray-200" : ""
              }`}
            onClick={() => setSelected("최신순")}>
            최신순
          </div>
        </div>
      </div>
      <div>
        <div className="flex gap-0 justify-center items-center">
          <Filter
            options={["Time", "Distance"]}
            onSelect={handleFilter}
          />
        </div>
      </div>
    </div>
  );
}

function Home() {
  return (
    <div className="flex flex-col justify-between pb-20 bg-white">
      <Header />
      <main className="flex flex-col self-center px-5 mt-9 max-w-full whitespace-nowrap w-[700px]">
        <h1 className="self-center text-9xl text-center text-rose-600 max-md:text-9xl font-Praise sm:text-9xl md:text-9xl lg:text-9xl xl:text-9xl">
          Moim
        </h1>
        <SearchBar />
      </main>
      <FilterBar />
    </div>
  );
}

export default Home;