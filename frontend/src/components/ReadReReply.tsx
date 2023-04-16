import reply_icon from '../assets/image/reply_icon.svg';

function ReadReReply(props: any) {
  return (
    <div className="relative">
      <img src={reply_icon} className="absolute ml-2 mt-1 h-5" />
      <div className="mt-3 ml-8 h-auto rounded-lg border-2 border-gray-400">
        <div className=" flex flex-row border-b border-dashed border-gray-400 py-1 pl-2 font-ng text-sm ">
          {`댓글 Id = ${props.contents.commentId}, 부모 Id = ${props.contents.parentId}`}
        </div>
        <p className="my-1 ml-2 font-ng" placeholder="댓글 로딩중">
          {props.contents.content}
        </p>
      </div>
    </div>
  );
}

export default ReadReReply;
