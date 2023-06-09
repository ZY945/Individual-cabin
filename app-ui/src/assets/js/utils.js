// utils.js

export function isLogin() {
    const token = localStorage.getItem("token"); // 获取登录凭证
    return !!token; // 如果登录凭证存在，则认为用户已登录
}
