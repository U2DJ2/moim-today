// React
import { useState, useEffect } from "react";

// Material UI
import { Slide } from "@mui/material";
import MuiAlert from "@mui/material/Alert";

// Icons
import EditIcon from "@mui/icons-material/Edit";

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
      <div className="flex gap-2.5 px-4 py-3.5 mt-2 leading-5 font-Pretendard_Light rounded-3xl border border-solid border-zinc-300 text-zinc-800 max-md:flex-wrap max-md:pr-5">
        <div>{value}</div>
        {editable ? <EditIcon /> : null}
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

export default function ProfileSection({ name, major }) {
  console.log(name);
  return (
    <section className="flex flex-col w-full max-md:ml-0 max-md:w-full h-full">
      <div className="flex flex-col self-stretch p-12 text-sm font-semibold leading-5 whitespace-nowrap bg-slate-50 rounded-[64px_64px_0px_0px] text-stone-500 max-md:px-5 max-md:mt-6 max-md:max-w-full h-full flex-grow">
        <h1 className="text-6xl font-Roboto_Bold text-black max-md:max-w-full max-md:text-4xl">
          Profile
        </h1>
        <InputField label="프로필 이미지" value={name} editable={false} />
        <InputField label="이름" value={name} editable={false} />
        <InputField label="학과" value={major} editable={true} />
      </div>
    </section>
  );
}
