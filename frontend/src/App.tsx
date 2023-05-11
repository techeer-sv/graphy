import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { RecoilRoot } from 'recoil';
import MainPage from './pages/MainPage';
import WritingPage from './pages/WritingPage';
import ReadingPage from './pages/ReadingPage';
import ModifyingPage from './pages/ModifyingPage';
import Modal from './components/Modal';

function App() {
  return (
    <RecoilRoot>
      <Router>
        <Routes>
          <Route path="/" element={<MainPage />} />
          <Route path="/write" element={<WritingPage />} />
          <Route path="/read" element={<ReadingPage />} />
          <Route path="/modify" element={<ModifyingPage />} />
          {/* <Route path="/modal" element={<Modal />} /> */}
        </Routes>
      </Router>
    </RecoilRoot>
  );
}
export default App;
