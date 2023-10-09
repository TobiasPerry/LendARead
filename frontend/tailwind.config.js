/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        'primary-gray': '#444444',
        'fluorescent-green': '#16df7e',
        'gray': '#D0DCD0',
        'dark-green': '#2B3B2B',
        'light-green': '#D1E9C3',
      },
    }
  },
  plugins: [],
}