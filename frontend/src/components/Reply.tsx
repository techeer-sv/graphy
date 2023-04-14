import { useRef, useState } from 'react';
import { useRecoilState } from 'recoil';
import { writeReplyState } from '../Recoil';
import ReadReply from './ReadReply';

function Reply(props: any) {
  const [count, SetCount] = useState(0);
  const textAreaRef = useRef<HTMLTextAreaElement>(null);
  const [value, setValue] = useRecoilState(writeReplyState);

  const handleChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
    const inputValue = event.target.value;
    if (textAreaRef.current) {
      textAreaRef.current.style.height = 'auto';
      textAreaRef.current.style.height = `${textAreaRef.current.scrollHeight}px`;
    }
    if (inputValue.length > 256) {
      setValue(inputValue.substring(0, 256));
      return;
    }
    setValue(event.target.value);
  };
  return (
    <div>
      {/*댓글 개수, 댓글 나열 카테고리*/}
      <div className="mb-2 flex flex-row border-b-2 border-graphyblue">
        <span className="mr-2 flex flex-row font-ng-b text-lg">
          전체 댓글 <p className="ml-1 text-graphyblue">{count}</p>개
        </span>
        <select className="mb-1 rounded border border-black bg-graphybg font-ng text-sm">
          <option value="regist_order">등록순</option>
          <option value="newest_order">최신순</option>
          <option value="reply_order">답글순</option>
        </select>
        <div className="mx-auto mr-0 mb-2">
          <button className="mr-2 border-r border-gray-500 pr-2 font-ng-b text-sm">
            본문 보기
          </button>
          <button className="mr-2 border-r border-gray-500 pr-2 font-ng-b text-sm">
            댓글 닫기
          </button>
          <button className="mr-1 font-ng-b text-sm">새로고침</button>
        </div>
      </div>
      {/*댓글 표시*/}
      <div className="my-2 border-graphyblue">
        {props.contents.map((x: object, y: number) => (
          <ReadReply contents={x} key={y} />
        ))}
        {/*댓글 입력창*/}
        <div className="mb-8 mt-3 border-t-2 border-graphyblue py-3">
          <div className="min-h-24 flex h-auto flex-col rounded-xl border-2 border-gray-400">
            <textarea
              className="h-auto w-full resize-none appearance-none rounded-xl bg-graphybg py-2 px-3 font-ng leading-tight text-gray-700 focus:outline-none"
              id="reply"
              placeholder="댓글을 입력하세요."
              ref={textAreaRef}
              value={value}
              onChange={handleChange}
            />
            <button
              className="focus:shadow-outline m-auto my-2 mr-2 h-8 w-16 appearance-none place-items-end rounded-lg border-2 border-gray-400 bg-graphybg font-ng hover:bg-gray-200"
              onClick={() => console.log('등록 버튼 클릭')}
            >
              등록
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Reply;
