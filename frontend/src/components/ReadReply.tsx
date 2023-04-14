import { useState } from 'react';

import nested_reply from '../assets/image/nested_reply.svg';
import ReadReReply from './ReadReReply';
import WriteReReply from './WriteReReply';

function ReadReply(props: any) {
  const [visible, setVisible] = useState<boolean>(false);

  return (
    <>
      <div className="mt-3 h-auto rounded-lg border-2 border-gray-400">
        <div className=" flex flex-row border-b border-dashed border-gray-400 py-1 pl-2 font-ng text-sm">
          {`댓글 Id = ${props.contents.commentId}`}
          <button
            className="mx-auto mr-0 flex border-l border-dashed border-gray-400 pr-2 pl-2"
            onClick={() => setVisible(!visible)}
          >
            <img src={nested_reply} className="mt-1 mr-1 h-3 font-ng text-sm" />
            답글
          </button>
        </div>
        <p className="my-1 ml-2 font-ng" placeholder="댓글 로딩중">
          {props.contents.content}
        </p>
      </div>
      {/*대댓글 표시*/}
      {props.contents.childComments.map((x: object, y: number) => (
        <ReadReReply contents={x} key={y} />
      ))}
      {/*대댓글 입력창*/}
      {visible ? <WriteReReply /> : null}
    </>
  );
}

export default ReadReply;
