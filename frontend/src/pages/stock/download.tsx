import React, { useMemo, useState, useEffect } from 'react';
import { useHistory } from 'umi';
import { createForm } from '@formily/core';
import { Field } from '@formily/react';

import styles from './index.less';
import { history } from 'umi';
import { Button, notification } from 'antd';
import type { NotificationPlacement } from 'antd/es/notification';
import { openNotification, openError } from '@/util';
import { Form, FormItem, Select, Input } from '@formily/antd';
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
      initialValues: {
        fromYear: new Date().getFullYear() - 3,
        toYear: new Date().getFullYear(),
      },
    });
  }, []);

  const stock = useSelector((state: any) => state.stock);
  //useEffect(() => {}, []);
  const [spinning, setSpinning] = useState(false);
  const [submitResult, setSubmitResult] = useState('');

  const handleSubmit = () => {
    setSpinning(true);
    setSubmitResult('');
    form
      .validate()
      .then(() => {
        const link = document.createElement('a');
        link.style.display = 'none';
        link.href =
          '/api/newstock/file/export?companyCode=' +
          form.values.companyCode +
          '&fromYear=' +
          form.values.fromYear +
          '&toYear=' +
          form.values.toYear +
          '&months=' +
          form.values.reportMonths;
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
        setSpinning(false);
        // store.dispatch.stock
        //   .downloadStock({
        //     companyCode: form.values.companyCode,
        //     month: form.values.reportMonth,
        //   })
        //   .then((res) => {
        //     setSpinning(false);
        //     setSubmitResult('Success');
        //   })
        //   .catch((err) => {
        //     setSpinning(false);
        //     setSubmitResult(JSON.stringify(err));
        //   });
      })
      .catch((err: any) => {
        setSpinning(false);
        setSubmitResult(JSON.stringify(err));
      });
  };

  const handleToReport = () => {
    setSpinning(true);
    setSubmitResult('');
    form
      .validate()
      .then(() => {
        store.dispatch.stock.updateReportParam({
          companyCode: form.values.companyCode,
          fromYear: form.values.fromYear,
          toYear: form.values.toYear,
          months: form.values.reportMonths,
        });
        history.push('/stock/report');
        setSpinning(false);
      })
      .catch((err: any) => {
        setSpinning(false);
        setSubmitResult(JSON.stringify(err));
      });
  };

  const handleDeleteByCompanyCode = () => {
    setSpinning(true);
    form
      .validate()
      .then(() => {
        store.dispatch.stock
          .deleteByCompanyCode({
            companyCode: form.values.companyCode,
          })
          .then(() => {
            setSpinning(false);
          })
          .catch((err: any) => {
            setSpinning(false);
            setSubmitResult(JSON.stringify(err));
          });
      })
      .catch((err: any) => {
        setSpinning(false);
        setSubmitResult(JSON.stringify(err));
      });
  };

  return (
    <div className={styles.container}>
      <Spin spinning={spinning} tip="Loading...">
        <div className={styles.content}>
          <h1 className={styles.title}>stock index</h1>
          <div>{submitResult}</div>

          <Button
            onClick={() => {
              history.push('/stock');
            }}
          >
            To Upload
          </Button>
          <Button onClick={handleDeleteByCompanyCode}>
            Delete By CompanyCode
          </Button>
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
                name="fromYear"
                title="From Year"
                initialValue={new Date().getFullYear() - 3}
                //validator={[{ len: 4, message: 'From Year length is 4!' }]}
                decorator={[FormItem]}
                component={[Input]}
                value={new Date().getFullYear() - 3}
                //component={[TextInput]}
              />

              <Field
                name="toYear"
                title="To Year"
                initialValue={new Date().getFullYear()}
                //validator={[{ len: 4, message: 'To Year length is 4!' }]}
                decorator={[FormItem]}
                component={[Input]}
                value={new Date().getFullYear()}
                //component={[TextInput]}
              />

              <Field
                name="reportMonths"
                title="Report Months"
                decorator={[FormItem]}
                initialValue={12}
                dataSource={[
                  { label: '12', value: '12' },
                  { label: '9', value: '9' },
                  { label: '6', value: '6' },
                  { label: '3', value: '3' },
                ]}
                component={[Select, { mode: 'multiple' }]}
              />

              <Button onClick={handleSubmit}>Download</Button>
              <Button onClick={handleToReport}>ToReport</Button>
            </MyFormLayout>
          </Form>
        </div>
      </Spin>
    </div>
  );
};
