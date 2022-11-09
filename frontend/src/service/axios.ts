import axios from 'axios';
import { get } from 'lodash';
import { history } from 'umi';
import { store } from '@/models/store';
import type { AxiosResponse, AxiosError } from 'axios';
import * as message from '@/util/message';

interface Request {
  url: string;
  params?: ObjectType;
  data?: ObjectType;
  method?: 'get' | 'post' | 'put' | 'delete';
  headers?: ObjectType;
}

export const request = ({
  url,
  params,
  data,
  method = 'get',
  headers,
}: Request) => {
  return new Promise<any>((resolve, reject) => {
    axios({ baseURL: reqBaseUrl, method, url, params, data, headers })
      .then(
        (response: AxiosResponse) => {
          resolve(response);
        },
        (err: AxiosResponse) => {
          const code: string = get(err, 'data.code');
          console.log('axios call err ', err);
          if (code != '200') {
            message.openError(err, 'axios call not 200');
            return Promise.reject(err);
          }
          reject(err);
        },
      )
      .catch((err: AxiosError) => {
        console.log('axios call err ', err);
        message.openError(err, 'axios call error');
        reject(err);
      });
  });
};

axios.interceptors.request.use(
  (config) => {
    return { ...config, headers: { ...config.headers } };
  },
  (err) => {
    console.log('axios req error', err);
    message.openError(err, 'axios request error');
    return Promise.reject(err);
  },
);

axios.interceptors.response.use(
  (res) => {
    console.log('axios res', res);
    const code: string = get(res, 'data.code');
    if (code != '200') {
      message.openError(res, 'axios response not 200');
      return Promise.reject(res);
    }
  },
  (err) => {
    console.log('axios res error', err);
    message.openError(err, 'axios response error');
    return Promise.reject(err);
  },
);
