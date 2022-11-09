
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


#Move & rename .umirc.ts to config/config.ts
cnpm install cross-env
Umi 的配置在 .umirc.ts 或 config/config.ts (二选一，.umirc.ts 优先级更高)文件中。
运行顺序：global.tsx > app.tsx > umi.ts


#http request: 
cnpm install axios
cnpm i --save lodash
cnpm i --save xss

#formily
cnpm install --save @formily/core
cnpm install --save @formily/react
cnpm install --save antd moment @formily/antd


#react table:
cnpm install @tanstack/react-table

#Visit:
http://localhost:8000/#/todo

#others:
cnpm install @tanstack/react-table
cnpm install styled-components
cnpm install --save @ant-design/icons

#References:
https://formilyjs.org/zh-CN/guide
https://core.formilyjs.org/zh-CN/api/entry/create-form

