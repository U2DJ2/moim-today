// React
import { useState, useRef, Fragment } from "react";
import { useNavigate } from "react-router-dom";

// UI and Icons
import { Checkbox } from "@mui/material";
import { Menu, Transition } from "@headlessui/react";
import { ChevronDownIcon } from "@heroicons/react/20/solid";

// Date
import DateRangePicker from "../../components/DateRangePicker";

// Define label style
const labelStyle = "mt-2.5 text-base font-Pretendard_SemiBold leading-5 text-stone-700 max-md:max-w-full";

// Define common input style
const commonInputStyle = "justify-center px-4 py-3.5 mt-2 text-sm font-Pretendard_Medium leading-5.5 rounded-xl bg-neutral-50 text-black";

function classNames(...classes) {
  return classes.filter(Boolean).join(" ");
}


function Dropdown({ label, options, onSelect }) {
  const [selectedOption, setSelectedOption] = useState(options[0]);

  const handleOptionClick = (option) => {
    setSelectedOption(option);
    onSelect(option);
  };

  return (
    <>
      <div className={labelStyle}>
        {label}
      </div>
      
      <Menu as="div" className="relative w-full inline-block text-left">
        <div>
          <Menu.Button className={`${commonInputStyle} inline-flex w-full justify-between items-center gap-x-1.5 px-4 hover:bg-gray-50`}>
            <span>{selectedOption}</span>
            <ChevronDownIcon
              className="h-5 w-5 text-gray-400 ml-auto"
              aria-hidden="true"
            />
          </Menu.Button>
        </div>

        <Transition
          as={Fragment}
          enter="transition ease-out duration-100"
          enterFrom="transform opacity-0 scale-95"
          enterTo="transform opacity-100 scale-100"
          leave="transition ease-in duration-75"
          leaveFrom="transform opacity-100 scale-100"
          leaveTo="transform opacity-0 scale-95"
        >
          <Menu.Items className="absolute left-0 z-10 mt-2 w-56 origin-top-right rounded-md bg-white shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none">
            <div className="py-1">
              {options.map((option, index) => (
                <Menu.Item key={index}>
                  {({ active }) => (
                    <a
                      href="#"
                      onClick={() => handleOptionClick(option)}
                      className={classNames(
                        active ? "bg-gray-100 text-gray-900" : "text-gray-700",
                        "block px-4 py-2 text-sm"
                      )}
                    >
                      {option}
                    </a>
                  )}
                </Menu.Item>
              ))}
            </div>
          </Menu.Items>
        </Transition>
      </Menu>
    </>
  );
}

function InputField({ label, placeholder }) {
  return (
    <>
      <div className={labelStyle}>
        {label}
      </div>
      <div className={`${commonInputStyle} max-md:max-w-full`}>
        <input
          type="text"
          className={`w-full bg-transparent outline-none`}
          placeholder={placeholder}
        />
      </div>
    </>
  );
}


function ImageUploader() {
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
    }
  };

  const handleImageClick = () => {
    fileInputRef.current.click();
  };

  return (
    <>
      <div className={labelStyle}>
        {" "}
        이미지 올리세요{" "}
      </div>
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
  const navigate = useNavigate();

  const handleVisibility = (event) => {
    const isChecked = event.target.checked; // 체크 여부를 가져옵니다.
    // isChecked 값에 따라 필요한 작업을 수행합니다.
    console.log("체크 여부:", isChecked);
  };

  const handleDropdown = (option) => {
    console.log(option);
  };

  // "취소하기" 버튼 클릭 시 메인 페이지로 이동
  const handleCancel = () => {
    navigate("/");
  };

  return (
    <div className="flex flex-col justify-center p-8 bg-white rounded-[32px] max-md:px-5">
      <header className="flex gap-0 pb-8 justify-between font-semibold text-black leading-[150%] max-md:flex-wrap items-center">
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

        <Dropdown
          label={"카테고리"}
          options={["스터디", "팀 프로젝트", "취미활동", "기타"]}
          onSelect={handleDropdown}
        />

        <InputField label="모임명" placeholder="모임 이름을 적어주세요." />
        <InputField label="상세 설명" placeholder="모임 설명을 적어주세요." />
        <ImageUploader />

        <InputField
          label="운영 시간"
          placeholder="이거 클릭하면 Date Picker 떠야 됨"
        />
        <DateRangePicker/>

        <InputField
          label="참여 인원"
          placeholder="참여 인원을 숫자만 입력해주세요!"
        />

      </main>
      <footer className="flex justify-center gap-12">
        <button
          className="mt-10 text-lg font-Pretendard_SemiBold leading-7 text-gray-400"
          onClick={handleCancel}
        >
          {" "}
          취소하기{" "}
        </button>
        <button className="mt-10 text-lg font-Pretendard_SemiBold leading-7 text-rose-600">
          {" "}
          생성하기{" "}
        </button>
      </footer>
    </div>
  );
}