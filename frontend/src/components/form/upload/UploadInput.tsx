import React, { useMemo, useState } from 'react';
import { Form, Input, FormItem } from '@formily/antd';
import { Upload, UploadProps } from 'antd';
import { get, some } from 'lodash';
import { UploadOutlined } from '@ant-design/icons';
import {
  createSchemaField,
  FormProvider,
  connect,
  mapProps,
  mapReadPretty,
  useField,
} from '@formily/react';
import styles from './index.less';

export const UploadInput = connect(
  (props: any) => {
    let [file, setFile] = useState(null);
    let field = useField();
    const uploadProps: UploadProps = useMemo(() => {
      return {
        name: 'file',
        maxCount: 1,
        action: '#',
        beforeUpload(file, FileList) {
          return false;
        },
        onChange: (info) => {
          if (info.fileList.length === 0) {
            setFile(null);
            //@ts-ignore
            field.setValue('');
            props.onChangex(null);
          } else {
            setFile(info.file as any);
            //@ts-ignore
            field.setValue('1');
            props.onChangex(info.file);
          }
          //@ts-ignore
          field.validate();
        },
      };
    }, []);

    return (
      <Upload {...uploadProps}>
        <UploadOutlined />
        &nbsp;Upload
      </Upload>
    );
  },
  mapProps((props: any, field) => {
    const validator = get(field, 'validator');
    let required = false;
    if (validator) {
      required = some(validator, (item: any) => item.required);
    }
    return {
      name: get(field, 'props.name'),
      required,
      //@ts-ignore
      invalid: field?.selfInvalid,
      //@ts-ignore
      errors: field?.errors,
    };
  }),
  mapReadPretty((props: any) => {
    return <span>{props.value}</span>;
  }),
);
