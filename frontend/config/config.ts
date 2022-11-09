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
  dva: {
    immer: false,
  },
  targets: {
    ie: 11,
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
  outputPath: '../src/main/resources/static/umi',
});
