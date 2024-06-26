import axios, { AxiosInstance } from "axios";
import { RefreshTokenAxios } from "./user";

export const localAxios: AxiosInstance = axios.create({
  baseURL: "/api",
  headers: {
    "Content-Type": "application/json;charset=utf-8",
  },
  withCredentials: true,
});

// 사진 전송
export const imageAxios: AxiosInstance = axios.create({
  baseURL: "/api",
  headers: {
    "Content-Type": "multipart/form-data",
  },
  withCredentials: true,
});

// imageAxios 인스턴스에 대한 요청 인터셉터 추가
imageAxios.interceptors.request.use(
  (config) => {
    const token = sessionStorage.getItem("accessToken");
    if (token) {
      config.headers.Authorization = `${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Interceptors > 요청 전에 accessToken 찾아서 넣어줌
localAxios.interceptors.request.use(
  (config) => {
    const token = sessionStorage.getItem("accessToken");
    if (token) {
      config.headers.Authorization = `${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

const retryLimit = 2;

// 응답이 200 에러 아닌 경우 sessionStorage를 삭제 이후 다시 axios 요청
localAxios.interceptors.response.use(
  async (response) => {
    if (response.status === 200 || response.status === 201) {
      const accessToken: string | null = sessionStorage.getItem("accessToken");
      if (accessToken) {
        sessionStorage.setItem("accessToken", accessToken);
      }
    }
    return response;
  },
  async (error) => {
    const originConfig = error.config;
    if (!originConfig.retryCount) {
      originConfig.retryCount = 0;
    }

    if (originConfig.retryCount >= retryLimit) {
      return Promise.reject(error);
    }

    originConfig.retryCount += 1;

    try {
      const at: string | null = sessionStorage.getItem("accessToken");
      sessionStorage.removeItem("accessToken");
      if (at) {
        await RefreshTokenAxios(at).then((res) =>
          sessionStorage.setItem("accessToken", res.headers.authorization)
        );
      }
      return localAxios.request(originConfig);
    } catch (error) {
      return Promise.reject(error);
    }
  }
);
