import reply_icon from '../assets/image/reply_icon.svg';
import delete_reply from '../assets/image/delete.svg';
import axios from 'axios';
import { useRecoilState } from 'recoil';
import { refreshState } from '../Recoil';
import { useState } from 'react';
import pencil_square from '../assets/image/pencil-square.svg';
import PutReply from './PutReply';

function ReadReReply(props: any) {
  const [putVis, setPutVis] = useState<boolean>(false);
  const [refresh, setRefresh] = useRecoilState(refreshState);

  const date = new Date(props.contents.createdAt);

  const formattedDate = `${date.getFullYear()}-${
    date.getMonth() + 1
  }-${date.getDate()} ${date.getHours().toString().padStart(2, '0')}:${date
    .getMinutes()
    .toString()
    .padStart(2, '0')}:${date.getSeconds().toString().padStart(2, '0')}`;

  function changePutVis() {
    setPutVis(false);
  }

  async function deleteReReply() {
    const url = `http://localhost:8080/api/v1/comments/${props.contents.commentId}`;
    try {
      const res = await axios.delete(url);
      console.log(res);
      setRefresh(!refresh);
    } catch (error) {
      console.error(error);
      alert('대댓글 삭제 실패');
    }
  }

  return (
    <>
      <div className="relative">
        <img src={reply_icon} className="absolute ml-2 mt-1 h-5" />
        <div className="mt-3 ml-8 h-auto rounded-lg border-2 border-gray-400">
          {props.contents.content !== '삭제된 댓글입니다.' ? (
            <div className=" flex flex-row border-b border-dashed border-gray-400 py-1 pl-2 font-ng text-sm ">
              <p className="font-ng">{`ID ${props.contents.commentId}`}</p>
              <p className="mx-auto mr-2 font-ng-b">{formattedDate}</p>
              <button
                className="flex items-center border-l border-dashed border-gray-400 pr-3 pl-2"
                onClick={() => deleteReReply()}
              >
                <img src={delete_reply} className="mr-1 h-4 font-ng text-sm" />
                삭제
              </button>
              <button
                className="flex items-center border-l border-dashed border-gray-400 pr-3 pl-3"
                onClick={() => setPutVis(!putVis)}
              >
                <img src={pencil_square} className="mr-1 h-3 font-ng text-sm" />
                수정
              </button>
            </div>
          ) : null}
          {props.contents.content !== '삭제된 댓글입니다.' ? (
            <p
              className="my-1 ml-2 break-words font-ng"
              placeholder="댓글 로딩중"
            >
              {props.contents.content}
            </p>
          ) : (
            <p
              className="my-1 ml-2 break-words font-ng text-gray-400"
              placeholder="댓글 로딩중"
            >
              {props.contents.content}
            </p>
          )}
        </div>
      </div>
      {/*댓글 수정창*/}
      {putVis ? (
        <div className="ml-8">
          <PutReply
            contents={props.contents}
            changePutVis={changePutVis}
            setSelectedValue={props.setSelectedValue}
          />
        </div>
      ) : null}
    </>
  );
}

export default ReadReReply;
