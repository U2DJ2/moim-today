import React from "react";

function Button(props) {
  const { name } = props;
  return (
    <div className=" w-52 justify-center px-7 py-5 text-xl font-black text-center text-white whitespace-nowrap bg-scarlet rounded-[50px]">
      {name}
    </div>
  );
}

export default Button;
