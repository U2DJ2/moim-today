function SidebarElementLink({ icon, text, color, onClick }) {
  return (
    <div
      className={`flex items-center gap-2.5 py-4 text-base font-semibold ${color}  cursor-pointer`}
      onClick={onClick}
    >
      {icon}
      <div className="flex-1 flex items-center justify-between font-Pretendard_SemiBold">
        {text}
      </div>
    </div>
  );
}
export default SidebarElementLink;
