// React
import { useState, useEffect, useRef } from "react";

// Material UI
import { Slide } from "@mui/material";
import MuiAlert from "@mui/material/Alert";

// Icons
import EditIcon from "@mui/icons-material/Edit";

import { POST } from "../../utils/axios";
import axios from "axios";

import infoIcon from "../../assets/svg/Info_duotone_line.svg";

function Alert(props) {
  return <MuiAlert elevation={6} variant="filled" {...props} />;
}

function InputField({ label, value, editable }) {
  const [inputValue, setInputValue] = useState(value);
  const [open, setOpen] = useState(false);

  useEffect(() => {
    let timer;
    if (open) {
      timer = setTimeout(() => {
        setOpen(false);
      }, 2000);
    }
    return () => {
      clearTimeout(timer);
    };
  }, [open]);

  return (
    <>
      <div className="mt-5 max-md:max-w-full font-Pretendard_Normal">
        {label}
      </div>
      <div className="flex justify-between gap-2.5 px-4 py-3.5 mt-2  leading-5 font-Pretendard_Light rounded-3xl border border-solid border-zinc-300 text-zinc-800 max-md:flex-wrap max-md:pr-5">
        <div className="text-[#333333] font-Pretendard_SemiBold font-semibold">
          {value}
        </div>
        {editable ? (
          <div className="hover:cursor-pointer">
            <EditIcon />
          </div>
        ) : null}
      </div>
      <Slide direction="up" in={open} mountOnEnter unmountOnExit>
        <div style={{ position: "fixed", bottom: 20, right: 20 }}>
          <Alert severity="success" onClose={() => setOpen(false)}>
            {inputValue}
          </Alert>
        </div>
      </Slide>
    </>
  );
}

function ImageInputer({ setProfileImg, profileImg }) {
  const [image, setImage] = useState(null);
  const fileInputRef = useRef(null);

  const handleImageChange = (event) => {
    const file = event.target.files[0];
    const reader = new FileReader();

    reader.onloadend = () => {
      setImage(reader.result);
      console.log(image);
    };

    console.log(profileImg);

    if (file) {
      reader.readAsDataURL(file);
      convertAndSendImage(file);
    }
  };

  const handleImageClick = () => {
    fileInputRef.current.click();
  };

  const convertAndSendImage = async (file) => {
    try {
      const formData = new FormData();
      formData.append("file", file);

      const response = await POST("api/members/profile-image", formData);
      console.log(response);

      const result = await axios.patch(
        "https://api.moim.today/api/members/profile",
        response
      );

      console.log(result);

      // Refresh current page
      window.location.reload();

    } catch (error) {
      console.error("이미지 전송 중 오류:", error);
    }
  };
  return (
    <div className="flex justify-center items-center px-4 py-4 mt-2 rounded-xl border border-dashed border-neutral-400 max-md:px-5 max-md:max-w-full">
      <img
        loading="lazy"
        src={
          typeof profileImg === "string" &&
            profileImg.split("/").pop() === "default-profile.png"
            ? "https://cdn.builder.io/api/v1/image/assets/TEMP/6bf0157b2a958fbe906dbd51a7e42975c058c0ecf274028bbcdbf686001a61ed?apiKey=d805a42ceca34cfc9ccedfe9a24c9a43&"
            : profileImg
        }
        className="my-3 max-w-full max-h-96 object-contain cursor-pointer"
        onClick={handleImageClick}
      />
      <input
        ref={fileInputRef}
        id="upload"
        type="file"
        className="hidden"
        onChange={handleImageChange}
      />
    </div>
  );
}

export default function ProfileSection({
  name,
  major,
  setProfileImg,
  profileImg,
  userInfo,
}) {
  return (
    <section className="flex flex-col w-full">
      <div className="flex flex-col self-stretch p-12 text-sm font-semibold leading-5 whitespace-nowrap bg-slate-50 rounded-[64px_0px_0px_0px] text-stone-500 max-md:px-5 max-md:mt-6 max-md:max-w-full h-full flex-grow">
        <h1 className="text-6xl font-Pretendard_Black text-black max-md:max-w-full max-md:text-4xl">
          Profile
        </h1>
        <div className="mt-5 max-md:max-w-full font-Pretendard_Normal">
          프로필 이미지
        </div>
        <ImageInputer setProfileImg={setProfileImg} profileImg={profileImg} />
        <InputField label="이름" value={name} editable={false} />
        <InputField label="학과" value={major} editable={false} />
      </div>
    </section>
  );
}
