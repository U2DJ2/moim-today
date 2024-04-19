import React from "react";
import AuthTitle from "../Authentification/AuthTitle";
function AuthLeft(props) {
  const { title, firstContent, secondContent } = props;
  return (
    <>
      <AuthTitle
        title={title}
        firstContent={firstContent}
        secondContent={secondContent}
      />
    </>
  );
}

export default AuthLeft;
