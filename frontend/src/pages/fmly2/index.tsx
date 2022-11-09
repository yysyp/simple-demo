import React, { useMemo, useState } from 'react';
import { createForm } from '@formily/core';
import { Field } from '@formily/react';
import {
  Form,
  FormItem,
  //Input,
  Password,
  Submit,
  FormButtonGroup,
} from '@formily/antd';
import { Button, Card } from 'antd';
import { store } from '@/models/store';
import { useHistory } from 'umi';
import { TextInput as Input, Upload } from '@/components/form';

export default () => {
  const history = useHistory();
  const [file, setFile] = useState();

  const form = useMemo(() => {
    return createForm({
      validateFirst: true,
    });
  }, []);

  const handleSubmit = () => {
    form.validate().then(() => {
      let formData = new FormData();
      formData.append('file', file as any);
      //formData.append('age', form.values.age);
      store.dispatch.sharks.testSubmitFormData(formData).then(
        () => {
          history.push('/todo');
        },
        (err) => {
          console.log('Error', err);
        },
      );
    });
  };

  return (
    <div
      style={{
        display: 'flex',
        justifyContent: 'center',
        background: '#eee',
        padding: '40px 0',
      }}
    >
      <Card title="变更密码" style={{ width: 620 }}>
        <Form
          form={form}
          labelCol={5}
          wrapperCol={16}
          //onAutoSubmit={console.log}
          onAutoSubmit={handleSubmit}
        >
          <Field
            name="username"
            title="用户名"
            validator={[
              { required: true, message: 'username is required!' },
              { maxLength: 6, message: 'maxiumn length is 6!' },
            ]}
            decorator={[FormItem]}
            component={[Input]}
          />
          <Field
            name="email"
            title="邮箱"
            // validator="email"
            validator={[
              { required: true, message: 'email is required!' },
              { format: 'email', message: 'Email is invalid!' },
            ]}
            decorator={[FormItem]}
            component={[Input]}
          />
          <Field
            name="file"
            title="文件"
            // validator="email"
            validator={[{ required: true, message: 'file is required!' }]}
            decorator={[FormItem]}
            component={[
              Upload,
              {
                onChangex: (f: any) => {
                  setFile(f);
                },
              },
            ]}
          />
          {/* <Input type="file" /> */}
          {/* <Field
            name="file"
            title="上传文件"
            //required
            decorator={[FormItem]}
            component={[UploadInput, {withCount:10, width: 200}]}
            reactions={(field) => {
              //const file = field.query('.file');
              //field.selfErrors = file.get('value') ? '' : 'Required!!';
              //const fileval = field.query('file').fileList;
              //console.log('reaction.fileval', fileval);
            }}
          /> */}
          <Field
            name="old_password"
            title="原始密码"
            required
            decorator={[FormItem]}
            component={[Password]}
          />
          <Field
            name="password"
            title="新密码"
            required
            decorator={[FormItem]}
            component={[
              Password,
              {
                checkStrength: true,
              },
            ]}
            reactions={(field) => {
              const confirm = field.query('.confirm_password');
              field.selfErrors =
                confirm.get('value') &&
                field.value &&
                field.value !== confirm.get('value')
                  ? '确认密码不匹配'
                  : '';
            }}
          />
          <Field
            name="confirm_password"
            title="确认密码"
            required
            decorator={[FormItem]}
            component={[
              Password,
              {
                checkStrength: true,
              },
            ]}
            reactions={(field) => {
              const confirm = field.query('.password');
              field.selfErrors =
                confirm.get('value') &&
                field.value &&
                field.value !== confirm.get('value')
                  ? '确认密码不匹配'
                  : '';
            }}
          />

          <FormButtonGroup.FormItem>
            <Submit block size="large">
              确认变更
            </Submit>
          </FormButtonGroup.FormItem>
        </Form>
      </Card>
    </div>
  );
};
