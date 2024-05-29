function MoimContainer({ children }) {

  return (
    <div className="bg-gradient-to-b from-white to-[#F6F8FE] w-full min-h-screen h-vh px-10">
      <div className="flex items-start gap-8 h-full overflow-auto">
        {children}
      </div>
    </div>
  );
}

export default MoimContainer;
