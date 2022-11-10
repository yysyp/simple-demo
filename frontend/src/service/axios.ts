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
        // Here no need to handle again as it has been handled by axios.interceptors
        // ,
        // (err: AxiosResponse) => {
        //   const code: string = get(err, 'data.code');
        //   console.log('axios call err ', err);
        //   if (code != '200') {
        //     message.openError(err, 'axios call not 200');
        //     return Promise.reject(err);
        //   }
        //   reject(err);
        // },
      )
      .catch((err: AxiosError) => {
        // Here need to forward reject.
        console.log('axios call resolve err ', err);
        reject(err);
      });
  });
};

axios.interceptors.request.use(
  (config) => {
    return { ...config, headers: { ...config.headers } };
  },
  (err) => {
    //request timeout etc.
    console.log('===>>axios req error', err);
    message.openError(err, 'axios request error');
    return Promise.reject(err);
  },
);

axios.interceptors.response.use(
  (res) => {
    //http status is 200
    console.log('axios res', res);
    const code: string = get(res, 'data.code');
    if (code == '200') {
      message.openInfo(res.data.message, 'Info');
    } else {
      message.openError(res, 'axios response not 200');
      return Promise.reject(res);
    }
  },
  (err) => {
    //http status is not 200
    console.log('===>>axios res error', err);
    message.openError(err, 'axios response error');
    return Promise.reject(err);
  },
);
