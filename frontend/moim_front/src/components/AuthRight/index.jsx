// Images
import landingImg from "../../assets/svg/landingImg.svg";

function AuthRight({ cardColor, textColor }) {
  return (
    <div className="flex-1 w-[50%] relative max-xl:hidden">
      <div
        className={`flex w-[100%] h-[130%] absolute left-48 -rotate-12 rounded-[64px] bg-${cardColor}`}
      ></div>
      <div
        className={`text-[180px] relative font-Praise top-1/4 grid items-center left-20 justify-center text-${textColor}`}
      >
        Moim
      </div>
      <img
        src={landingImg}
        className="absolute bottom-0 right-0 object-fill w-full h-72 left-20"
      />
    </div>
  );
}

export default AuthRight;
