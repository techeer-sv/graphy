import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { ReactQueryDevtools } from '@tanstack/react-query-devtools';
import { Suspense, lazy } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { RecoilRoot } from 'recoil';

import LoadingSpinner from './components/LoadingSpinner';

// lazy 동적으로 필요할 때 import를 하여 실제로 로드되는 것
const MainPage = lazy(() => import('./pages/MainPage'));
const WritingPage = lazy(() => import('./pages/WritingPage'));
const ReadingPage = lazy(() => import('./pages/ReadingPage'));
const ModifyingPage = lazy(() => import('./pages/ModifyingPage'));
const ErrorPage = lazy(() => import('./pages/ErrorPage'));

const queryClient = new QueryClient();

function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <RecoilRoot>
        <Router>
          <Suspense fallback={<LoadingSpinner />}>
            <Routes>
              <Route path="/" element={<MainPage />} />
              <Route path="/write" element={<WritingPage />} />
              <Route path="/read/:id" element={<ReadingPage />} />
              <Route path="/modify" element={<ModifyingPage />} />
              <Route path="/*" element={<ErrorPage />} />
            </Routes>
          </Suspense>
        </Router>
      </RecoilRoot>
      <ReactQueryDevtools initialIsOpen={false} />
    </QueryClientProvider>
  );
}
export default App;
