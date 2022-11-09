import {isObject, map, forIn} from 'lodash';
import xss from 'xss';

export function setSession(k:string, v:any) {
    let str = '';
    if (isObject(v)) {
        str = JSON.stringify(v);
    } else {
        str = v;
    }
    sessionStorage.setItem(k, str);
}

export function getSession(k:string) {
    let isObj = false;
    const item = xss(sessionStorage.getItem(k) ?? '');
    let itemObj;
    try {
        itemObj = JSON.parse(item);
        isObj = isObject(itemObj);
    } catch (err) {
    }
    if (isObj) {
        return itemObj;
    }
    return item;
}

export function setLocal(k: string, v:any) {
    let str;
    if (isObject(v)) {
        str = JSON.stringify(v);
    } else {
        str = v;
    }
    localStorage.setItem(k, str);
}

export function getLocal(k:string) {
    let isObj = false;
    const item = xss(localStorage.getItem(k) ?? '');
    let itemObj;
    try {
        itemObj = JSON.parse(item);
        isObj = isObject(itemObj);
    } catch (err) {
    }
    if (isObj) {
        return itemObj;
    }
    return item;
}