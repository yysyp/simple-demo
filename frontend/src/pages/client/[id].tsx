import { React, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { store } from '@/models/store';
import styles from './index.less';
import TanStackTable from './table';

import { Layout } from 'antd';

const { Header, Footer, Sider, Content } = Layout;

export default (props: any) => {
  return (
    <div className={`${styles.client}`}>
      <div className={styles['client-form']}>{props.match.params.id}</div>
    </div>
  );
};
