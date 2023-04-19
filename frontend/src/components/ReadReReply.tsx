import reply_icon from '../assets/image/reply_icon.svg';
import delete_reply from '../assets/image/delete.svg';
import axios from 'axios';
import { useRecoilState } from 'recoil';
import { refreshState } from '../Recoil';

function ReadReReply(props: any) {
  const [refresh, setRefresh] = useRecoilState(refreshState);

  async function deleteReReply() {
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
    <div className="relative">
      <img src={reply_icon} className="absolute ml-2 mt-1 h-5" />
      <div className="mt-3 ml-8 h-auto rounded-lg border-2 border-gray-400">
        <div className=" flex flex-row border-b border-dashed border-gray-400 py-1 pl-2 font-ng text-sm ">
          {`댓글 Id = ${props.contents.commentId}, 부모 Id = ${props.contents.parentId}`}
          {props.contents.content !== '삭제된 댓글입니다.' ? (
            <button
              className="mx-auto mr-0 flex items-center border-l border-dashed border-gray-400 pr-3 pl-2"
              onClick={() => deleteReReply()}
            >
              <img src={delete_reply} className="mr-1 h-4 font-ng text-sm" />
              삭제
            </button>
          ) : null}
        </div>
        <p className="my-1 ml-2 break-words font-ng" placeholder="댓글 로딩중">
          {props.contents.content}
        </p>
      </div>
    </div>
  );
}

export default ReadReReply;
