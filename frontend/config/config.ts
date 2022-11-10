import { defineConfig } from 'umi';

export default defineConfig({
  hash: true,
  history: { type: 'hash' },
  define: {
    reqBaseUrl: '',
  },
  nodeModulesTransform: {
    type: 'none',
  },

  // nodeModulesTransform: {
  //   type: 'all',
  //   exclude: ['@umijs/utils'],
  // },
  //   nodeModulesTransform: {
  //     type: 'none',
  //     exclude: ['@umijs/utils'],
  //   },
  // dva: {
  //   immer: false,
  // },
  // targets: {
  //   ie: 11, // 支持IE11
  // },
  // headScripts: [
  //   'http://polyfill.alicdn.com/v3/polyfill.js', // or https://polyfill.io/v3/polyfill.min.js
  // ],
  //legacy: {},

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
