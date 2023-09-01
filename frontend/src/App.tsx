import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { ReactQueryDevtools } from '@tanstack/react-query-devtools';
import { Suspense, lazy } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { RecoilRoot } from 'recoil';

import GptResultModal from './components/general/GptResultModal';
import LoadingSpinner from './components/general/LoadingSpinner';

// lazy 동적으로 필요할 때 import를 하여 실제로 로드되는 것
const MainPage = lazy(() => import('./pages/main/MainPage'));
const MyPage = lazy(() => import('./pages/user/MyPage'));
const WritingPage = lazy(() => import('./pages/submit/WritingPage'));
const ReadingPage = lazy(() => import('./pages/read/ReadingPage'));
const ModifyingPage = lazy(() => import('./pages/submit/ModifyingPage'));
const ErrorPage = lazy(() => import('./pages/error/ErrorPage'));
const SigninPage = lazy(() => import('./pages/user/SigninPage'));
const SignupPage = lazy(() => import('./pages/user/SignupPage'));
const SearchProjectPage = lazy(() => import('./pages/main/SearchProjectPage'));
const SearchUserPage = lazy(() => import('./pages/main/SearchUserPage'));

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
              <Route
                path="/searchProject/:projectName"
                element={<SearchProjectPage />}
              />
              <Route
                path="/searchUser/:userName"
                element={<SearchUserPage />}
              />
              <Route path="/*" element={<ErrorPage />} />
            </Routes>
          </Suspense>
        </Router>
        <GptResultModal />
      </RecoilRoot>
      <ReactQueryDevtools initialIsOpen={false} />
    </QueryClientProvider>
  );
}
export default App;
