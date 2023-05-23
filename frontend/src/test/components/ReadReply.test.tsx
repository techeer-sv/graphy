import '@testing-library/jest-dom';
import { fireEvent, render, screen, waitFor } from '@testing-library/react';
import ReadReply from '../../components/ReadReply';
import { RecoilRoot } from 'recoil';
import { onChange, RecoilObserver } from '../jest/RecoilObserver';
import { refreshState } from '../../Recoil';
import { rest } from 'msw';
import { setupServer } from 'msw/node';

jest.mock('../../components/PutReply', () => () => (
  <div data-testid="PutReply" />
));
jest.mock('../../components/ReadReReply', () => () => (
  <div data-testid="ReadReReply" />
));
jest.mock('../../components/WriteReReply', () => () => (
  <div data-testid="WriteReReply" />
));

const contents = {
  childCount: 2,
  commentId: 1,
  content: '0',
  createdAt: '2000-01-01T00:00:00.000000',
};

const setSelectedValue = jest.fn();

const server = setupServer(
  rest.delete(
    `http://localhost:8080/api/v1/comments/${contents.commentId}`,
    (req, res, ctx) => {
      return res(
        ctx.json({ code: 'C003', message: '댓글 삭제 성공', data: '' }),
      );
    },
  ),
  rest.get(
    `http://localhost:8080/api/v1/comments/${contents.commentId}`,
    (req, res, ctx) => {
      return res(
        ctx.json({
          code: 'C001',
          message: '답글 조회 성공',
          data: [
            {
              commentId: 2,
              content: '2',
              createdAt: '2000-01-01T00:00:00.000000',
            },
            {
              commentId: 3,
              content: '3',
              createdAt: '2000-01-01T00:00:00.000000',
            },
          ],
        }),
      );
    },
  ),
);

describe('ReadReply', () => {
  beforeAll(() => {
    server.listen();
  });
  afterEach(() => {
    server.resetHandlers();
  });
  afterAll(() => {
    server.close();
  });
  test('ReadReply 테스트', async () => {
    render(
      <RecoilRoot>
        <RecoilObserver node={refreshState} onChange={onChange} />
        <ReadReply contents={contents} setSelectedValue={setSelectedValue} />
      </RecoilRoot>,
    );

    const replyIcon = screen.getByAltText('reply icon');
    expect(replyIcon).toBeInTheDocument();

    const id = screen.getByText('ID 1');
    expect(id).toBeInTheDocument();

    const seeTime = screen.getByText('2000-1-1 00:00:00');
    expect(seeTime).toBeInTheDocument();

    const deleteButton = screen.getByText('삭제');
    expect(deleteButton).toBeInTheDocument();
    fireEvent.click(deleteButton);

    await waitFor(() => expect(onChange).toBeCalledTimes(2));

    const putButton = screen.getByText('수정');
    expect(putButton).toBeInTheDocument();
    fireEvent.click(putButton);

    const putReply = screen.queryByTestId('PutReply');
    expect(putReply).toBeInTheDocument();
    fireEvent.click(putButton);
    expect(screen.queryByText('PutReply')).not.toBeInTheDocument();

    const writeButton = screen.getByText('답글');
    expect(writeButton).toBeInTheDocument();
    fireEvent.click(writeButton);

    const writeReReply = screen.queryByTestId('WriteReReply');
    expect(writeReReply).toBeInTheDocument();
    fireEvent.click(writeButton);
    expect(screen.queryByText('WriteReReply')).not.toBeInTheDocument();

    const contentIn = screen.getByText('0');
    expect(contentIn).toBeInTheDocument();

    const openReReply = screen.getByTestId('openReReply');
    expect(openReReply).toBeInTheDocument();
    fireEvent.click(openReReply);

    const closeReReply = screen.getByTestId('closeReReply');
    await waitFor(() => {
      expect(closeReReply).toBeInTheDocument();
    });

    const readReReply = screen.getAllByTestId('ReadReReply');
    expect(readReReply).toHaveLength(2);
    fireEvent.click(closeReReply);
    expect(openReReply).toBeInTheDocument();
    expect(screen.queryAllByTestId('ReadReReply')).toHaveLength(0);
  });
});
