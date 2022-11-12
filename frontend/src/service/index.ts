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
    url: '/api/newstock/file/upload',
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

namespace SubmitStock {
  export type Req = {
    formData: FormData;
  };

  export type Res = CommonRes & {
    data?: {};
  };
}

const submitStock = (req: SubmitStock.Req): Promise<SubmitStock.Res> => {
  return request({
    url: '/api/newstock/file/upload',
    method: 'post',
    headers: { 'Content-Type': 'multipart/form-data' },
    data: req.formData,
  });
};

export {
  GetUser,
  SubmitFormDataTest,
  GetProduct,
  SubmitStock,
  submitStock,
  getProduct,
  submitFormDataTest,
  getUser,
};