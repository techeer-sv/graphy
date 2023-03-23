/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ['./src/**/*.{html,js,ts,tsx}'],
  theme: {
    extend: {
      width: {
        284: '17.75rem',
      },
      height: {
        49: '3.063rem',
        110: '6.875rem',
        228: '14.25rem',
      },
      minWidth: {
        284: '17.75rem',
      },
      maxWidth: {
        1100: '68.75rem',
      },
      minHeight: {
        96: '24rem',
      },
      fontFamily: {
        ng: ['NanumGothic', 'sans-serif'],
        'ng-b': ['NanumGothicBold', 'sans-serif'],
        'ng-eb': ['NanumGothicExtraBold', 'sans-serif'],
        'ng-l': ['NanumGothicLight', 'sans-serif'],
      },
    },
  },
  plugins: [],
};
