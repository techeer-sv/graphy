import { URL } from 'https://jslib.k6.io/url/1.0.0/index.js';
import http from 'k6/http';
import { check, group, sleep, fail } from 'k6';

export let options = {
    stages: [
        { duration: '3m', target: 25 },     // 먼저 3분 동안 VUser 1에서 25까지 서서히 올린다.
        { duration: '10m',target: 25 },     // Vuser 25에서 10분간 유지한다.
        { duration: '3m', target: 125 },    // 다시 3분간 25에서 125까지 서서히 올린다.
        { duration: '10m',target: 125 },    // 30분간 유지
        { duration: '3m', target: 0 },      // 3분 동안 Vuser 0으로 내려온다.
    ],

    thresholds: {                           // 부하 테스트가 언제 성공했다고 할 수 있는지
        http_req_duration: ['p(95)<138'],   // 전체 요청의 95%가 138ms 안에 들어오면 성공
    },
};

const BASE_URL = 'http://localhost:8080/api/v1/projects';

function getProjects() {
    var page = Math.floor(Math.random() * 3);
    var size = 4;
    var url = new URL(`${BASE_URL}/search`);
    url.searchParams.append('page', page);
    url.searchParams.append('size', 4);

    let pathRes = http.get(url.toString(), {
        tags: {
            page_name: "get_projects",
        },
    });

    check(pathRes, {                    // 결과를 체크
        'success to get projects': (res) => res.status === 200,
    });
}

function getProjectDetail(){
    var projectId = Math.floor(Math.random() * 10 + 1);
    var url = new URL(`${BASE_URL}/${projectId}`);

    let pathResultRes = http.get(url.toString(), {
        tags: {
            page_name: "get_project_detail",
        },
    });

    check(pathResultRes, {
        'success to get project detail': (res) => res.status === 200
    });
}

export default function ()  {
    getProjects();
    getProjectDetail();
};