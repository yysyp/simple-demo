import React, { useMemo, useState, useEffect } from 'react';
import { useHistory } from 'umi';
import { createForm } from '@formily/core';
import { Field } from '@formily/react';

import styles from './index.less';
import { history } from 'umi';
import { Button, notification } from 'antd';
import type { NotificationPlacement } from 'antd/es/notification';
import { openNotification, openError } from '@/util';
import { Form, FormItem, Select } from '@formily/antd';
import { Upload, TextInput, MyFormLayout } from '@/components/form';
import { MyTable } from '@/components/table';
import {
  createColumnHelper,
  flexRender,
  getCoreRowModel,
  useReactTable,
} from '@tanstack/react-table';
import { useDispatch, useSelector } from 'react-redux';
import { store } from '@/models/store';
import { Alert, Spin } from 'antd';

export default (props: any) => {
  const history = useHistory();
  const [file, setFile] = useState();
  const form = useMemo(() => {
    return createForm({
      validateFirst: true,
    });
  }, []);

  const stock = useSelector((state: any) => state.stock);
  //useEffect(() => {}, []);
  const [spinning, setSpinning] = useState(false);

  const handleSubmit = () => {
    setSpinning(true);
    form
      .validate()
      .then(() => {
        var formData = new FormData();
        formData.append('file', file);
        formData.append('companyCode', form.values.companyCode);
        formData.append('companyName', form.values.companyName);
        formData.append('kemuType', form.values.reportType);
        store.dispatch.stock
          .submitStock(formData)
          .then((res) => {
            console.log('stock page save sucess', res);
            setSpinning(false);
          })
          .catch((err) => {
            setSpinning(false);
          });
      })
      .catch((err: any) => {
        setSpinning(false);
      });
  };

  return (
    <div className={styles.container}>
      <Spin spinning={spinning} tip="Loading...">
        <div className={styles.content}>
          <h1 className={styles.title}>stock index</h1>

          <Form form={form}>
            <MyFormLayout width={'450px'}>
              <Field
                name="companyCode"
                title="Company Code"
                validator={[
                  { required: true, message: 'Company Code is required!' },
                  { maxLength: 6, message: 'Company Code length is 6!' },
                ]}
                decorator={[FormItem]}
                component={[TextInput]}
              />

              <Field
                name="companyName"
                title="Company Name"
                validator={[
                  { required: true, message: 'Company Name is required!' },
                ]}
                decorator={[FormItem]}
                component={[TextInput]}
              />

              <Field
                name="reportType"
                title="Report Type"
                validator={[
                  { required: true, message: 'Report Type is required!' },
                ]}
                decorator={[FormItem]}
                dataSource={[
                  { label: '', value: '' },
                  { label: 'Debt', value: 'debt' },
                  { label: 'Benefit', value: 'benefit' },
                  { label: 'Cash', value: 'cash' },
                ]}
                component={[Select]}
              />

              <Field
                name="file"
                title="File"
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

              <Button onClick={handleSubmit}>Submit</Button>
            </MyFormLayout>
          </Form>
        </div>
      </Spin>
    </div>
  );
};
