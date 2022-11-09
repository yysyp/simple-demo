import React, { useEffect } from 'react';
import {
  createForm,
  onFieldChange,
  onFieldInitialValueChange,
  onFieldValueChange,
} from '@formily/core';
import {
  createSchemaField,
  FormConsumer,
  FormProvider,
  useFormEffects,
  Field,
  useField,
  observer,
} from '@formily/react';
import { SchemaField } from '@/components/form';
import { testschema } from './_schema';
import { Submit } from '@formily/antd';
import { store } from '@/models/store';

const initialValues = {
  username: 'aa',
  password: 'aaa',
};
export default () => {
  //用来创建表单核心领域模型，它是作为MVVM设计模式的标准 ViewModel
  const form = createForm({
    initialValues,
    effects() {
      onFieldValueChange('password', (field) => {
        console.log('password changed', field);
      });
    },
  });
  const field = useField();

  const handleSubmit = () => {
    //e.preventDefault();
    console.log('formsubmit', form.values);

    // let formData = new FormData();
    // formData.append('username', form.values.username);
    // formData.append('password', form.values.password);
    // store.dispatch.sharks.testSubmitFormData(formData);

    //const file = get(e, 'currentTarget.files.0');
    console.log('form', form);

    // const file = form.values.get('username').files[0];
    // let formData = new FormData();
    // formData.append('file', file);
    // store.dispatch.sharks.testSubmitFormData(formData);
  };

  return (
    <FormProvider form={form}>
      <SchemaField schema={testschema} />
      <Submit onSubmit={handleSubmit}>提交</Submit>
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
