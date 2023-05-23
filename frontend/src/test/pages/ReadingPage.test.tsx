import '@testing-library/jest-dom';
import { render, screen, fireEvent } from '@testing-library/react';
import { RecoilRoot } from 'recoil';
import ReadingPage from '../../pages/ReadingPage';
import { MemoryRouter, Route, Routes } from 'react-router-dom';
import { rest } from 'msw';
import { setupServer } from 'msw/node';

jest.mock('../../components/NavBar', () => () => <div data-testid="NavBar" />);
jest.mock('../../components/QuillWritten', () => () => (
  <div data-testid="QuillWritten" />
));
jest.mock('../../components/Reply', () => () => <div data-testid="Reply" />);

const server = setupServer(
  rest.get('http://localhost:8080/api/v1/projects/0', (req, res, ctx) => {
    return res(
      ctx.json({
        code: 'P002',
        message: '프로젝트 조회 성공',
        data: {
          commentsList: [
            {
              childCount: 2,
              commentId: 1,
              content: '',
              createdAt: '2000-01-01T00:00:00.000000',
            },
            {
              childCount: 1,
              commentId: 4,
              content: '',
              createdAt: '2000-01-01T00:00:00.000000',
            },
          ],
          content: '<p>test</p>',
          createdAt: '2000-01-01T00:00:00.000000',
          description: 'testTldr',
          id: 0,
          projectName: 'testTitle',
          techTags: ['React', 'ReactNative'],
          thumbNail: 'test-file-stub',
        },
      }),
    );
  }),
  rest.delete('http://localhost:8080/api/v1/projects/0', (req, res, ctx) => {
    return res(
      ctx.json({
        code: 'P003',
        message: '프로젝트 삭제 성공',
        data: '',
      }),
    );
  }),
);

describe('ReadingPage', () => {
  beforeAll(() => server.listen());
  afterEach(() => server.resetHandlers());
  afterAll(() => server.close());

  test('ReadingPage 테스트', async () => {
    render(
      <RecoilRoot>
        <MemoryRouter initialEntries={['/read/0']}>
          <Routes>
            <Route path="/read/:id" element={<ReadingPage />} />
          </Routes>
        </MemoryRouter>
      </RecoilRoot>,
    );
    const navBar = screen.getByTestId('NavBar');
    expect(navBar).toBeInTheDocument();

    const testTitle = await screen.findByText('testTitle');
    expect(testTitle).toBeInTheDocument();

    const tldr = screen.getByText('한줄 소개');
    expect(tldr).toBeInTheDocument();

    const testTldr = screen.getByText('testTldr');
    expect(testTldr).toBeInTheDocument();

    const techStack = screen.getByText('기술 스택');
    expect(techStack).toBeInTheDocument();

    const techStacks = screen.getAllByAltText('Stack');
    expect(techStacks).toHaveLength(2);

    const quillWritten = screen.getByTestId('QuillWritten');
    expect(quillWritten).toBeInTheDocument();

    const reply = screen.getByTestId('Reply');
    expect(reply).toBeInTheDocument();
  });
  test('수정 버튼 테스트', async () => {
    render(
      <RecoilRoot>
        <MemoryRouter initialEntries={['/read/0']}>
          <Routes>
            <Route path="/read/:id" element={<ReadingPage />} />
            <Route path="/modify" element={<div>Mock Modify Page</div>} />
          </Routes>
        </MemoryRouter>
      </RecoilRoot>,
    );
    const putButton = screen.getByText('수정');
    expect(putButton).toBeInTheDocument();
    fireEvent.click(putButton);

    const mockMain = await screen.findByText('Mock Modify Page');
    expect(mockMain).toBeInTheDocument();
  });
  test('삭제 버튼 테스트', async () => {
    render(
      <RecoilRoot>
        <MemoryRouter initialEntries={['/read/0']}>
          <Routes>
            <Route path="/read/:id" element={<ReadingPage />} />
            <Route path="/" element={<div>Mock Main Page</div>} />
          </Routes>
        </MemoryRouter>
      </RecoilRoot>,
    );
    const deleteButton = screen.getByText('삭제');
    expect(deleteButton).toBeInTheDocument();
    fireEvent.click(deleteButton);
    expect(window.location.pathname).toBe('/');

    const mockMain = await screen.findByText('Mock Main Page');
    expect(mockMain).toBeInTheDocument();
  });
  test('글작성 버튼 테스트', async () => {
    render(
      <RecoilRoot>
        <MemoryRouter initialEntries={['/read/0']}>
          <Routes>
            <Route path="/read/:id" element={<ReadingPage />} />
            <Route path="/write" element={<div>Mock Write Page</div>} />
          </Routes>
        </MemoryRouter>
      </RecoilRoot>,
    );
    const writeButton = screen.getByText('글작성');
    expect(writeButton).toBeInTheDocument();
    fireEvent.click(writeButton);

    const mockMain = await screen.findByText('Mock Write Page');
    expect(mockMain).toBeInTheDocument();
  });
});
