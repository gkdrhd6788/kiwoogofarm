import axios from "axios";
const token = localStorage.getItem("accessToken");
function localaxios() {
  const instance = axios.create({
    baseURL: "https://j10b303.p.ssafy.io/api",
    headers: {
      "Content-Type": "application/json;charset=utf-8",
    },
    "Access-Control-Allow-Origin": "*",
    "Access-Control-Allow-Credentials": true,
    withCredentials: true,
  });
  instance.defaults.headers.common["Authorization"] = `Bearer ${token}`;
  instance.defaults.headers.post["Content-Type"] = "application/json";
  instance.defaults.headers.put["Content-Type"] = "application/json";
  return instance;
}

export { localaxios };
