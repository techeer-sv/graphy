const filesToCache = [
  '/',
  '/index.html',
  '/main.js',
  '/style.css',
  '/src/assets/image/imginsert.svg',
];
const CACHE_NAME = 'graphy';

self.addEventListener('install', (event) => {
  event.waitUntil(
    caches
      .open(CACHE_NAME)
      .then((cache) => {
        return cache.addAll(filesToCache).catch((error) => {
          console.error('Failed to add files to cache:', error);
        });
      })
      .catch((error) => {
        console.error('Failed to open cache:', error);
      }),
  );
});

self.addEventListener('fetch', function (event) {
  if (event.request.method !== 'GET') {
    event.respondWith(handlePostRequest(event.request));
  } else {
    event.respondWith(
      fetch(event.request)
        .then(function (response) {
          const responseToCache = response.clone();
          caches.open(CACHE_NAME).then(function (cache) {
            cache.put(event.request, responseToCache);
          });
          return response;
        })
        .catch(function () {
          return caches.match(event.request);
        }),
    );
  }
});

self.addEventListener('message', (event) => {
  if (event.data.action === 'cache-contains') {
    caches
      .open('my-cache')
      .then((cache) => cache.match(event.data.url))
      .then((match) => {
        // 메인 스레드에 메시지를 보내 캐시에 매치되는 요청이 있는지 알립니다.
        event.ports[0].postMessage({ hasMatch: !!match });
      });
  }
});

function handlePostRequest(request) {
  // POST 요청을 처리하는 코드 작성
  // fetch 함수를 사용하여 서버로 요청을 보내고 결과를 반환
  return fetch(request);
}
