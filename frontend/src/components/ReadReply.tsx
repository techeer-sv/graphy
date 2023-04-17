import { useState } from 'react';

import nested_reply from '../assets/image/nested_reply.svg';
import ReadReReply from './ReadReReply';
import WriteReReply from './WriteReReply';
import axios from 'axios';
import { useRecoilState } from 'recoil';
import { refreshState } from '../Recoil';

function ReadReply(props: any) {
  const [visible, setVisible] = useState<boolean>(false);
  const [refresh, setRefresh] = useRecoilState(refreshState);

  async function deleteReply() {
    const url = `http://localhost:8080/api/v1/comments/${props.contents.commentId}`;
    try {
      const res = await axios.delete(url);
      console.log(res);
      setRefresh(!refresh);
    } catch (error) {
      console.error(error);
    }
  }

  return (
    <>
      <div className="mt-3 h-auto rounded-lg border-2 border-gray-400">
        <div className=" flex flex-row border-b border-dashed border-gray-400 py-1 pl-2 font-ng text-sm">
          {`댓글 Id = ${props.contents.commentId}`}
          <div className="mx-auto mr-0 flex flex-row">
            {props.contents.content !== '삭제된 댓글입니다.' ? (
              <button
                className="flex border-l border-dashed border-gray-400 pr-2 pl-2"
                onClick={() => deleteReply()}
              >
                <img
                  src={nested_reply}
                  className="mt-1 mr-1 h-3 font-ng text-sm"
                />
                삭제
              </button>
            ) : null}
            <button
              className="mx-auto mr-0 flex border-l border-dashed border-gray-400 pr-2 pl-2"
              onClick={() => setVisible(!visible)}
            >
              <img
                src={nested_reply}
                className="mt-1 mr-1 h-3 font-ng text-sm"
              />
              답글
            </button>
          </div>
        </div>
        <p className="my-1 ml-2 font-ng" placeholder="댓글 로딩중">
          {props.contents.content}
        </p>
      </div>
      {/*대댓글 표시*/}
      {props.contents.childComments.map((x: object, y: number) => (
        <ReadReReply
          contents={x}
          key={props.contents.childComments[y].commentId}
        />
      ))}
      {/*대댓글 입력창*/}
      {visible ? <WriteReReply contents={props.contents} /> : null}
    </>
  );
}

export default ReadReply;
