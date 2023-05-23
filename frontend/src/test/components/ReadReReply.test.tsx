import '@testing-library/jest-dom';
import { fireEvent, render, screen, waitFor } from '@testing-library/react';
import ReadReReply from '../../components/ReadReReply';
import { RecoilRoot } from 'recoil';
import { rest } from 'msw';
import { setupServer } from 'msw/node';
import { onChange, RecoilObserver } from '../jest/RecoilObserver';
import { refreshState } from '../../Recoil';

jest.mock('../../components/PutReply', () => () => (
  <div data-testid="PutReply" />
));

const contents = {
  commentId: 0,
  content: '0',
  createdAt: '2000-01-01T00:00:00.000000',
};
const setSelectedValue = jest.fn();
const changeCommentRef = jest.fn();

const server = setupServer(
  rest.delete(
    `http://localhost:8080/api/v1/comments/${contents.commentId}`,
    (req, res, ctx) => {
      return res(
        ctx.json({ code: 'C003', message: '댓글 삭제 성공', data: '' }),
      );
    },
  ),
);

describe('ReadReReply', () => {
  beforeAll(() => server.listen());
  afterEach(() => server.resetHandlers());
  afterAll(() => server.close());
  test('ReadReReply 테스트', async () => {
    render(
      <RecoilRoot>
        <RecoilObserver node={refreshState} onChange={onChange} />
        <ReadReReply
          contents={contents}
          setSelectedValue={setSelectedValue}
          changeCommentRef={changeCommentRef}
        />
      </RecoilRoot>,
    );
    const replyIcon = screen.getByAltText('reply icon');
    expect(replyIcon).toBeInTheDocument();

    const id = screen.getByText('ID 0');
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

    const putReply = screen.getByTestId('PutReply');
    expect(putReply).toBeInTheDocument();

    const contentIn = screen.getByText('0');
    expect(contentIn).toBeInTheDocument();
  });
});
