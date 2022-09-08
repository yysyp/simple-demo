import {createModel} from "@rematch/core";

export const sharks = createModel()({
    state: 0,
    reducers: {
        increment: (state, payload) => state + payload,
        decrement: (state, payload) => state - payload
    },
    effects: (dispatch) => ({
        async incrementAsync(payload) {
            await new Promise((resolve) => setTimeout(resolve, 1000));
            dispatch.sharks.increment(payload);
        },
        async decrementAsync(payload) {
            await new Promise((resolve) => setTimeout(resolve, 1000));
            dispatch.sharks.decrement(payload);
        }

    })
});