import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { RecoilRoot } from 'recoil';
import MainPage from './pages/MainPage';
import WritingPage from './pages/WritingPage';
import ReadingPage from './pages/ReadingPage';
import ModifyingPage from './pages/ModifyingPage';
import ErrorPage from './pages/ErrorPage';

function App() {
  return (
    <RecoilRoot>
      <Router>
        <Routes>
          <Route path="/" element={<MainPage />} />
          <Route path="/write" element={<WritingPage />} />
          <Route path="/read/:id" element={<ReadingPage />} />
          <Route path="/modify" element={<ModifyingPage />} />
          <Route path="/*" element={<ErrorPage />} />
        </Routes>
      </Router>
    </RecoilRoot>
  );
}
export default App;
