import { useState } from 'react';

function Reply() {
  const [count, SetCount] = useState(0);
  return (
    <div>
      <div className="mb-2 flex flex-row border-b-2 border-graphyblue pb-2">
        <p className="mr-2 font-ng-b text-lg">전체 댓글 {count}개</p>
        <select className="rounded border border-black bg-graphybg font-ng text-sm">
          <option value="regist_order">등록순</option>
          <option value="newest_order">최신순</option>
          <option value="reply_order">답글순</option>
        </select>
      </div>
      <div className="min-h-24 mb-10 flex h-auto flex-col rounded-xl border">
        <textarea
          className="h-5 w-full resize-none appearance-none rounded-xl bg-graphybg py-2 px-3 font-ng leading-tight text-gray-700 focus:outline-none"
          id="reply"
          placeholder="댓글을 입력하세요."
        />
        <button
          className="focus:shadow-outline m-auto my-2 mr-2 h-8 w-16 appearance-none place-items-end rounded-lg border bg-graphybg font-ng hover:bg-gray-200"
          onClick={() => console.log('등록 버튼 클릭')}
        >
          등록
        </button>
      </div>
    </div>
  );
}

export default Reply;
