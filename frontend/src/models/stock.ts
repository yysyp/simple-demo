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
  },
  reducers: {
    updatePage: (state, payload) => {
      return {
        ...state,
        pageParam: { ...state.pageParam, currentPage: payload },
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
  }),
});
