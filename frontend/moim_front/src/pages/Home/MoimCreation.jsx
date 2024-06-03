// React
import { useState, useRef } from "react";
import { useNavigate } from "react-router-dom";

// UI and Icons
import { Modal } from "flowbite-react";
import { Checkbox } from "@mui/material";
import { HiOutlineExclamationCircle } from "react-icons/hi";

// Date Picker
import DateRangePicker from "../../components/DatePicker/Range";
import Dropdown from "../../components/Dropdown/LabelSimple";

// API
import { POST } from "../../utils/axios";

// Define label style
const labelStyle = "mt-2.5 mb-2.5 text-base font-Pretendard_SemiBold leading-5 text-stone-700 max-md:max-w-full";

// Define common input style
const commonInputStyle = "justify-center px-4 py-3.5 text-sm font-Pretendard_Medium leading-5.5 rounded-xl bg-neutral-50 text-black";

const modalTheme = {
  root: {
    base: "fixed inset-x-0 top-0 z-50 h-screen overflow-y-auto overflow-x-hidden md:inset-0 md:h-full",
    show: {
      on: "flex bg-gray-900 bg-opacity-50 dark:bg-opacity-80",
      off: "hidden",
    },
    sizes: {
      sm: "max-w-sm",
      md: "max-w-md",
      lg: "max-w-lg",
      xl: "max-w-xl",
      "2xl": "max-w-2xl",
      "3xl": "max-w-3xl",
      "4xl": "max-w-4xl",
      "5xl": "max-w-5xl",
      "6xl": "max-w-6xl",
      "7xl": "max-w-7xl",
    },
    positions: {
      "top-left": "items-start justify-start",
      "top-center": "items-start justify-center",
      "top-right": "items-start justify-end",
      "center-left": "items-center justify-start",
      center: "items-center justify-center",
      "center-right": "items-center justify-end",
      "bottom-right": "items-end justify-end",
      "bottom-center": "items-end justify-center",
      "bottom-left": "items-end justify-start",
    },
  },
  content: {
    base: "relative h-full w-full p-4 md:h-auto",
    inner:
      "relative flex max-h-[90dvh] flex-col rounded-xl bg-white shadow dark:bg-gray-700",
  },
  body: {
    base: "flex-1 overflow-auto p-6",
    popup: "pt-0",
  },
  header: {
    base: "flex items-start justify-between rounded-t border-b mt-0.5 p-5 dark:border-gray-600",
    popup: "border-b-0 p-2",
    title:
      "pt-0.5 text-xl font-Pretendard_SemiBold text-gray-900 dark:text-white",
    close: {
      base: "ml-auto inline-flex items-center rounded-lg bg-transparent p-1.5 text-sm text-gray-400 hover:bg-gray-200 hover:text-gray-900 dark:hover:bg-gray-600 dark:hover:text-white",
      icon: "h-5 w-5",
    },
  },
  footer: {
    base: "flex items-center space-x-2 rounded-b border-gray-200 p-6 dark:border-gray-600",
    popup: "border-t",
  },
};

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

function ImageUploader({ setUploadFile }) {
  const [image, setImage] = useState(null);
  const fileInputRef = useRef(null);

  const handleImageChange = (event) => {
    const file = event.target.files[0];
    const reader = new FileReader();

    reader.onloadend = () => {
      setImage(reader.result);
    };

    if (file) {
      reader.readAsDataURL(file);
      convertAndSendImage(file);
    }
  };

  const convertAndSendImage = async (file) => {
    try {
      const formData = new FormData();
      formData.append("file", file);

      POST("api/moims/image", formData)
        .then((res) => {
          setUploadFile(res.imageUrl);
          console.log(res.imageUrl);
        })
        .catch((error) => {
          console.log(error);
        });
    } catch (error) {
      console.error("이미지 전송 중 오류:", error);
    }
  };
  const handleImageClick = () => {
    fileInputRef.current.click();
  };

  return (
    <>
      <div className={labelStyle}> 이미지 올리세요 </div>
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
    </>
  );
}

