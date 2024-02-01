import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react-swc'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  build: {
    rollupOptions: {
      output: {
        assetFileNames: 'public/[name]-[hash][extname]'
      }
    },
    minify: 'esbuild', // 'terser' is the default minifier for production
  },
  base: '/'
})
