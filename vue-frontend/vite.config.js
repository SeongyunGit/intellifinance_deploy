import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'

// https://vite.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    vueDevTools(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    },
  },
  build: {
      outDir: "../jwt-tutorial/src/main/resources/static",
    }, // 빌드 결과물이 생성되는 경로
    server: {
      proxy: {
        "/api": "http://localhost:8080",
      }, // proxy 설정
    },
})
