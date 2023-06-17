import '@testing-library/jest-dom';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import { rest } from 'msw';
import { setupServer } from 'msw/node';
import { useEffect } from 'react';
import { BrowserRouter } from 'react-router-dom';
import { RecoilRoot, RecoilState, useRecoilValue } from 'recoil';

import WritingPage from '../../pages/WritingPage';
import { titleState, tldrState } from '../../Recoil';
import { onChange, RecoilObserver } from '../jest/RecoilObserver';

jest.mock(
  '../../components/NavBar',
  () =>
    function () {
      return <div data-testid="NavBar" />;
    },
);
jest.mock(
  '../../components/TechStackSelection',
  () =>
    function () {
      return <div data-testid="TechStackSelection" />;
    },
);
jest.mock(
  '../../components/ImageUploader',
  () =>
    function () {
      return <div data-testid="ImageUploader" />;
    },
);
jest.mock(
  '../../components/QuillEditor',
  () =>
    function () {
      return <div data-testid="QuillEditor" />;
    },
);

const server = setupServer(
  rest.post('http://localhost:8080/api/v1/projects', (req, res, ctx) => {
    return res(
      ctx.json({
        code: 'P001',
        message: '프로젝트 생성 성공',
        data: {
          projectId: 1,
        },
      }),
    );
  }),
);

describe('WritingPage', () => {
  interface RecoilObserverProps<T> {
    node: RecoilState<T>;
    onchange: (newValue: T) => void;
  }

  const onChange2 = jest.fn();

  const RecoilObserver2 = <T,>({ node, onchange }: RecoilObserverProps<T>) => {
    const value = useRecoilValue(node);
    useEffect(() => onChange2(value), [onchange, value]);
    return null;
  };

  beforeEach(() => {
    window.alert = jest.fn();
  });
  beforeAll(() => server.listen());
  afterEach(() => server.resetHandlers());
  afterAll(() => server.close());
  test('WritingPage 테스트', async () => {
    render(
      <RecoilRoot>
        <BrowserRouter>
          <RecoilObserver<string> node={titleState} onchange={onChange} />
          <RecoilObserver2<string> node={tldrState} onchange={onChange2} />
          <WritingPage />
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
    expect(onChange2).toHaveBeenCalledWith('');
    expect(onChange2).toHaveBeenCalledWith('testTldr');
    expect(onChange2).toHaveBeenCalledTimes(2);

    const techStackSelection = screen.getByTestId('TechStackSelection');
    expect(techStackSelection).toBeInTheDocument();

    const imageUploader = screen.getByTestId('ImageUploader');
    expect(imageUploader).toBeInTheDocument();

    const quillEditor = screen.getByTestId('QuillEditor');
    expect(quillEditor).toBeInTheDocument();

    const cancelButton = screen.getByText('취소');
    expect(cancelButton).toBeInTheDocument();
    fireEvent.click(cancelButton);
    expect(window.location.pathname).toBe('/');

    const postButton = screen.getByText('저장');
    expect(postButton).toBeInTheDocument();

    fireEvent.click(postButton);

    await waitFor(() => {
      expect(window.location.pathname).toBe('/read/1');
    });
  });
});
