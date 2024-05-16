import flowbite from "flowbite-react/tailwind";

/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
    "./node_modules/react-tailwindcss-datepicker/dist/index.esm.js",
    flowbite.content(),
  ],
  theme: {
    extend: {
      colors: {
        scarlet: "#FF2440",
        white: "#ffffff",
        black: "#000000",
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
      gridTemplateColumns: {
        // Simple 16 column grid
        16: "repeat(16, minmax(0, 1fr))",

        // Complex site-specific column configuration
        card: "repeat(3, minmax(0, 1fr))",
      },
    },
  },
  plugins: [
  ],
  darkMode: '',
};
