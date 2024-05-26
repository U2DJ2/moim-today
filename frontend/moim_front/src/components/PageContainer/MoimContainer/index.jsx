function MoimContainer({ children }) {
  return (
    <div className="bg-gradient-to-b from-white to-[#F6F8FE] h-full w-full min-h-[800px] px-9 ">
      <div className="flex ">
        <div className="flex gap-9 pt-2 flex-1 overflow-auto">{children}</div>
      </div>
    </div>
  );
}

export default MoimContainer;
