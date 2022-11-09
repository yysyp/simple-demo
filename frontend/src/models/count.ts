import { createModel } from '@rematch/core';
import { plugin } from 'umi';
import { GetUser, getUser, submitFormDataTest } from '@/service';

export const sharks = createModel()({
  state: 0,
  reducers: {
    increment: (state, payload) => state + payload,
    decrement: (state, payload) => state - payload,
    initNumRd: (state, payload) => {
      state = Math.floor(Math.random() * payload);
      return state;
    },
  },
  effects: (dispatch) => ({
    initNum(payload) {
      this.initNumRd(payload);
    },
    async incrementAsync(payload) {
      //await new Promise((resolve) => setTimeout(resolve, 1000));
      const res: GetUser.Res = await getUser({ id: '123' });
      console.log('incrementAsync', res);
      dispatch.sharks.increment(payload);
    },
    async decrementAsync(payload) {
      await new Promise((resolve) => setTimeout(resolve, 1000));
      dispatch.sharks.decrement(payload);
    },
    async testSubmitFormData(payload: FormData) {
      const res = await submitFormDataTest(payload);
      console.log('testSubmitFormData', res);
      return res;
    },
  }),
});
