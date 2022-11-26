import { createModel } from '@rematch/core';
import { plugin } from 'umi';
import * as service from '@/service';

export const stock = createModel()({
  state: {
    pageParam: {
      queryKey: '' as string,
      currentPage: 1 as number,
      pageSize: 10 as number,
    },
    reportParam: {
      companyCode: '' as string,
      fromYear: (new Date().getFullYear() - 3) as number,
      toYear: new Date().getFullYear() as number,
      months: '3,6,9,12' as string,
    },
  },
  reducers: {
    updatePage: (state, payload) => {
      return {
        ...state,
        pageParam: { ...state.pageParam, currentPage: payload },
      };
    },
    updateReportParam: (state, payload) => {
      return {
        ...state,
        reportParam: { ...payload },
      };
    },
  },
  effects: (dispatch) => ({
    async submitStock(payload) {
      let resp = service.submitStock({ formData: payload });
      return resp;
    },

    async downloadStock(payload) {
      let resp = service.downloadStock(payload);
      return resp;
    },

    async getReportData(payload) {
      let resp = await service.getReportData(payload);

      return resp;
    },
    async deleteByCompanyCode(payload) {
      let resp = await service.deleteByCompanyCode(payload);

      return resp;
    },
  }),
});
