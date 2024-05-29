import ProfileSection from "../Manage/ProfileSection";
import { useEffect, useState } from "react";
import { GET } from "../../utils/axios";
function ManageProfilePage() {
  const [name, setName] = useState("");
  const [major, setMajor] = useState("");
  const [profileImg, setProfileImg] = useState("");

  useEffect(() => {
    GET("/api/members/profile")
      .then((res) => {
        setName(res.username);
        setMajor(res.departmentName);
        setProfileImg(res.memberProfileImageUrl);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  return (
    <>
      <ProfileSection profileImg={profileImg} name={name} major={major} />
    </>
  );
}

export default ManageProfilePage;
