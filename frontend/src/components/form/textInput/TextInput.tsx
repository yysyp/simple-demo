import React, { useState } from 'react';
import { connect, mapProps, mapReadPretty } from '@formily/react';
import { find, get, includes, keys, some, omit } from 'lodash';
import styles from './index.less';

export const TextInput = connect(
  //React.forwardRef((props: any, ref: React.Ref<React.RefObject<HTMLInputElement>> | undefined) => {...})
  (props: any) => {
    const { errors, ...rest } = props;
    return (
      <input
        placeholder={props.placeholder || 'Please Input'}
        className={
          styles.TextInput +
          ' ' +
          (errors.length == 0 ? styles.TextInputOk : styles.TextInputErr)
        }
        onChange={props.onChange}
        //value={props.value} /*devScripts.js:6523 Warning: A component is changing an uncontrolled input to be controlled.*/
      ></input>
    );
  },
  mapProps((props, formField) => {
    const validator = get(formField, 'validator');
    let maxLength;
    let required = false;
    if (validator) {
      const maxLengthItem = find(validator, (item: any) =>
        includes(keys(item), 'maxLength'),
      );
      maxLength = maxLengthItem?.maxlength;
      required = some(validator, (item: any) => item.required);
    }
    return {
      //@ts-ignore
      invalid: formField?.selfInvalid,
      //@ts-ignore
      errors: formField?.errors,
      name: get(formField, 'props.name'),
      maxLength,
      required,
    };
  }),
  mapReadPretty((props: any) => {
    return <span>{get(props, 'value')}</span>;
  }),
);
