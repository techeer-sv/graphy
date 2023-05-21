import '@testing-library/jest-dom';
import { useEffect } from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import { RecoilRoot, useRecoilValue } from 'recoil';
import { rest } from 'msw';
import { setupServer } from 'msw/node';
import MainPage from '../../pages/MainPage';
import { BrowserRouter } from 'react-router-dom';
import { searchTextState } from '../../Recoil';

const onChange = jest.fn();

export const RecoilObserver = ({ node, onChange }: any) => {
  const value = useRecoilValue(node);
  useEffect(() => onChange(value), [onChange, value]);
  return null;
};

jest.mock('../../components/NavBar', () => () => <div data-testid="NavBar" />);
jest.mock('../../components/Banner', () => () => <div data-testid="Banner" />);
jest.mock('../../components/ProjectCard', () => () => (
  <div data-testid="ProjectCard" />
));

const server = setupServer(
  rest.get(
    'http://localhost:8080/api/v1/projects/search',
    (
      req: any,
      res: (arg0: any) => any,
      ctx: {
        json: (arg0: {
          code: string;
          message: string;
          data: {
            id: number;
            projectName: string;
            description: string;
            thumbNail: string;
            createdAt: string;
            techTags: [];
          }[];
        }) => any;
      },
    ) => {
      return res(
        ctx.json({
          code: 'P005',
          message: '프로젝트 페이징 조회 성공',
          data: [
            {
              id: 1,
              projectName: 'Test1',
              description: 'Test1des',
              thumbNail: '',
              createdAt: '2000-00-00T00:00:00.000000',
              techTags: [],
            },
            {
              id: 2,
              projectName: 'Test2',
              description: 'Test2des',
              thumbNail: '',
              createdAt: '2000-00-00T00:00:00.000000',
              techTags: [],
            },
          ],
        }),
      );
    },
  ),
);

describe('MainPage', () => {
  beforeEach(() => {
    render(
      <RecoilRoot>
        <BrowserRouter>
          <RecoilObserver node={searchTextState} onChange={onChange} />
          <MainPage />
        </BrowserRouter>
      </RecoilRoot>,
    );
  });

  beforeAll(() => server.listen());
  afterEach(() => server.resetHandlers());
  afterAll(() => server.close());

  test('쓰기 버튼 테스트', async () => {
    const toWriteButton = screen.getByRole('button', { name: 'toWritePage' });
    expect(toWriteButton).toBeInTheDocument();
    fireEvent.click(toWriteButton);
    expect(window.location.pathname).toBe('/write');
  });

  test('프로젝트 카드 생성 테스트', async () => {
    const findProjectCards = await screen.findAllByTestId('ProjectCard');
    expect(findProjectCards).toHaveLength(2);
  });
});
