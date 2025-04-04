import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";

// https://vite.dev/config/
export default defineConfig({
  plugins: [vue()],
  server: {
    port: 5378,
    proxy: {
      '/api': {
        target: 'http://localhost:8081/douyin',
        // 把路径中的 /api 去掉。
        rewrite: (path) => path.replace(/^\/api/,""),
        changeOrigin: true
      }
    }
  }
});