export default function MoimCreation() {
  const [uploadFileUrl, setUploadFile] = useState(null);
  const [category, setCategory] = useState("STUDY");
  const [title, setTitle] = useState("");
  const [contents, setContents] = useState("");
  const [password, setPassword] = useState("");
  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");
  const [capacity, setCapacity] = useState(0);
  const [isChecked, setIsChecked] = useState(false);
  const [displayStatus, setDisplay] = useState("PRIVATE");
  const [isAlertModalOpen, setAlertModalOpen] = useState(false);
  const [alertMessage, setAlertMessage] = useState("");

  const navigate = useNavigate();

  const handleVisibility = (event) => {
    const isChecked = event.target.checked; // 체크박스의 체크 상태 얻기
    setIsChecked(isChecked); // isChecked 상태 업데이트
    // isChecked 값에 따라 displayStatus 상태를 "PUBLIC" 또는 "PRIVATE"로 설정
    setDisplay(isChecked ? "PUBLIC" : "PRIVATE");
  };

  // STUDY, TEAM_PROJECT, HOBBY, EXERCISE, OTHERS
  const handleDropdown = (option) => {
    console.log(option);
    if (option === "스터디") {
      setCategory("STUDY");
    } else if (option === "팀 프로젝트") {
      setCategory("TEAM_PROJECT");
    } else if (option === "취미활동") {
      setCategory("HOBBY");
    } else if (option === "운동") {
      setCategory("EXERCISE");
    } else {
      setCategory("OTHERS");
    }
  };

  // "취소하기" 버튼 클릭 시 메인 페이지로 이동
  const handleCancel = () => {
    navigate("/");
  };

  const handleDateRange = (dateRange) => {
    console.log(dateRange);
    setStartDate(dateRange.startDate);
    setEndDate(dateRange.endDate);
  };

  const data = {
    title: title,
    contents: contents,
    capacity: capacity,
    password: password,
    imageUrl: uploadFileUrl,
    moimCategory: category,
    displayStatus: displayStatus,
    startDate: startDate,
    endDate: endDate,
  };

  const onClickHandler = () => {
    if (data.title === "" || data.contents === "" || data.startDate === "" || data.endDate === "") {
      setAlertMessage("필수 입력 항목을 모두 입력해주세요!");
      setAlertModalOpen(true);
      return;
    }

    // capacity가 정수인지 확인
    if (isNaN(data.capacity)) {
      setAlertMessage("참여 인원은 숫자로 입력해주세요!");
      setAlertModalOpen(true);
      return;
    }

    if (data.capacity < 2) {
      setAlertMessage("참여 인원은 2명 이상으로 설정해주세요!");
      setAlertModalOpen(true);
      return;
    }

    try {
      POST("api/moims", data)
        .then((res) => {
          console.log(res);
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
    <>
      <Modal
        show={isAlertModalOpen}
        size="sm"
        onClose={() => setAlertModalOpen(false)}
        theme={modalTheme}
        popup
      >
        <Modal.Header />
        <Modal.Body>
          <div className="text-center">
            <HiOutlineExclamationCircle className="mx-auto mb-4 h-14 w-14 text-gray-400 dark:text-gray-200" />
            <h3 className="mb-5 text-base font-Pretendard_Normal text-black">
              { alertMessage || "모임을 생성했습니다!" }
            </h3>
            <div className="flex justify-center gap-4">
              <button
                className="py-3 px-5 w-fit text-base font-Pretendard_Normal text-white bg-scarlet rounded-[50px] hover:cursor-pointer"
                onClick={() => {
                  setAlertModalOpen(false);
                }}
              >
                네
              </button>
            </div>
          </div>
        </Modal.Body>
      </Modal>
      <div className="flex flex-col justify-center p-8 bg-white rounded-[32px] max-md:px-5">
        <header className="flex gap-0 pb-8 justify-between font-Pretendard_Black font-semibold text-black leading-[150%] max-md:flex-wrap items-center">
          <h1 className="flex-1 text-3xl max-md:max-w-full">
            모임을 생성해주세요
          </h1>
          <div className="flex gap-2 justify-center py-3 text-lg whitespace-nowrap items-center">
            <Checkbox
              onChange={handleVisibility}
              inputProps={{ "aria-label": "controlled" }}
            />
            <div>공개</div>
          </div>
        </header>
        <main>
          {!isChecked && (
            <>
              <div className={labelStyle}>비밀번호</div>
              <div className={`${commonInputStyle} max-md:max-w-full`}>
                <input
                  type="password"
                  className={`w-full bg-transparent outline-none`}
                  placeholder={"비밀번호를 입력해주세요."}
                  onChange={(e) => {
                    setPassword(e.target.value);
                  }}
                />
              </div>
            </>
          )}
          <Dropdown
            label={"카테고리"}
            options={["스터디", "팀 프로젝트", "취미활동", "운동", "기타"]}
            onSelect={handleDropdown}
            labelStyle={labelStyle}
            commonInputStyle={commonInputStyle}
          />
          <InputField
            label="모임명"
            placeholder="모임 이름을 적어주세요."
            setTitle={setTitle}
            setContents={setContents}
            changeHandler={(e) => {
              setTitle(e.target.value);
            }}
          />
          <InputField
            label="상세 설명"
            placeholder="모임 설명을 적어주세요."
            setTitle={setTitle}
            setContents={setContents}
            changeHandler={(e) => {
              setContents(e.target.value);
            }}
          />
          <ImageUploader setUploadFile={setUploadFile} />
          <div className={labelStyle}>{"운영 시간"}</div>
          <DateRangePicker
            onChange={handleDateRange}
            inputClassName={`w-full ${commonInputStyle}`}
            useMinDate={true}
          />
          <InputField
            label="참여 인원"
            placeholder="참여 인원을 숫자만 입력해주세요!"
            changeHandler={(e) => {
              setCapacity(parseInt(e.target.value));
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
            생성하기
          </button>
        </footer>
      </div>
    </>
  );
}
