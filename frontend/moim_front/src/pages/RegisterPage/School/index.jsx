import React, { useEffect, useState } from "react";
import AuthTitle from "../../../components/Authentification/AuthTitle";
import { GET } from "../../../utils/axios";
import axios from "axios";
import properties from "../../../config/properties";
import Dropdown from "../../../components/Dropdown/Simple";
import Search from "../../../components/Dropdown/Search";

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
  const getAllDepartment = axios.create({
    baseURL: properties.baseURL,
    withCredentials: true,
  });

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
          console.log(departmentNameList);
          setResult(res.data.data);
          setDepartmentInfo(departmentNameList);
        });
    } else {
      console.log("first");
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

  // const checkInputsFilled = (departmentValue, studentIdValue, departmentId) => {
  //   // 학과와 학번이 모두 채워졌는지 확인
  //   if (
  //     departmentValue.trim() !== "" &&
  //     studentIdValue.trim() !== "" &&
  //     departmentId != null
  //   ) {
  //     setActiveNext(true); // 모두 입력되면 setActiveNext를 true로 설정
  //   } else {
  //     setActiveNext(false); // 둘 중 하나라도 비어있으면 setActiveNext를 false로 설정
  //   }
  // };

  const handleDropdown = (index) => {
    console.log(result[index].departmentId);
    const id = result[index].departmentId;
    //Search에서 클릭한 id값을 가져옴
    setDepartmentId(id);
  };

  return (
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
        <div className="gap-1">
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
  );
}

export default School;
