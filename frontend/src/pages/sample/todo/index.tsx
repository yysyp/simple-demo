import Dispatcher from '@/.umi/plugin-model/helpers/dispatcher';
import {React, useEffect} from 'react';
import { useDispatch, useSelector } from 'react-redux';
import {store} from '@/models/store';


export default function Todo() {

    useEffect(() => {
      store.dispatch.sharks.initNum(100000);
    }, []);

    const sharks = useSelector((state: any) => state.sharks);

    return (
      <div>
        <h1>Todo index</h1>
        <button onClick={() => store.dispatch.sharks.incrementAsync(1)}>count increment</button>
        <button onClick={() => store.dispatch.sharks.decrementAsync(1)}>count decrement</button>
        <div>{sharks}</div>
      </div>
    );

  }