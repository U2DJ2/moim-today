// Images
import landingImg from "../../assets/svg/landingImg.svg";

function AuthRight({ cardColor, textColor }) {
  return (
    <div className="flex-1 w-[80%] relative">
      <div
        className={`flex w-[120%] h-[130%] absolute left-32 -rotate-12 rounded-[64px] bg-${cardColor}`}
      ></div>
      <div
        className={`text-[150px] relative font-Praise top-1/4 grid items-center left-12 justify-center text-${textColor}`}
      >
        Moim
      </div>
      <img
        src={landingImg}
        className="absolute bottom-0 right-0 object-fill w-full h-72 left-12"
      />
    </div>
  );
}

export default AuthRight;
