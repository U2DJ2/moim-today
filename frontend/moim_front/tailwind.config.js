/** @type {import('tailwindcss').Config} */
export default {
  content: ["./index.html", "./src/**/*.{js,ts,jsx,tsx}"],
  theme: {
    extend: {},
    fontFamily: {
      Praise: ["Praise"],
      Pretendard_Black: ["Pretendard-Black"],
      Pretendard_Normal: ["Pretendard-Normal"],
      Pretendard_Light: ["Pretendard-Light"],
    },
    colors: {
      scarlet: "#FF2440",
      white: "#ffffff",
    },
    rotate: {
      18: "18deg",
    },
  },
  plugins: [],
};
