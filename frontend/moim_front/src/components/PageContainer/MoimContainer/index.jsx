function MoimContainer({ children }) {
  return (
    <div className="bg-gradient-to-b from-white to-[#F6F8FE] w-full px-10">
      <div className="flex flex-1  gap-8 overflow-auto">{children}</div>
    </div>
  );
}

export default MoimContainer;
