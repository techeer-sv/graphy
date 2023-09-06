import axios, { AxiosInstance } from 'axios';

// const accessToken = sessionStorage.getItem('accessToken');
// const persistToken = localStorage.getItem('accessToken');

const generalApi: AxiosInstance = axios.create({
  baseURL: 'http://localhost:8080/api/v1',
  headers: {
    'Content-Type': 'application/json',
  },
});

const tokenApi: AxiosInstance = axios.create({
  baseURL: 'http://localhost:8080/api/v1',
  headers: {
    'Content-Type': 'application/json',
  },
});

// tokenApi.interceptors.request.use(function (config) {
//   if (accessToken) {
//     config.headers.Authorization = `Bearer ${accessToken}`;
//   } else if (persistToken) {
//     config.headers.Authorization = `Bearer ${persistToken}`;
//   } else {
//     config.headers.Authorization = `Bearer ${persistToken}`;
//   }
//   return config;
// });

export { generalApi, tokenApi };
