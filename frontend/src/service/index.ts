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
    url: '/api/newstock/file/upload-files',
    method: 'post',
    headers: { 'Content-Type': 'multipart/form-data' },
    data: req.formData,
  });
};

namespace DownloadStock {
  export type Req = {
    companyCode: string;
    month: number;
  };

  export type Res = CommonRes & {
    data?: {};
  };
}

const downloadStock = (req: DownloadStock.Req): Promise<DownloadStock.Res> => {
  return request({
    url:
      '/api/newstock/file/export?companyCode=' +
      req.companyCode +
      '&month=' +
      req.month,
    method: 'get',
  });
};

namespace GetReportData {
  export type Req = {
    companyCode: string;
    fromYear: number;
    toYear: number;
    months: string;
  };

  export type Res = CommonRes & {
    data?: {};
  };
}
//http://localhost:8080/api/newstock/report/json?companyCode=600519&fromYear=2020&toYear=2022&months=12
const getReportData = (req: GetReportData.Req): Promise<GetReportData.Res> => {
  return request({
    url:
      '/api/newstock/report/json?companyCode=' +
      req.companyCode +
      '&fromYear=' +
      req.fromYear +
      '&toYear=' +
      req.toYear +
      '&months=' +
      req.months,
    method: 'get',
  });
};

namespace DeleteByCompanyCode {
  export type Req = {
    companyCode: string;
  };

  export type Res = CommonRes & {
    data?: {};
  };
}
const deleteByCompanyCode = (
  req: GetReportData.Req,
): Promise<GetReportData.Res> => {
  return request({
    url: '/api/newstock/report/delete?companyCode=' + req.companyCode,
    method: 'post',
  });
};

export {
  GetUser,
  SubmitFormDataTest,
  GetProduct,
  SubmitStock,
  DownloadStock,
  GetReportData,
  DeleteByCompanyCode,
  deleteByCompanyCode,
  getReportData,
  downloadStock,
  submitStock,
  getProduct,
  submitFormDataTest,
  getUser,
};
