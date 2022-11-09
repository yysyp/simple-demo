import { defineConfig } from 'umi';

export default defineConfig({
  hash: true,
  history: {type: 'hash'},
  define: {
    reqBaseUrl: ''
  },
  nodeModulesTransform: {
    type: 'none',
  },
  //base: '/',
  //publicPath: '/',
  //runtimePublicPath: true,
  // routes: [
  //   { path: '/', component: '@/pages/index' },
  //   { path: '/todo', component: '@/pages/todo/index' },
  // ],
  fastRefresh: {},
  mfsu: {},
  outputPath: ''
});
