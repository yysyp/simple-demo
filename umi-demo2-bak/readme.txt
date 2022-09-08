
#Init with umi-app template:
cnpm i yarn tyarn -g
mkdir umi-demo2
tyarn create @umijs/umi-app


#build & run
cnpm i
cnpm run start


#install redux rematch:
cnpm install --save redux react-redux
cnpm install @rematch/core


Umi 的配置在 .umirc.ts 或 config/config.ts (二选一，.umirc.ts 优先级更高)文件中。


运行顺序：global.tsx > app.tsx > umi.ts
https://segmentfault.com/a/1190000039705975