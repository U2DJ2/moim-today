/** @type {import('tailwindcss').Config} */
export default {
  content: ["./index.html", "./src/**/*.{js,ts,jsx,tsx}"],
  theme: {
    extend: {
      colors: {
        scarlet: "#FF2440",
        white: "#ffffff",
        black: "#000000",
      },
    },
    fontFamily: {
      Praise: ["Praise"],
      Pretendard_Black: ["Pretendard-Black"],
      Pretendard_Light: ["Pretendard-Light"],
      Pretendard_Normal: ["Pretendard-Regular"],
      Pretendard_Medium: ["Pretendard-Medium"],
      Pretendard_SemiBold: ["Pretendard-SemiBold"],
    },
    rotate: {
      18: "18deg",
    },
  },
  plugins: [],
};
