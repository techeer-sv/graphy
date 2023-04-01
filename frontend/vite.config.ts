import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';
import dotenv from 'dotenv';

export default () => {
  // production 환경일 경우 .env.production 파일을 로드합니다.
  const env = dotenv.config({
    path: '../.env',
  }).parsed;

  return defineConfig({
    // Vite config 설정
    server: {
      host: '0.0.0.0',
      port: 3000,
    },
    plugins: [react()],
    define: {
      // 로드한 환경 변수를 Vite에 전달합니다.
      'process.env': Object.keys(env).reduce((acc, key) => {
        acc[key] = JSON.stringify(env[key]);
        return acc;
      }, {}),
    },
  });
};

// https://vitejs.dev/config/
