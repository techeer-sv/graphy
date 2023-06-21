import axios from 'axios';
import { useState } from 'react';
import { useRecoilState, useRecoilValue } from 'recoil';

import PutReply from './PutReply';
import delete_reply from '../assets/image/delete.svg';
import pencil_square from '../assets/image/pencil-square.svg';
import reply_icon from '../assets/image/reply_icon.svg';
import { persistTokenState, refreshState } from '../Recoil';

interface PropsObject {
  contents: {
    commentId: number;
    childCount?: number;
    content: string;
    createdAt: string;
  };
  setSelectedValue: React.Dispatch<React.SetStateAction<string>>;
  changeCommentRef: () => void;
}

function ReadReReply(props: PropsObject) {
  const { contents, setSelectedValue, changeCommentRef } = props;

  const [putVis, setPutVis] = useState<boolean>(false);
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

  const changePutVis = () => {
    setPutVis(false);
  };

  async function deleteReReply() {
    const url = `http://localhost:8080/api/v1/comments/${contents.commentId}`;
    try {
      const res = await axios.delete(url, {
        headers: {
          Authorization: `Bearer ${accessToken || persistToken}`,
        },
      });
      console.log(res.data);
      setRefresh(!refresh);
      changeCommentRef();
    } catch (error) {
      if (!navigator.onLine) {
        alert('오프라인 상태입니다. 네트워크 연결을 확인해주세요.');
      } else {
        console.error(error);
        alert('답글 삭제 실패');
      }
    }
  }

  return (
    <>
      <div className="relative">
        <img
          src={reply_icon}
          className="absolute ml-2 mt-1 h-5"
          alt="reply icon"
        />
        <div className="mt-3 ml-8 h-auto rounded-lg border-2 border-gray-400">
          {contents.content !== '삭제된 댓글입니다.' ? (
            <div className=" flex flex-row border-b border-dashed border-gray-400 py-1 pl-2 font-ng text-sm ">
              <p className="ml-1 mr-3 font-ng">{`ID ${contents.commentId}`}</p>
              <p className="mr-3 hidden border-l border-dashed border-gray-400 pl-3 pr-3 font-ng-b sm:block">
                {formattedDate}
              </p>
              <div className="mx-auto mr-2 flex flex-row">
                <button
                  className="flex items-center border-l border-dashed border-gray-400 pr-3 pl-2"
                  onClick={() => deleteReReply()}
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
                  className="flex items-center border-l border-dashed border-gray-400 pr-2 pl-3"
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
              </div>
            </div>
          ) : null}
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
      </div>
      {/* 댓글 수정창 */}
      {putVis ? (
        <div className="ml-8">
          <PutReply
            contents={contents}
            changePutVis={changePutVis}
            setSelectedValue={setSelectedValue}
            changeCommentRef={changeCommentRef}
          />
        </div>
      ) : null}
    </>
  );
}

export default ReadReReply;
