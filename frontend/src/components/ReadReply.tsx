import { act } from '@testing-library/react';
import axios from 'axios';
import { useState } from 'react';
import { useRecoilState, useRecoilValue } from 'recoil';

import PutReply from './PutReply';
import ReadReReply from './ReadReReply';
import WriteReReply from './WriteReReply';
import delete_reply from '../assets/image/delete.svg';
import nested_reply from '../assets/image/nested_reply.svg';
import pencil_square from '../assets/image/pencil-square.svg';
import { persistTokenState, refreshState } from '../Recoil';
import useDidMountEffect from '../useDidMountEffect';

interface ReadReReplyObject {
  commentId: number;
  content: string;
  createdAt: string;
}

interface PropsObject {
  contents: {
    commentId: number;
    childCount: number;
    content: string;
    createdAt: string;
  };
  setSelectedValue: React.Dispatch<React.SetStateAction<string>>;
}

function ReadReply(props: PropsObject) {
  const { contents, setSelectedValue } = props;

  const [writeVis, setWriteVis] = useState<boolean>(false);
  const [putVis, setPutVis] = useState<boolean>(false);
  const [commentVis, setCommentVis] = useState<boolean>(false);
  const [comment, setComment] = useState<ReadReReplyObject[]>([]);
  const [commentRef, setCommentRef] = useState<boolean>(false);
  const accessToken = sessionStorage.getItem('accessToken');
  const persistToken = useRecoilValue(persistTokenState);
  const [refresh, setRefresh] = useRecoilState(refreshState);

  const date = new Date(contents.createdAt);

  const formattedDate = `${date.getFullYear()}-${
    date.getMonth() + 1
  }-${date.getDate()} ${date.getHours().toString().padStart(2, '0')}:${date
    .getMinutes()
    .toString()
    .padStart(2, '0')}:${date.getSeconds().toString().padStart(2, '0')}`;

  const changeWriteVis = () => {
    setWriteVis(false);
  };

  const changePutVis = () => {
    setPutVis(false);
  };

  async function getComment() {
    const url = `http://localhost:8080/api/v1/comments/${contents.commentId}`;
    try {
      const res = await axios.get(url, {
        headers: {
          Authorization: `Bearer ${accessToken || persistToken}`,
        },
      });
      act(() => {
        setComment(res.data.data);
      });
    } catch (error) {
      console.error(error);
      alert('답글 조회 실패');
    }
  }

  async function deleteReply() {
    const url = `http://localhost:8080/api/v1/comments/${contents.commentId}`;
    try {
      await axios.delete(url, {
        headers: {
          Authorization: `Bearer ${accessToken || persistToken}`,
        },
      });
      setRefresh(!refresh);
    } catch (error) {
      if (!navigator.onLine) {
        alert('오프라인 상태입니다. 네트워크 연결을 확인해주세요.');
      } else {
        console.error(error);
        alert('댓글 삭제 실패');
      }
    }
  }

  function openComment() {
    act(() => {
      setCommentVis(true);
    });
    getComment();
  }

  const changeCommentRef = () => {
    setCommentRef(!commentRef);
  };

  useDidMountEffect(() => {
    getComment();
  }, [commentRef]);

  let buttonContent;
  if (contents.childCount === 0) {
    buttonContent = null;
  } else if (commentVis) {
    buttonContent = (
      <button
        className="mr-2 font-ng"
        onClick={() => setCommentVis(false)}
        data-testid="closeReReply"
        type="button"
      >
        ▲ 답글 {contents.childCount}개
      </button>
    );
  } else {
    buttonContent = (
      <button
        className="mr-2 font-ng"
        onClick={() => openComment()}
        data-testid="openReReply"
        type="button"
      >
        ▼ 답글 {contents.childCount}개
      </button>
    );
  }

  return (
    <>
      <div className="mt-3 h-auto rounded-lg border-2 border-gray-400">
        <div className="flex flex-row whitespace-nowrap border-b border-dashed border-gray-400 py-1 pl-2 font-ng text-xs sm:text-sm">
          {contents.content !== '삭제된 댓글입니다.' ? (
            <>
              <p className="ml-1 mr-3 font-ng">{`ID ${contents.commentId}`}</p>
              <p className="mr-3 hidden border-l border-dashed border-gray-400 pl-3 pr-3 font-ng-b sm:block">
                {formattedDate}
              </p>
            </>
          ) : null}

          <div className="mx-auto mr-2 flex flex-row">
            {buttonContent}

            {contents.content !== '삭제된 댓글입니다.' ? (
              <>
                <button
                  className="flex items-center border-l border-dashed border-gray-400 pr-3 pl-3"
                  onClick={() => deleteReply()}
                  type="button"
                >
                  <img
                    src={delete_reply}
                    className="mr-1 h-4 font-ng text-sm"
                    alt="delete icon"
                  />
                  삭제
                </button>
                <button
                  className="flex items-center border-l border-dashed border-gray-400 pr-3 pl-3"
                  onClick={() => setPutVis(!putVis)}
                  type="button"
                >
                  <img
                    src={pencil_square}
                    className="mr-1 h-3 font-ng text-sm"
                    alt="pencil icon"
                  />
                  수정
                </button>
              </>
            ) : null}

            <button
              className="mx-auto mr-0 flex items-center border-l border-dashed border-gray-400 pr-2 pl-3"
              onClick={() => setWriteVis(!writeVis)}
              type="button"
            >
              <img
                src={nested_reply}
                className="mr-1 h-3 font-ng text-sm"
                alt="reply icon"
              />
              답글
            </button>
          </div>
        </div>
        {contents.content !== '삭제된 댓글입니다.' ? (
          <p
            className="my-1 ml-2 break-words font-ng"
            placeholder="댓글 로딩중"
          >
            {contents.content}
          </p>
        ) : (
          <p
            className="my-1 ml-2 break-words font-ng text-gray-400"
            placeholder="댓글 로딩중"
          >
            {contents.content}
          </p>
        )}
      </div>
      {/* 댓글 수정창 */}
      {putVis ? (
        <PutReply
          contents={contents}
          changePutVis={changePutVis}
          setSelectedValue={setSelectedValue}
          changeCommentRef={changeCommentRef}
        />
      ) : null}
      {/* 대댓글 표시 */}
      {commentVis
        ? comment.map((x: ReadReReplyObject, y: number) => (
            <ReadReReply
              contents={x}
              key={comment[y].commentId}
              setSelectedValue={setSelectedValue}
              changeCommentRef={changeCommentRef}
            />
          ))
        : null}
      {/* 대댓글 입력창 */}
      {writeVis ? (
        <WriteReReply
          contents={contents}
          changeWriteVis={changeWriteVis}
          setSelectedValue={setSelectedValue}
        />
      ) : null}
    </>
  );
}

export default ReadReply;
