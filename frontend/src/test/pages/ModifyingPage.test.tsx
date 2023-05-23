import '@testing-library/jest-dom';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import { RecoilRoot } from 'recoil';
import ModifyingPage from '../../pages/ModifyingPage';
import { BrowserRouter } from 'react-router-dom';
import { onChange, RecoilObserver } from '../jest/RecoilObserver';
import { titleState, tldrState } from '../../Recoil';
import { rest } from 'msw';
import { setupServer } from 'msw/node';

jest.mock('../../components/NavBar', () => () => <div data-testid="NavBar" />);
jest.mock('../../components/TechStackSelection', () => () => (
  <div data-testid="TechStackSelection" />
));
jest.mock('../../components/ImageUploader', () => () => (
  <div data-testid="ImageUploader" />
));
jest.mock('../../components/QuillEditor', () => () => (
  <div data-testid="QuillEditor" />
));

const server = setupServer(
  rest.put('http://localhost:8080/api/v1/projects/0', (req, res, ctx) => {
    return res(
      ctx.json({
        code: 'P004',
        message: '프로젝트 수정 성공',
        data: {
          content: '<p>0</p>',
          description: '0',
          projectId: 0,
          projectName: '0',
          techTags: null,
          thumbNail: null,
        },
      }),
    );
  }),
);

describe('ModifyingPage', () => {
  const onChange1 = jest.fn();
  beforeEach(() => {
    window.alert = jest.fn();
  });
  beforeAll(() => server.listen());
  afterEach(() => server.resetHandlers());
  afterAll(() => server.close());
  test('ModifyingPage 테스트', async () => {
    render(
      <RecoilRoot>
        <BrowserRouter>
          <RecoilObserver node={titleState} onChange={onChange} />
          <RecoilObserver node={tldrState} onChange={onChange1} />
          <ModifyingPage />
        </BrowserRouter>
      </RecoilRoot>,
    );
    const navBar = screen.getByTestId('NavBar');
    expect(navBar).toBeInTheDocument();

    const titleInput = screen.getByPlaceholderText('제목을 입력해주세요.');
    expect(titleInput).toBeInTheDocument();
    fireEvent.change(titleInput, { target: { value: 'testTitle' } });
    expect(onChange).toHaveBeenCalledWith('');
    expect(onChange).toHaveBeenCalledWith('testTitle');
    expect(onChange).toHaveBeenCalledTimes(2);

    const tldrInput = screen.getByPlaceholderText('한 줄 소개를 입력해주세요.');
    expect(tldrInput).toBeInTheDocument();
    fireEvent.change(tldrInput, { target: { value: 'testTldr' } });
    expect(onChange1).toHaveBeenCalledWith('');
    expect(onChange1).toHaveBeenCalledWith('testTldr');
    expect(onChange1).toHaveBeenCalledTimes(2);

    const techStackSelection = screen.getByTestId('TechStackSelection');
    expect(techStackSelection).toBeInTheDocument();

    const imageUploader = screen.getByTestId('ImageUploader');
    expect(imageUploader).toBeInTheDocument();

    const quillEditor = screen.getByTestId('QuillEditor');
    expect(quillEditor).toBeInTheDocument();

    const cancelButton = screen.getByText('취소');
    expect(cancelButton).toBeInTheDocument();
    fireEvent.click(cancelButton);
    expect(window.location.pathname).toBe('/read/0');

    const postButton = screen.getByText('수정');
    expect(postButton).toBeInTheDocument();

    fireEvent.click(postButton);

    await waitFor(() => {
      expect(window.location.pathname).toBe('/read/0');
    });
  });
});
