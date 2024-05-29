function MoimContainer({ children }) {
  return (
    <div className="justify-between flex flex-col md:pl-10 bg-gradient-to-b from-white to-[#F6F8FE]">
      <div className="flex max-md:flex-col gap-8">
        {children}
      </div>
    </div>
  );
}

export default MoimContainer;
