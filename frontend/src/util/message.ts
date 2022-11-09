import { isObject, map, forIn } from 'lodash';
import xss from 'xss';
import { MESSAGE_TYPE } from '@/constants/messagetype';
import { notification } from 'antd';
import type { NotificationPlacement } from 'antd/es/notification';
import { InfoCircleFilled } from '@ant-design/icons';

type NotificationType = 'success' | 'info' | 'warning' | 'error';

export const openInfo = (message: any, title: string = 'Info') => {
  openNotification('info', title, message, 'top');
};

export const openWarning = (message: any, title: string = 'Warning') => {
  openNotification('warning', title, message, 'top');
};

export const openError = (message: any, title: string = 'Error') => {
  openNotification('error', title, message, 'top');
};

export const openNotification = (
  type: NotificationType,
  title: string,
  message: any,
  placement: NotificationPlacement = 'top',
) => {
  let str;
  if (isObject(message)) {
    str = JSON.stringify(message);
  } else {
    str = message;
  }

  notification[type]({
    message: title,
    description: str,
    duration: 3,
    placement,
  });
};
