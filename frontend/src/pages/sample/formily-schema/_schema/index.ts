export const testschema = {
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
              width: '200',
              prefix: "{{icon('UserOutlined')}}",
            },
            'effects': ["onFieldInputValueChange"],
            'fulfill': {
              run: "console.log('xxx', $self.value);",
            }
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