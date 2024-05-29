import React, { useEffect, useState } from "react";
import AuthTitle from "../../../components/Authentification/AuthTitle";
import { GET, POST } from "../../../utils/axios";
import axios from "axios";
import properties from "../../../config/properties";
import Dropdown from "../../../components/Dropdown/Simple";
import Search from "../../../components/Dropdown/Search";

import { Modal } from "flowbite-react";
import { HiOutlineExclamationCircle } from "react-icons/hi";

function School({
  universityId,
  setDepartment,
  department,
  setStudentId,
  studentId,
  universityName,
  setActiveNext,
  setDepartmentId,
  departmentId,
}) {
  //department 중에서도 name만 담는 state for search
  const [departmentInfo, setDepartmentInfo] = useState([]);
  // 서버로부터 전체 department의 정보를 담는 state
  const [result, setResult] = useState([]);
  const [requestDepartment, setRequestDepartment] = useState("");
  const [openAlertModal, setOpenAlertModal] = useState(false);
  const getAllDepartment = axios.create({
    baseURL: properties.baseURL,
    withCredentials: true,
  });

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

  useEffect(() => {
    if (universityId) {
      getAllDepartment
        .get(`api/departments/university-id`, {
          params: { universityId: universityId },
        })
        .then((res) => {
          console.log(res.data.data);
          const departmentNameList = res.data.data.map((department, index) => {
            return department.departmentName;
          });
          setResult(res.data.data);
          setDepartmentInfo(departmentNameList);
        });
    }
  }, [universityId]);

  useEffect(() => {
    if (studentId.trim() !== "" && departmentId != null) {
      setActiveNext(true);
    } else setActiveNext(false);
  }, [studentId, departmentId]);

  const studentIdHandler = (e) => {
    setStudentId(e.target.value);
  };

  const handleDropdown = (index) => {
    const id = result[index].departmentId;
    setDepartmentId(id);
  };

  const onClickHandler = async () => {
    setOpenAlertModal(false);

    console.log("hi");

    try {
      const body = {
        universityId: universityId,
        departmentName: requestDepartment,
      };
      const result = await POST("api/request-departments", body);

      console.log(result);
    } catch (e) {
      console.log(e);
    }
  };

  return (
    <>
      <Modal
        show={openAlertModal}
        size="lg"
        onClose={() => setOpenAlertModal(false)}
        theme={modalTheme}
        popup
      >
        <Modal.Header />
        <Modal.Body>
          <div className="text-center">
            <HiOutlineExclamationCircle className="mx-auto mb-4 h-14 w-14 text-gray-400 dark:text-gray-200" />
            <h3 className="mb-5 text-base font-Pretendard_Normal text-black">
              추가할 학과명을 입력해주세요
            </h3>
            <div>
              <input
                placeholder="ex) 소프트웨어학과"
                className=" font-Pretendard_Light focus:outline-none bg-slate-300 text-white placeholder:text-white rounded-md p-1 "
                onChange={(e) => setRequestDepartment(e.target.value)}
              />
            </div>

            <div className="flex justify-center gap-4 pt-4">
              <button
                className="py-3 px-5 w-fit text-base font-Pretendard_Normal text-white bg-scarlet rounded-[50px] hover:cursor-pointer"
                onClick={onClickHandler}
              >
                제출하기
              </button>
            </div>
          </div>
        </Modal.Body>
      </Modal>

      <div className="flex flex-col gap-16">
        <AuthTitle
          title={"School"}
          firstContent={"이제 두번째 단계입니다."}
          secondContent={"학교 관련 정보를 입력해주세요."}
          titleColor={"white"}
          contentColor={"white"}
        />
        <div className="flex flex-col gap-12">
          <div className="gap-1">
            <p className=" font-Pretendard_Black block text-xl text-white">
              학교명
            </p>
            <p className="border-b border-white font-Pretendard_Light text-gray-300 text-xl pt-2 pb-2 bg-scarlet focus:outline-none w-full block placeholder:text-white">
              {universityName}
            </p>
          </div>
          <div className="flex flex-col gap-1">
            <p className=" font-Pretendard_Black block text-xl text-white">
              학과
            </p>

            <Search
              label={"학과를 선택해주세요."}
              placeHolder={"학과를 검색해주세요."}
              options={departmentInfo}
              onSelect={handleDropdown}
              setActiveNext={setActiveNext}
            />
            <div
              className="text-white font-Pretendard_Light hover:cursor-pointer"
              onClick={() => setOpenAlertModal(true)}
            >
              학과 추가 요청하기
            </div>
          </div>
          <div className="gap-1">
            <p className=" font-Pretendard_Black block text-xl text-white">
              학번
            </p>
            <input
              type="number"
              name="studentId"
              autoComplete="off"
              placeholder="학번을 입력해주세요."
              className={`border-b border-white font-Pretendard_Light text-white text-xl pt-2 pb-2 bg-scarlet focus:outline-none w-full block placeholder:text-white `}
              value={studentId}
              onChange={studentIdHandler}
            />
          </div>
        </div>
      </div>
    </>
  );
}

export default School;
