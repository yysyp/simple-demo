import React, { useEffect, useMemo, useState } from 'react';
import { useHistory } from 'umi';
import { MyTable, MyTable2 } from '@/components/table';
import { useDispatch, useSelector } from 'react-redux';
import { store } from '@/models/store';
import { Button, notification } from 'antd';
import { history } from 'umi';

// const columns = [
//   {
//     Header: 'Age',
//     accessor: 'age',
//     Cell: ({ value, row }) => {
//       if (value > 30) {
//         return <div>{value}</div>;
//       } else {
//         return <div style={{ color: 'red' }}>{value}</div>;
//       }
//     },
//   },
// ];

const addCellFun = (columns: []) => {
  for (let col of columns) {
    if (col['Header'] == 'YOY') {
      col['Cell'] = ({ value, row }) => {
        if (value < 0) {
          return (
            <div style={{ background: 'lightyellow', color: 'red' }}>
              {value}
            </div>
          );
        } else {
          return <div style={{ background: 'lightyellow' }}>{value}</div>;
        }
      };
    } else if (col['Header'] == 'PctInATOrR') {
      col['Cell'] = ({ value, row }) => {
        if (value < 0) {
          return (
            <div style={{ background: 'lightpink', color: 'red' }}>{value}</div>
          );
        } else {
          return <div style={{ background: 'lightpink' }}>{value}</div>;
        }
      };
    } else if (col['Header'] == 'DeltaPctToX') {
      col['Cell'] = ({ value, row }) => {
        if (value < 0) {
          return (
            <div style={{ background: 'purple', color: 'red' }}>{value}</div>
          );
        } else {
          return <div style={{ background: 'purple' }}>{value}</div>;
        }
      };
    }
  }
};
export default (props: any) => {
  const stock = useSelector((state: any) => state.stock);

  const [calc, setCalc] = useState({ columns: [], tableData: [] });
  const [debt, setDebt] = useState({ columns: [], tableData: [] });
  const [benefit, setBenefit] = useState({ columns: [], tableData: [] });
  const [cash, setCash] = useState({ columns: [], tableData: [] });

  useEffect(() => {
    let respm = store.dispatch.stock.getReportData(stock.reportParam);

    respm
      .then((resp) => {
        console.log('---resp', resp);
        const calc = resp.data[0];
        addCellFun(calc.columns);
        setCalc(calc);
        const debt = resp.data[1];
        addCellFun(debt.columns);
        setDebt(debt);
        const benefit = resp.data[2];
        addCellFun(benefit.columns);
        setBenefit(benefit);
        const cash = resp.data[3];
        addCellFun(cash.columns);
        setCash(cash);
      })
      .catch((e) => {
        console.log('e', e);
      });
  }, []);

  return (
    <div>
      <Button
        onClick={() => {
          history.push('/stock/download');
        }}
      >
        To Download
      </Button>
      <br />
      <hr />
      <h3>Calc indications:</h3>
      <MyTable2 columns={calc.columns} data={calc.tableData}></MyTable2>
      <hr />
      <h3>Debt:</h3>
      <MyTable2 columns={debt.columns} data={debt.tableData}></MyTable2>
      <hr />
      <h3>Benefit:</h3>
      <MyTable2 columns={benefit.columns} data={benefit.tableData}></MyTable2>
      <hr />
      <h3>Cash:</h3>
      <MyTable2 columns={cash.columns} data={cash.tableData}></MyTable2>
    </div>
  );
};
