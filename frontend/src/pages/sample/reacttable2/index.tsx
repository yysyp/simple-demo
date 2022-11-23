import React, { useMemo } from 'react';
import { useTable } from 'react-table';
import Table from './Table';

//https://github.com/TanStack/table/blob/v7/examples/sorting/src/App.js
export default function App() {
  const data = useMemo(
    () => [
      {
        name: '蒋铁柱',
        address: '北京市海淀区西三环中路19号',
        date: '2022-07-01',
        order: '1596694478675759682',
      },
      {
        name: '陈成功',
        address: '湖北武汉武昌区天子家园',
        date: '2022-06-27',
        order: '1448752212249399810',
      },
      {
        name: '宋阿美',
        address: '湖北武汉武昌区天子家园',
        date: '2022-06-21',
        order: '1171859737495400477',
      },
      {
        name: '张小乐',
        address: '北京市海淀区北航南门',
        date: '2022-06-30',
        order: '1096242976523544343',
      },
      {
        name: '马国庆',
        address: '北京市海淀区花园桥东南',
        date: '2022-06-12',
        order: '1344783976877111376',
      },
      {
        name: '小果',
        address: '广州天河机场西侧停车场',
        date: '2022-06-07',
        order: '1505069508845600364',
      },
    ],
    [],
  );

  const columns = useMemo(
    () => [
      {
        Header: '订单编号',
        accessor: 'order',
      },
      {
        Header: '姓名',
        accessor: 'name',
      },
      {
        Header: '收货地址',
        accessor: 'address',
      },
      {
        Header: '下单日期',
        accessor: 'date',
      },
    ],
    [],
  );

  return (
    <div>
      <h1>React Table Demo2</h1>
      <Table columns={columns} data={data}></Table>
    </div>
  );
}
