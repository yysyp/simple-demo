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
import UploadInput from '@/components/form/upload';

//用来创建表单核心领域模型，它是作为MVVM设计模式的标准 ViewModel
const form = createForm();

// 创建一个 SchemaField 组件用于解析JSON-Schema动态渲染表单的组件
const SchemaField = createSchemaField({
  // 组件列表
  components: {
    FormLayout,
    FormItem,
    Input,
    Password,
    UploadInput,
  },
  // 全局作用域，用于实现协议表达式变量注入
  scope: {
    icon(name: string) {
      return React.createElement(ICONS[name]);
    },
  },
});

/**初始化一份json schema
 * 解析 json-schema 的能力；将 json-schema 转换成 Field Model 的能力；编译 json-schema 表达式的能力
 **/
const schema = {
  // schema type
  type: 'object',
  properties: {
    layout: {
      type: 'void',
      'x-component': 'FormLayout',
      'x-component-props': {
        labelCol: 2,
        wrapperCol: 6,
        labelAlign: 'right',
        // layout: 'vertical',
      },
      // 属性描述
      properties: {
        username: {
          // schema type
          type: 'string',
          // 标题
          title: '用户名',
          // 必填
          required: true,
          // 字段 UI 包装器组件
          'x-decorator': 'FormItem',
          // 字段 UI 组件
          'x-component': 'UploadInput',
          // 字段 UI 组件属性
          'x-component-props': {
            prefix: "{{icon('UserOutlined')}}",
          },
        },
        password: {
          type: 'string',
          title: '密码',
          required: true,
          'x-decorator': 'FormItem',
          'x-decorator-props': {
            addonAfter: '强度高',
          },
          'x-component': 'Password',
          'x-component-props': {
            prefix: "{{icon('LockOutlined')}}",
          },
        },
      },
    },
  },
};

export default () => {
  return (
    <FormProvider form={form}>
      <FormLayout layout="vertical">
        <SchemaField schema={schema} />
      </FormLayout>
      <FormButtonGroup>
        <Submit onSubmit={console.log}>提交</Submit>
      </FormButtonGroup>
      <FormConsumer>
        {() => (
          <div
            style={{
              width: 340,
              marginTop: 20,
              padding: 5,
              border: '1px dashed #666',
            }}
          >
            实时响应-用户名：{form.values.username}
          </div>
        )}
      </FormConsumer>
    </FormProvider>
  );
};
