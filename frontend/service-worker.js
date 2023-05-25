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
        return cache
          .addAll(
            filesToCache.map((file) => new Request(file, { cache: 'reload' })),
          )
          .catch((error) => {
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
      .open(CACHE_NAME)
      .then((cache) => cache.match(event.data.url))
      .then((match) => {
        event.ports[0].postMessage({ hasMatch: !!match });
      });
  }
});

function handlePostRequest(request) {
  return fetch(request);
}
