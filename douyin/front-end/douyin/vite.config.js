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
  },
  rules: [
    {
      test: /\.scss$/, // 匹配所有以 .scss 结尾的文件
      use: [
        'style-loader',     // 3️⃣ 把 CSS 插入到 <style> 标签中
        {
          loader: 'css-loader', // 2️⃣ 把 CSS 解析为 JS 中可用的模块
          options: { sourceMap: true }, // 开启 source map，方便调试
        },
        {
          loader: 'sass-loader', // 1️⃣ 把 SCSS 编译成 CSS
          options: { sourceMap: true },
        },
      ],
    },
  ]
});
