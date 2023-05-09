const filesToCache = ['/', '/index.html', '/main.js', '/style.css'];
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
    // POST 요청은 캐싱하지 않음
    return fetch(event.request);
  }
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
});
