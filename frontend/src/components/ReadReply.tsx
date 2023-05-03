import { useState } from 'react';

import nested_reply from '../assets/image/nested_reply.svg';
import ReadReReply from './ReadReReply';
import WriteReReply from './WriteReReply';
import delete_reply from '../assets/image/delete.svg';
import pencil_square from '../assets/image/pencil-square.svg';
import axios from 'axios';
import { useRecoilState } from 'recoil';
import { refreshState } from '../Recoil';
import PutReply from './PutReply';

function ReadReply(props: any) {
  const [writeVis, setWriteVis] = useState<boolean>(false);
  const [putVis, setPutVis] = useState<boolean>(false);
  const [refresh, setRefresh] = useRecoilState(refreshState);

  // const date = new Date(props.contents.createdAt);

  // const formattedDate = `${date.getFullYear()}-${
  //   date.getMonth() + 1
  // }-${date.getDate()} ${date.getHours().toString().padStart(2, '0')}:${date
  //   .getMinutes()
  //   .toString()
  //   .padStart(2, '0')}:${date.getSeconds().toString().padStart(2, '0')}`;

  function changeWriteVis() {
    setWriteVis(false);
  }

  function changePutVis() {
    setPutVis(false);
  }

  async function deleteReply() {
    const url = `http://localhost:8080/api/v1/comments/${props.contents.commentId}`;
    try {
      const res = await axios.delete(url);
      console.log(res);
      setRefresh(!refresh);
    } catch (error) {
      console.error(error);
      alert('댓글 삭제 실패');
    }
  }

  return (
    <>
      <div className="mt-3 h-auto rounded-lg border-2 border-gray-400">
        <div className="flex flex-row border-b border-dashed border-gray-400 py-1 pl-2 font-ng text-sm">
          <p className="font-ng">{`ID ${props.contents.commentId}`}</p>
          {/* <p className="mx-auto mr-2 font-ng-b">{formattedDate}</p> */}
          <div className="mx-auto mr-2 flex flex-row">
            {props.contents.content !== '삭제된 댓글입니다.' ? (
              <button
                className="flex items-center border-l border-dashed border-gray-400 pr-3 pl-3"
                onClick={() => deleteReply()}
              >
                <img src={delete_reply} className="mr-1 h-4 font-ng text-sm" />
                삭제
              </button>
            ) : null}
            <button
              className="flex items-center border-l border-dashed border-gray-400 pr-3 pl-3"
              onClick={() => setPutVis(!putVis)}
            >
              <img src={pencil_square} className="mr-1 h-3 font-ng text-sm" />
              수정
            </button>
            <button
              className="mx-auto mr-0 flex items-center border-l border-dashed border-gray-400 pr-2 pl-3"
              onClick={() => setWriteVis(!writeVis)}
            >
              <img src={nested_reply} className="mr-1 h-3 font-ng text-sm" />
              답글
            </button>
          </div>
        </div>
        <p className="my-1 ml-2 break-words font-ng" placeholder="댓글 로딩중">
          {props.contents.content}
        </p>
      </div>
      {/*댓글 수정창*/}
      {putVis ? (
        <PutReply
          contents={props.contents}
          changePutVis={changePutVis}
          setSelectedValue={props.setSelectedValue}
        />
      ) : null}
      {/*대댓글 표시*/}
      {/* {props.contents.childComments.map((x: object, y: number) => (
        <ReadReReply
          contents={x}
          key={props.contents.childComments[y].commentId}
        />
      ))} */}
      {/*대댓글 입력창*/}
      {/* {writeVis ? (
        <WriteReReply
          contents={props.contents}
          changeWriteVis={changeWriteVis}
          setSelectedValue={props.setSelectedValue}
        />
      ) : null} */}
    </>
  );
}

export default ReadReply;
