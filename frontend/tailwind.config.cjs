/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ['./src/**/*.{html,js,ts,tsx}'],
  theme: {
    extend: {
      spacing: {
        660: '660px',
        630: '630px',
        555: '555px',
        460: '460px',
        355: '350px',
        504: '54px',
      },

      colors: {
        graphybg: '#F9F8F8',
        graphyblue: '#505F9A',
        graphypink: '#CA92C7',
        mainbannerleft: '#678EF4',
        mainbannerright: '#FF93AE',
        subbanner: '#C1D0EF',
        gptbutton: '#7082CA',
        button: '#364A9A',
      },
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
        56: '14rem',
        96: '24rem',
      },
      fontFamily: {
        sans: ['NanumGothic', 'Arial', 'sans-serif'],
        ng: ['NanumGothic', 'sans-serif'],
        'ng-b': ['NanumGothicBold', 'sans-serif'],
        'ng-eb': ['NanumGothicExtraBold', 'sans-serif'],
        'ng-l': ['NanumGothicLight', 'sans-serif'],
        lef: ['LeferiBaseRegular', 'sans-serif'],
        'lef-b': ['LeferiBaseBold', 'sans-serif'],
        lato: ['LatoRegular', 'LatoRegular'],
        'lato-b': ['LatoBold', 'LatoBold'],
        'lato-l': ['LatoLight', 'LatoLight'],
        'lato-sb': ['LatoSemibold', 'LatoSemibold'],
      },
    },
  },
  plugins: [],
};
