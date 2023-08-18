// utils.js

export function isLogin() {
    // const token = localStorage.getItem("token"); // 获取登录凭证---localStorage只是存放前端使用的
    const token = getCookie("token"); // 获取登录凭证cookie
    return !!token; // 如果登录凭证存在，则认为用户已登录
}

export function getSuffix(path) {
    return path.split('.').pop();
}


export function setCookie(name, value, exMinutes) {
    //name  :表示cookie的名称，比如token
    //value  :表示cookie的值
    //exM  :表示cookie的有效时间 单位分钟
    const d = new Date();
    d.setTime(d.getTime() + (exMinutes * 60 * 1000));
    const expires = "expires=" + d.toGMTString();
    document.cookie = name + "=" + value + "; " + expires;
}

export function set5mCookie(name, value) {
    //name  :表示cookie的名称，比如token
    //value  :表示cookie的值
    const d = new Date();
    d.setTime(d.getTime() + (5 * 60 * 1000));
    const expires = "expires=" + d.toGMTString();
    document.cookie = name + "=" + value + "; " + expires;
}

export function getCookie(name) {
    let ret, m;
    if (typeof name === 'string' && name !== '') {
        if ((m = String(document.cookie).match(
            new RegExp('(?:^| )' + name + '(?:(?:=([^;]*))|;|$)')))) {
            ret = m[1] ? decodeURIComponent(m[1]) : ''
        }
    }
    return ret
}

export function removeCookie(name) {
    //name  :表示cookie的名称，比如token
    //value  :表示cookie的值
    const d = new Date();
    d.setTime(d.getTime() - (5 * 60 * 1000));
    const expires = "expires=" + d.toGMTString();
    document.cookie = name + "=;" + expires;
}