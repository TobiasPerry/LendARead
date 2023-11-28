/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      fontSize:{

      },
      textColor:{
        'primary': '#2B3B2B',
        'secondary': '#444444',
        'black': '#111711'
      },
      fontFamily:{
        'primary': ['Overpass', 'sans-serif']
      },
      colors: {
        'primary-gray': '#444444',
        'fluorescent-green': '#16df7e',
        'gray': '#D0DCD0',
        'dark-green': '#2B3B2B',
        'black-lend-a-read': '#111711',
        'light-green': '#D1E9C3',
      },
    }
  },
  plugins: [],
}