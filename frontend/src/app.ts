import React from 'react';
import {Provider} from 'react-redux';
import {history} from 'umi';
import type { History } from 'umi';

import {store} from '@/models/store';

export function rootContainer(container: any) {
    //@ts-ignore
    return React.createElement(Provider, { store }, container);
}