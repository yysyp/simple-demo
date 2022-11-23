import React, { useMemo, useState } from 'react';
import { useHistory } from 'umi';
import { createForm } from '@formily/core';
import { Field } from '@formily/react';

import styles from './index.less';
import { history } from 'umi';
import { Button, notification } from 'antd';
import type { NotificationPlacement } from 'antd/es/notification';
import { openNotification, openError } from '@/util';
import { Form, FormItem } from '@formily/antd';
import { Upload, TextInput, MyFormLayout } from '@/components/form';
import { MyTable, MyTable2 } from '@/components/table';
import {
  createColumnHelper,
  flexRender,
  getCoreRowModel,
  useReactTable,
} from '@tanstack/react-table';

//start
type Person = {
  pid: number;
  firstName: string;
  lastName: string;
  age: number;
  visits: number;
  status: string;
  progress: number;
};

const defaultData: Person[] = [
  {
    pid: 111,
    firstName: 'tanner',
    lastName: 'linsley',
    age: 24,
    visits: 100,
    status: 'In Relationship',
    progress: 50,
  },
  {
    pid: 222,
    firstName: 'tandy',
    lastName: 'miller',
    age: 40,
    visits: 40,
    status: 'Single',
    progress: 80,
  },
  {
    pid: 233,
    firstName: 'joe',
    lastName: 'dirte',
    age: 45,
    visits: 20,
    status: 'Complicated',
    progress: 10,
  },
];

const columnHelper = createColumnHelper<Person>();

const columns = [
  columnHelper.accessor('firstName', {
    cell: (info) => info.getValue(),
    footer: (info) => info.column.id,
  }),
  columnHelper.accessor((row) => row.lastName, {
    id: 'lastName',
    cell: (info) => <i>{info.getValue()}</i>,
    header: () => <span>Last Name</span>,
    footer: (info) => info.column.id,
  }),
  columnHelper.accessor('age', {
    header: () => 'Age',
    cell: (info) => info.renderValue(),
    footer: (info) => info.column.id,
  }),
  columnHelper.accessor('visits', {
    header: () => <span>Visits</span>,
    footer: (info) => info.column.id,
  }),
  columnHelper.accessor('status', {
    header: 'Status',
    footer: (info) => info.column.id,
  }),
  columnHelper.accessor('progress', {
    header: 'Profile Progress',
    footer: (info) => info.column.id,
  }),
];

//for MyTable2
const columns2 = [
  {
    Header: 'PID',
    accessor: 'pid',
  },
  {
    Header: 'First Name',
    accessor: 'firstName',
  },
  {
    Header: 'Last Name',
    accessor: 'lastName',
  },
  {
    Header: 'Age',
    accessor: 'age',
    Cell: ({ value, row }) => {
      if (value > 30) {
        return <div>{value}</div>;
      } else {
        return <div style={{ color: 'red' }}>{value}</div>;
      }
    },
  },
];
//end

export default function IndexPage() {
  const history = useHistory();
  const [file, setFile] = useState();

  const form = useMemo(() => {
    return createForm({
      validateFirst: true,
    });
  }, []);

  const Btn = (props: any) => {
    return <Button>{props.row.original.pid}</Button>;
  };
  return (
    <div>
      <h1 className={styles.title}>Page index</h1>
      <button
        onClick={() => {
          openError('hahah');
        }}
      >
        Test btn1
      </button>
      <Form form={form}>
        <MyFormLayout width={'450px'}>
          <Field
            name="username"
            title="User Name"
            validator={[
              { required: true, message: 'username is required!' },
              { maxLength: 6, message: 'maxiumn length is 6!' },
            ]}
            decorator={[FormItem]}
            component={[TextInput]}
          />
          <Button
            onClick={() => {
              form.validate().then(() => {
                console.log('form.values', form.values);
              });
            }}
          >
            Submit
          </Button>
        </MyFormLayout>
      </Form>
      Table:
      <div>
        <MyTable data={defaultData} columns={columns}>
          <Btn></Btn>
        </MyTable>
      </div>
      <hr />
      <MyTable2 columns={columns2} data={defaultData}></MyTable2>
    </div>
  );
}
