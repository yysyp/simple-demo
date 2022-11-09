import { defineConfig } from 'umi';

export default defineConfig({
  devtool: 'source-map',
  define: {
    ENV: 'dev',
    reqBaseUrl: 
    //'http://localhost:8000'
    ''
  },
  proxy: {
    "/api": {
        "target": "http://localhost:8080",
        "secure": false,
        "changeOrigin": true,
        //"pathRewrite": {"^/px": ""}
    }
  },
});
