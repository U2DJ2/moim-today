// React
import { useState, useEffect, useRef } from "react";

// Material UI
import { Slide } from "@mui/material";
import MuiAlert from "@mui/material/Alert";

// Icons
import EditIcon from "@mui/icons-material/Edit";

import { POST } from "../../utils/axios";

function Alert(props) {
  return <MuiAlert elevation={6} variant="filled" {...props} />;
}

function InputField({ label, value, editable }) {
  const [inputValue, setInputValue] = useState(value);
  const [open, setOpen] = useState(false);

  const handleInputChange = (e) => {
    setInputValue("수정이 완료됐습니다");
  };

  const handleInputBlur = () => {
    setOpen(true);
    // console.log("수정된 값:", inputValue);
  };

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

function ImageInputer() {
  const [image, setImage] = useState(null);
  const fileInputRef = useRef(null);

  const handleImageChange = (event) => {
    const file = event.target.files[0];
    const reader = new FileReader();

    reader.onloadend = () => {
      setImage(reader.result);
      console.log(image);
    };

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

      /*const response = await fetch("your-server-url", {
        method: "POST",
        body: formData,
      });*/

      POST("api/members/profile-image", formData)
        .then((res) => console.log(res))
        .catch((error) => {
          console.log(error);
        });
    } catch (error) {
      console.error("이미지 전송 중 오류:", error);
    }
  };
  return (
    <div className="flex justify-center items-center px-4 py-4 mt-2 rounded-xl border border-dashed border-neutral-400 max-md:px-5 max-md:max-w-full">
      {image ? (
        <img
          loading="lazy"
          src={image}
          className="my-3 max-w-full max-h-96 object-contain cursor-pointer"
          onClick={handleImageClick}
        />
      ) : (
        <img
          loading="lazy"
          src="https://cdn.builder.io/api/v1/image/assets/TEMP/6bf0157b2a958fbe906dbd51a7e42975c058c0ecf274028bbcdbf686001a61ed?apiKey=d805a42ceca34cfc9ccedfe9a24c9a43&"
          className="my-3 w-14 aspect-square fill-zinc-300 cursor-pointer"
          onClick={handleImageClick}
        />
      )}
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

export default function ProfileSection({ name, major }) {
  console.log(name);
  return (
    <section className="flex flex-col w-full max-md:ml-0 max-md:w-full h-full">
      <div className="flex flex-col self-stretch p-12 text-sm font-semibold leading-5 whitespace-nowrap bg-slate-50 rounded-[64px_64px_0px_0px] text-stone-500 max-md:px-5 max-md:mt-6 max-md:max-w-full h-full flex-grow">
        <h1 className="text-6xl font-Pretendard_Black text-black max-md:max-w-full max-md:text-4xl">
          Profile
        </h1>
        <div className="mt-5 max-md:max-w-full font-Pretendard_Normal">
          프로필 이미지
        </div>
        <ImageInputer />
        <InputField label="이름" value={name} editable={false} />
        <InputField label="학과" value={major} editable={false} />
      </div>
    </section>
  );
}
