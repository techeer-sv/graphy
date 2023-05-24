import '@testing-library/jest-dom';
import { fireEvent, render, screen, waitFor } from '@testing-library/react';
import { rest } from 'msw';
import { setupServer } from 'msw/node';
import { RecoilRoot } from 'recoil';

import PutReply from '../../components/PutReply';
import { refreshState } from '../../Recoil';
import { onChange, RecoilObserver } from '../jest/RecoilObserver';

const contents = {
  commentId: 0,
  content: '0',
  createdAt: '2000-00-00T00:00:00.000000',
};
const setSelectedValue = jest.fn();
const changeCommentRef = jest.fn();
const changePutVis = jest.fn();

const scrollIntoViewMock = jest.fn();
Object.defineProperty(window.Element.prototype, 'scrollIntoView', {
  value: scrollIntoViewMock,
  configurable: true,
});

const server = setupServer(
  rest.put(
    `http://localhost:8080/api/v1/comments/${contents.commentId}`,
    (req, res, ctx) => {
      return res(
        ctx.json({
          code: 'C004',
          message: '댓글 수정 성공',
          data: '',
        }),
      );
    },
  ),
);
describe('PutReply', () => {
  beforeAll(() => server.listen());
  afterEach(() => server.resetHandlers());
  afterAll(() => server.close());
  test('PutReply 테스트', async () => {
    render(
      <RecoilRoot>
        <RecoilObserver node={refreshState} onChange={onChange} />
        <PutReply
          contents={contents}
          changePutVis={changePutVis}
          setSelectedValue={setSelectedValue}
          changeCommentRef={changeCommentRef}
        />
      </RecoilRoot>,
    );
    const textArea = screen.getByPlaceholderText('수정할 댓글을 입력하세요.');
    expect(textArea).toBeInTheDocument();

    const putButton = screen.getByText('수정');
    expect(putButton).toBeInTheDocument();

    fireEvent.change(textArea, { value: '2' });
    fireEvent.click(putButton);

    await waitFor(() => expect(onChange).toBeCalledTimes(2));
    expect(changePutVis).toHaveBeenCalled();
    expect(setSelectedValue).toHaveBeenCalled();
  });
});
