import '@testing-library/jest-dom';
import { fireEvent, render, screen, waitFor } from '@testing-library/react';
import { rest } from 'msw';
import { setupServer } from 'msw/node';
import { RecoilRoot } from 'recoil';

import Reply from '../../components/Reply';
import { refreshState } from '../../Recoil';
import { onChange, RecoilObserver } from '../jest/RecoilObserver';

jest.mock(
  '../../components/ReadReply',
  () =>
    function () {
      return <div data-testid="ReadReply" />;
    },
);

const contents = [
  {
    childCount: 2,
    commentId: 1,
    content: '0',
    createdAt: '2000-01-01T00:00:00.000000',
  },
  {
    childCount: 1,
    commentId: 4,
    content: '0',
    createdAt: '2000-01-01T00:00:00.000000',
  },
];

const setReadReply = jest.fn();

const server = setupServer(
  rest.post(`http://localhost:8080/api/v1/comments`, (req, res, ctx) => {
    return res(
      ctx.json({
        code: 'C003',
        message: '댓글 생성 성공',
        data: { commentId: 0 },
      }),
    );
  }),
);

window.scrollTo = jest.fn();

describe('Reply', () => {
  beforeEach(() => {
    Object.defineProperty(window, 'sessionStorage', {
      value: {
        getItem: jest.fn(() => 'faketoken'),
        setItem: jest.fn(() => null),
        removeItem: jest.fn(() => null),
        clear: jest.fn(() => null),
      },
      writable: true,
    });
  });
  beforeAll(() => {
    server.listen();
  });
  afterEach(() => {
    server.resetHandlers();
  });
  afterAll(() => {
    server.close();
  });
  test('Reply 테스트', async () => {
    render(
      <RecoilRoot>
        <RecoilObserver<boolean> node={refreshState} onchange={onChange} />
        <Reply contents={contents} setReadReply={setReadReply} />
      </RecoilRoot>,
    );
    const readReply = screen.getAllByTestId('ReadReply');
    expect(readReply).toHaveLength(2);

    const replyCount = screen.getByTestId('replyCount');
    expect(replyCount).toBeInTheDocument();

    const orderSelect = screen.getByTestId('orderSelect') as HTMLSelectElement;
    expect(orderSelect).toBeInTheDocument();
    fireEvent.change(orderSelect, { target: { value: 'newest_order' } });
    expect(orderSelect.value).toBe('newest_order');
    fireEvent.change(orderSelect, { target: { value: 'reply_order' } });
    expect(orderSelect.value).toBe('reply_order');
    fireEvent.change(orderSelect, { target: { value: '' } });
    expect(orderSelect.value).toBe('regist_order');

    const moveToTop = screen.getByText('본문 보기');
    expect(moveToTop).toBeInTheDocument();
    fireEvent.click(moveToTop);

    const closeReply = screen.getByText('댓글 닫기');
    expect(closeReply).toBeInTheDocument();
    fireEvent.click(closeReply);
    expect(screen.queryAllByTestId('ReadReply')).toHaveLength(0);

    const openReply = screen.getByText('댓글 열기');
    expect(openReply).toBeInTheDocument();
    fireEvent.click(openReply);
    expect(closeReply).toBeInTheDocument();

    const refreshButton = screen.getByText('새로고침');
    expect(refreshButton).toBeInTheDocument();
    fireEvent.click(refreshButton);

    await waitFor(() => expect(onChange).toBeCalledTimes(2));

    const textArea = screen.getByPlaceholderText(
      '댓글을 입력하세요.',
    ) as HTMLTextAreaElement;
    expect(textArea).toBeInTheDocument();
    fireEvent.change(textArea, { target: { value: 'test' } });
    expect(textArea.value).toBe('test');

    const postButton = screen.getByText('등록');
    expect(postButton).toBeInTheDocument();
    fireEvent.click(postButton);

    await waitFor(() => expect(onChange).toBeCalledTimes(3));
  });
});
