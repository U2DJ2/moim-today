// React
import { useState } from "react";
import { useNavigate } from "react-router-dom";

// API
import { POST } from "../../utils/axios";

// Define label style
const labelStyle =
  "mt-2.5 mb-2.5 text-base font-Pretendard_SemiBold leading-5 text-stone-700 max-md:max-w-full";

// Define common input style
const commonInputStyle =
  "justify-center px-4 py-3.5 text-sm font-Pretendard_Medium leading-5.5 rounded-xl bg-neutral-50 text-black";

function InputField({
  label,
  placeholder,
  setTitle,
  setContents,
  changeHandler,
}) {
  return (
    <>
      <div className={labelStyle}>{label}</div>
      <div className={`${commonInputStyle} max-md:max-w-full`}>
        <input
          type="text"
          className={`w-full bg-transparent outline-none`}
          placeholder={placeholder}
          onChange={changeHandler}
        />
      </div>
    </>
  );
}


export default function MoimCreation() {
  const [title, setTitle] = useState("");
  const [contents, setContents] = useState("");

  const navigate = useNavigate();

  // "취소하기" 버튼 클릭 시 메인 페이지로 이동
  const handleCancel = () => {
    navigate("/");
  };

  const data = {
    inquiryTitle: title,
    inquiryContent: contents,
  };

  const onClickHandler = () => {
    try {
      POST("api/members/user-inquiry", data)
        .then(() => {
          navigate("/");
        })
        .catch((error) => {
          console.log(error);
        });
    } catch (error) {
      console.log("error");
    }
  };
  return (
    <div className="flex flex-col justify-center p-8 bg-white rounded-[32px] max-md:px-5">
      <header className="flex gap-0 pb-8 justify-between font-Pretendard_Black font-semibold text-black leading-[150%] max-md:flex-wrap items-center">
        <h1 className="flex-1 text-3xl max-md:max-w-full">
          문의 내용을 입력해주세요!
        </h1>
      </header>
      <main>
        <InputField
          label="제목"
          placeholder="문의 요약 제목"
          setTitle={setTitle}
          setContents={setContents}
          changeHandler={(e) => {
            setTitle(e.target.value);
          }}
        />
        <InputField
          label="상세 설명"
          placeholder="설명을 적어주세요."
          setTitle={setTitle}
          setContents={setContents}
          changeHandler={(e) => {
            setContents(e.target.value);
          }}
        />
      </main>
      <footer className="flex justify-center gap-12">
        <button
          className="mt-10 text-lg font-Pretendard_SemiBold leading-7 text-gray-400"
          onClick={handleCancel}
        >
          취소하기
        </button>
        <button
          className="mt-10 text-lg font-Pretendard_SemiBold leading-7 text-rose-600"
          onClick={onClickHandler}
        >
          등록하기
        </button>
      </footer>
    </div>
  );
}
