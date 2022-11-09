import React, { forwardRef } from 'react';
import { useForm } from '@formily/react';
import classnames from 'classnames';
import './index.less';
import styled from 'styled-components';

type FormLayoutType = {
  width?: string;
};

export const FormLayout: React.FC<FormLayoutType> = (props) => {
  const form = useForm();
  const readPretty = form.readPretty;
  const widhtpx = props.width ? props.width : '500px';
  return (
    <div className="my-form" style={{ width: widhtpx }}>
      {props.children}
    </div>
  );
  //const cls = classnames('form-layout');
  // const tyledDiv = styled(
  //   forwardRef((props: any, ref) => {
  //     return <div>{props.children}</div>;
  //   }),
  // )`
  //   width: ${props.width};
  // `;
  // return tyledDiv;
};
