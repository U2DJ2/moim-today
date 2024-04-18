function Header() {
  return (
    <div className="flex flex-col justify-between pb-20 bg-white">
      <div className="flex gap-5 justify-between px-9 py-6 w-full text-lg leading-7 text-center text-gray-500 max-md:flex-wrap max-md:px-5 max-md:max-w-full">
        <div className="flex gap-5 justify-center my-auto font-semibold">
          <div>대시보드</div>
          <div>모임 생성</div>
        </div>
        <div className="flex gap-5 justify-center font-bold whitespace-nowrap">
          <div className="justify-center my-auto">아주대학교</div>
          <img
            loading="lazy"
            srcSet="https://cdn.builder.io/api/v1/image/assets/TEMP/c9e3048d2ecf88eda8221e80e1f3587f63212f6776bfaae61a153b6c70100a59?apiKey=d805a42ceca34cfc9ccedfe9a24c9a43&width=100 100w, https://cdn.builder.io/api/v1/image/assets/TEMP/c9e3048d2ecf88eda8221e80e1f3587f63212f6776bfaae61a153b6c70100a59?apiKey=d805a42ceca34cfc9ccedfe9a24c9a43&width=200 200w, https://cdn.builder.io/api/v1/image/assets/TEMP/c9e3048d2ecf88eda8221e80e1f3587f63212f6776bfaae61a153b6c70100a59?apiKey=d805a42ceca34cfc9ccedfe9a24c9a43&width=400 400w, https://cdn.builder.io/api/v1/image/assets/TEMP/c9e3048d2ecf88eda8221e80e1f3587f63212f6776bfaae61a153b6c70100a59?apiKey=d805a42ceca34cfc9ccedfe9a24c9a43&width=800 800w, https://cdn.builder.io/api/v1/image/assets/TEMP/c9e3048d2ecf88eda8221e80e1f3587f63212f6776bfaae61a153b6c70100a59?apiKey=d805a42ceca34cfc9ccedfe9a24c9a43&width=1200 1200w, https://cdn.builder.io/api/v1/image/assets/TEMP/c9e3048d2ecf88eda8221e80e1f3587f63212f6776bfaae61a153b6c70100a59?apiKey=d805a42ceca34cfc9ccedfe9a24c9a43&width=1600 1600w, https://cdn.builder.io/api/v1/image/assets/TEMP/c9e3048d2ecf88eda8221e80e1f3587f63212f6776bfaae61a153b6c70100a59?apiKey=d805a42ceca34cfc9ccedfe9a24c9a43&width=2000 2000w, https://cdn.builder.io/api/v1/image/assets/TEMP/c9e3048d2ecf88eda8221e80e1f3587f63212f6776bfaae61a153b6c70100a59?apiKey=d805a42ceca34cfc9ccedfe9a24c9a43&"
            className="shrink-0 w-10 aspect-square"
          />
        </div>
      </div>
      <div className="flex flex-col self-center px-5 mt-9 max-w-full whitespace-nowrap w-[700px]">
        <div className="self-center text-9xl text-center text-rose-600 max-md:text-4xl">
          Moim
        </div>
        <div className="flex flex-col justify-center mt-2 text-zinc-700 text-opacity-60 max-md:max-w-full">
          <div className="flex gap-3 px-5 py-5 bg-zinc-50 bg-opacity-90 rounded-[40px] max-md:flex-wrap">
            <div className="justify-center px-1.5 py-1.5 text-lg font-medium text-center leading-[50.04px]">
              􀊫
            </div>
            <div className="flex-1 my-auto text-lg tracking-tight leading-6 max-md:max-w-full">
              Search
            </div>
          </div>
        </div>
      </div>
      <div className="flex gap-2.5 justify-between px-20 py-4 mt-9 w-full text-base text-center whitespace-nowrap text-neutral-700 max-md:flex-wrap max-md:px-5 max-md:max-w-full">
        <div className="flex gap-0 justify-center p-3 rounded-lg border border-gray-200 border-solid">
          <div>Views</div>
          <img
            loading="lazy"
            src="https://cdn.builder.io/api/v1/image/assets/TEMP/cb429ac32dd8c4cda3a1ab47fc76559f7394988000793bb7db4d615b978a0830?apiKey=d805a42ceca34cfc9ccedfe9a24c9a43&"
            className="shrink-0 w-5 aspect-square"
          />
        </div>
        <div className="flex justify-center items-center self-start px-16 font-medium text-black max-md:px-5 max-md:max-w-full">
          <div className="flex gap-3">
            <div className="justify-center px-9 py-3 bg-stone-100 rounded-[64px] max-md:px-5">
              전체
            </div>
            <div className="justify-center px-6 py-3 rounded-[64px] max-md:px-5">
              추천순
            </div>
            <div className="justify-center px-6 py-3 rounded-[64px] max-md:px-5">
              최신순
            </div>
          </div>
        </div>
        <div className="flex gap-0 justify-center px-4 py-3 border border-gray-200 border-solid rounded-[30px]">
          <img
            loading="lazy"
            src="https://cdn.builder.io/api/v1/image/assets/TEMP/07307db312450381256c74bc0bff6eb04c6f1539fc1874e0b249832c26cf2bcb?apiKey=d805a42ceca34cfc9ccedfe9a24c9a43&"
            className="shrink-0 w-5 aspect-square"
          />
          <div>Filters</div>
        </div>
      </div>
      <button className="flex gap-0 justify-center px-4 py-3 border border-gray-200 border-solid rounded-[30px]">
        <img src="https://cdn.builder.io/api/v1/image/assets/TEMP/07307db312450381256c74bc0bff6eb04c6f1539fc1874e0b249832c26cf2bcb?apiKey=d805a42ceca34cfc9ccedfe9a24c9a43&" alt="Filters" className="shrink-0 w-5 aspect-square" />
        <div>Filters</div>
      </button>
    </div>
  );
}

function Home() {
  return (
    <div className="flex flex-col justify-between pb-20 bg-white">
      <Header />
      <main className="flex flex-col self-center px-5 mt-9 max-w-full whitespace-nowrap w-[700px]">
        <h1 className="self-center text-9xl text-center text-rose-600 max-md:text-4xl">
          Moim
        </h1>
        <SearchBar />
      </main>
      <FilterBar />
    </div>
  );
}

export default Home;