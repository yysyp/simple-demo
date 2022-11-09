import { defineConfig } from 'umi';

export default defineConfig({
  hash: true,
  history: {type: 'hash'},
  define: {
    reqBaseUrl: ''
  },
//   nodeModulesTransform: {
//     type: 'all',
//   },
  nodeModulesTransform: {
      type: 'none',
  },
//   nodeModulesTransform: {
//     type: 'none',
//     exclude: ['@umijs/utils'],
//   },
  dva: {
    immer: false,
  },
  targets: {
    ie: 11,
  },
  //base: '/', //default is '/', modify it when not deploy to root context path
  publicPath: '/umi/', //To make the generated 'index.html' to have href='/umi/xxx' and src='/umi/xxx'
  //runtimePublicPath: true,
  // routes: [
  //   { path: '/', component: '@/pages/index' },
  //   { path: '/todo', component: '@/pages/todo/index' },
  // ],
  fastRefresh: {},
  mfsu: {},
  outputPath: '../src/main/resources/static/umi',
});
