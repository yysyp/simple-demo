import { request } from './axios';

namespace GetUser {
  export type Req = {
    id?: string;
  };

  export type Res = CommonRes & {
    data: {
      id: string;
      name: string;
      age: number;
    };
  };
}

const getUser = (data: GetUser.Req): Promise<GetUser.Res> =>
  request({ method: 'get', url: '/mock/api/user', data });

namespace SubmitFormDataTest {
  //...
}

const submitFormDataTest = (data: FormData): Promise<FormData> => {
  return request({
    method: 'post',
    url: '/api/upload/file?key=umijsdemo2',
    data: data,
    headers: { 'Content-Type': 'multipart/form-data' },
  });
};

namespace GetProduct {
  export type Req = {
    id?: string;
    page?: number;
  };

  export type Res = CommonRes & {
    data: {
      currentPage: number;
      count: number;
      products: [
        {
          id: string;
          name: string;
          price: number;
          desc: string;
          createDate: Date;
        },
      ];
    };
  };
}

const getProduct = (req: GetProduct.Req): Promise<GetProduct.Res> => {
  return request({
    method: 'get',
    url: '/mock/api/product',
    data: req,
  });
};

export {
  GetUser,
  SubmitFormDataTest,
  GetProduct,
  getProduct,
  submitFormDataTest,
  getUser,
};
