import '@testing-library/jest-dom';
import { fireEvent, render, screen, waitFor } from '@testing-library/react';
import { rest } from 'msw';
import { setupServer } from 'msw/node';
import { RecoilRoot } from 'recoil';

import WriteReReply from '../../../../../../components/read/ReadingPage/Reply/ReadReply/WriteReReply';
import { refreshState } from '../../../../../../Recoil';
import { onChange, RecoilObserver } from '../../../../../jest/RecoilObserver';

const mockScrollIntoView = jest.fn();
// HTMLElement의 프로토타입에 scrollIntoView 메서드를 추가합니다.
HTMLElement.prototype.scrollIntoView = mockScrollIntoView;

const contents = {
  childCount: 0,
  commentId: 0,
  content: '',
  createdAt: '',
};
const changeWriteVis = jest.fn();
const setSelectedValue = jest.fn();

const server = setupServer(
  rest.post(`http://localhost:8080/api/v1/comments`, (req, res, ctx) => {
    return res(
      ctx.json({
        code: 'C002',
        message: '댓글 생성 성공',
        data: { commentId: 0 },
      }),
    );
  }),
);

describe('WriteReReply', () => {
  beforeAll(() => server.listen());
  afterEach(() => server.resetHandlers());
  afterAll(() => server.close());
  test('WriteReReply 테스트', async () => {
    render(
      <RecoilRoot>
        <RecoilObserver<boolean> node={refreshState} onchange={onChange} />
        <WriteReReply
          contents={contents}
          changeWriteVis={changeWriteVis}
          setSelectedValue={setSelectedValue}
        />
      </RecoilRoot>,
    );
    expect(mockScrollIntoView).toHaveBeenCalled();

    const replyIcon = screen.getByAltText('reply icon');
    expect(replyIcon).toBeInTheDocument();

    const textArea = screen.getByPlaceholderText('답글을 입력하세요.');
    expect(textArea).toBeInTheDocument();

    const postButton = screen.getByText('등록');
    expect(postButton).toBeInTheDocument();
    fireEvent.click(postButton);

    await waitFor(() => {
      expect(onChange).toBeCalledTimes(2);
    });
  });
});
