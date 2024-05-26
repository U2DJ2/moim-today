import ProfileSection from "../Manage/ProfileSection";
import { useEffect, useState } from "react";
import { GET } from "../../utils/axios";
function ManageProfilePage() {
  const [name, setName] = useState("");
  const [major, setMajor] = useState("");
  useEffect(() => {
    GET("/api/members/profile")
      .then((res) => {
        console.log(res);
        setName(res.username);
        setMajor(res.departmentName);
        console.log(major);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  return (
    <>
      <ProfileSection name={name} major={major} />
    </>
  );
}

export default ManageProfilePage;
