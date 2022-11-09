import {init} from '@rematch/core';
import {models} from ".";

export const store = init({models});
export type Store = typeof store;