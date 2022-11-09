import React from 'react';
import { createForm } from '@formily/core';
import { createSchemaField, FormConsumer, FormProvider } from '@formily/react';
import {
  FormItem,
  Input,
  Password,
  Submit,
  FormLayout,
  FormButtonGroup,
} from '@formily/antd';
import * as ICONS from '@ant-design/icons';
import Upload from './upload';
import TextInput from './textInput';
import { FormLayout as MyFormLayout } from './formLayout';

// 创建一个 SchemaField 组件用于解析JSON-Schema动态渲染表单的组件
export const SchemaField = createSchemaField({
  // 组件列表
  components: {
    FormLayout,
    FormItem,
    MyFormLayout,
    TextInput,
    Password,
    Upload,
  },
  // 全局作用域，用于实现协议表达式变量注入
  scope: {
    icon(name: string) {
      return React.createElement(ICONS[name]);
    },
  },
});

export { TextInput, Upload, MyFormLayout };
