import { useState } from 'react';
import nested_reply from '../assets/nested_reply.svg';
import reply_icon from '../assets/reply_icon.svg';

function Reply() {
  const [count, SetCount] = useState(0);
  return (
    <div>
      {/*댓글 개수, 댓글 나열 카테고리*/}
      <div className="mb-3 flex flex-row border-b-2 border-graphyblue pb-2">
        <p className="mr-2 flex flex-row font-ng-b text-lg">
          전체 댓글 <p className="ml-1 text-graphyblue">{count}</p>개
        </p>
        <select className="rounded border border-black bg-graphybg font-ng text-sm">
          <option value="regist_order">등록순</option>
          <option value="newest_order">최신순</option>
          <option value="reply_order">답글순</option>
        </select>
      </div>
      {/*댓글 입력창*/}
      <div className="min-h-24 flex h-auto flex-col rounded-xl border-2 border-gray-400">
        <textarea
          className="h-auto w-full resize-none appearance-none rounded-xl bg-graphybg py-2 px-3 font-ng leading-tight text-gray-700 focus:outline-none"
          id="reply"
          placeholder="댓글을 입력하세요."
        />
        <button
          className="focus:shadow-outline m-auto my-2 mr-2 h-8 w-16 appearance-none place-items-end rounded-lg border-2 border-gray-400 bg-graphybg font-ng hover:bg-gray-200"
          onClick={() => console.log('등록 버튼 클릭')}
        >
          등록
        </button>
      </div>
      {/*댓글 표시*/}
      <div className="my-3 border-t-2 border-graphyblue">
        {/*답글 표시*/}
        <div className="mt-2 h-auto rounded-lg border-2 border-gray-400">
          <div className=" flex h-6 flex-row border-b border-dashed border-gray-400 pl-2 font-ng text-sm">
            닉네임
            <button className="mx-auto mr-2 flex border-l border-dashed border-gray-400 pl-2">
              <img
                src={nested_reply}
                className="mt-1 mr-1 h-3 font-ng text-sm"
              />
              답글
            </button>
          </div>
          <p className="my-1 ml-2 font-ng" placeholder="댓글 로딩중">
            댓글 내용
          </p>
        </div>
        {/*대댓글 표시*/}
        <div className="relative">
          <img src={reply_icon} className="absolute ml-2 mt-1 h-5" />
          <div className="mt-2 ml-8 h-auto rounded-lg border-2 border-gray-400">
            <div className=" flex h-6 flex-row border-b border-dashed border-gray-400 pl-2 font-ng text-sm">
              닉네임
              <button className="mx-auto mr-2 flex border-l border-dashed border-gray-400 pl-2">
                <img
                  src={nested_reply}
                  className="mt-1 mr-1 h-3 font-ng text-sm"
                />
                답글
              </button>
            </div>
            <p className="my-1 ml-2 font-ng" placeholder="댓글 로딩중">
              대댓글 내용
            </p>
          </div>
        </div>
        {/*대댓글 입력창*/}
        <div className="relative">
          <img src={reply_icon} className="absolute ml-2 mt-1 h-5" />
          <div className="min-h-24 mt-2 ml-8 flex h-auto flex-col rounded-xl border-2 border-gray-400">
            <textarea
              className="h-auto w-full resize-none appearance-none rounded-xl bg-graphybg py-2 px-3 font-ng leading-tight text-gray-700 focus:outline-none"
              id="reply"
              placeholder="대댓글을 입력하세요."
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
