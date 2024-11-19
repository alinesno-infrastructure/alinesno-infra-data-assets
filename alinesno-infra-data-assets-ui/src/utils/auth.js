const TokenKey = 'Admin-Token';

// 获取token
export const getToken = () => {
  return localStorage.getItem(TokenKey);
};

// 设置token
export const setToken = (access_token) => {
  if (typeof access_token === 'string') {
    localStorage.setItem(TokenKey, access_token);
  } else {
    throw new Error('Access token must be a string');
  }
};

// 移除token
export const removeToken = () => {
  localStorage.removeItem(TokenKey);
};