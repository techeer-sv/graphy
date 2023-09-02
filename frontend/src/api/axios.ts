import axios, { AxiosInstance } from 'axios';

const accessToken = sessionStorage.getItem('accessToken');
const persistToken = localStorage.getItem('accessToken');

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
    Authorization: `Bearer ${accessToken || persistToken}`,
  },
});

export { generalApi, tokenApi };
