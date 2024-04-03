// tailwind.config.js
module.exports = {
  purge: ['./index.html', './src/**/*.{vue,js,ts,jsx,tsx}'],
  darkMode: false, // or 'media' or 'class'
  theme: {
    extend: {},
    fontFamily: {
      Notosans: ["Noto Sans KR"],
    },
    //fontFamily 추가
  },
  variants: {
    extend: {}
  },
  plugins: []
};
