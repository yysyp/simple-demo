import { React, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { store } from '@/models/store';

import { Layout } from 'antd';

const { Header, Footer, Sider, Content } = Layout;

export default (props: any) => {
  return <div>{props.match.params.optionalId}</div>;
};
