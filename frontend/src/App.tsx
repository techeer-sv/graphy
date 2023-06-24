import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { ReactQueryDevtools } from '@tanstack/react-query-devtools';
import { Suspense, lazy } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { RecoilRoot } from 'recoil';

import LoadingSpinner from './components/LoadingSpinner';
import ResultModal from './components/ResultModal';

// lazy 동적으로 필요할 때 import를 하여 실제로 로드되는 것
const MainPage = lazy(() => import('./pages/MainPage'));
const MyPage = lazy(() => import('./pages/MyPage'));
const WritingPage = lazy(() => import('./pages/WritingPage'));
const ReadingPage = lazy(() => import('./pages/ReadingPage'));
const ModifyingPage = lazy(() => import('./pages/ModifyingPage'));
const ErrorPage = lazy(() => import('./pages/ErrorPage'));
const SigninPage = lazy(() => import('./pages/SigninPage'));
const SignupPage = lazy(() => import('./pages/SignupPage'));

const queryClient = new QueryClient();

function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <RecoilRoot>
        <Router>
          <Suspense fallback={<LoadingSpinner />}>
            <Routes>
              <Route path="/" element={<MainPage />} />
              <Route path="/my" element={<MyPage />} />
              <Route path="/write" element={<WritingPage />} />
              <Route path="/read/:id" element={<ReadingPage />} />
              <Route path="/modify" element={<ModifyingPage />} />
              <Route path="/signin" element={<SigninPage />} />
              <Route path="/signup" element={<SignupPage />} />
              <Route path="/*" element={<ErrorPage />} />
            </Routes>
          </Suspense>
        </Router>
        <ResultModal />
      </RecoilRoot>
      <ReactQueryDevtools initialIsOpen={false} />
    </QueryClientProvider>
  );
}
export default App;
