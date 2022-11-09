import axios from "axios";
import { get } from "lodash";
import {history} from 'umi';
import {store} from '@/models/store';
import type { AxiosResponse, AxiosError } from "axios";

interface Request {
    url: string;
    params?: ObjectType;
    data?: ObjectType;
    method?: 'get' | 'post' | 'put' | 'delete';
    headers?: ObjectType;
}

export const request = ({url, params, data, method='get', headers}: Request) => {

    return new Promise<any>((resolve, reject) => {
        axios({baseURL: reqBaseUrl,
        method,
        url,
        params,
        data,
        headers,
    }).then((response: AxiosResponse) => {resolve(response);},
        (err: AxiosResponse) => {
            const code: string = get(err, 'data.status');
            console.log("axios call err ", err);
            reject(err);
        }).catch((error: AxiosError) => {
            console.log("axios call err ", error);
            reject(error);
        });
        
    });
};

axios.interceptors.request.use((config) => {
    return {...config, headers: {...config.headers}};
}, (err) => {
    console.log("axios req error", err);
    return Promise.reject(err);
});

axios.interceptors.response.use((res) => {

    console.log("axios res", res);

}, (err) => {
    console.log("axios res error", err);
    return Promise.reject(err);
});