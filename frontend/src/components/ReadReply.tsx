import nested_reply from '../assets/image/nested_reply.svg';

function ReadReply() {
  return (
    <div className="mt-3 h-auto rounded-lg border-2 border-gray-400">
      <div className=" flex flex-row border-b border-dashed border-gray-400 py-1 pl-2 font-ng text-sm">
        닉네임
        <button className="mx-auto mr-0 flex border-l border-dashed border-gray-400 pr-2 pl-2">
          <img src={nested_reply} className="mt-1 mr-1 h-3 font-ng text-sm" />
          답글
        </button>
      </div>
      <p className="my-1 ml-2 font-ng" placeholder="댓글 로딩중">
        댓글 내용
      </p>
    </div>
  );
}

export default ReadReply;
