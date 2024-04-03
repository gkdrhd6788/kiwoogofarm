import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
  ],

  server: {
    host: '0.0.0.0', // 모든 네트워크 인터페이스에 바인딩
    port: 5173 // 원하는 포트로 설정
  },

  build: {
    outDir: 'dist', // 빌드 결과물이 생성될 경로
  },

  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  }
})
