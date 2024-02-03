import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react-swc'
import VitePluginHtmlEnv from 'vite-plugin-html-env'
import dotenv from 'dotenv'

dotenv.config()
const base = process.env.VITE_APP_BASE_PATH || '/'


// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
      react(),
      VitePluginHtmlEnv()
  ],
  build: {
    rollupOptions: {
      output: {
        assetFileNames: 'public/[name]-[hash][extname]'
      }
    },
    minify: 'esbuild', // 'terser' is the default minifier for production
  },
  base: base,
  test: {
    globals: true,
    environment: "jsdom",
    setupFiles: "./src/setupTests.ts"

  }
})
