import { createModel } from '@rematch/core';
import { plugin } from 'umi';

export type Product = {
  id: string;
  name: string;
  price: number;
  desc: string;
  createDate: Date;
};

export const products = createModel()({
  state: { products: [] as Product[] },
  reducers: {
    addProduct: (state, payload: Product) => {
      state.products.push(payload);
      return { ...state };
    },
  },
  effects: (dispatch) => ({
    async addProduct(payload: Product) {
      this.addProduct(payload);
    },
  }),
});
