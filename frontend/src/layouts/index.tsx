import React from 'react';
import styles from './index.less';
import { Layout } from 'antd';

const { Header, Footer, Sider, Content } = Layout;

const MyLayout: React.FC = (props) => {
  return (
    <div className={styles.main}>
      <Layout>
        <Header>
          Header
          <br />
          <hr />
        </Header>
        <Content>{props.children}</Content>
        <Footer>
          <hr />
          <br />
          Footer
        </Footer>
      </Layout>
    </div>
  );
};

export default MyLayout;
