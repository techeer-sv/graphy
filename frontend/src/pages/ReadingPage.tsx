import React from 'react';

function ReadingPage() {
  return (
    <div>
      {/**전체 컨텐츠 영역**/}
      <div>
        {/**텍스트 영역**/}
        <div>
          {/**제목**/}
          <div></div>
          {/**한줄소개**/}
          <div></div>
          {/**사용기술**/}
          <div></div>
        </div>
        {/**글 영역**/}
        <div></div>
        {/**버튼 영역**/}
        <div>
          <div className="mt-20 mb-4 flex justify-end sm:mt-20 lg:mt-12">
            <button
              className="focus:shadow-outline mr-2 h-12 w-24 appearance-none rounded-sm border bg-gray-500 font-ng text-white hover:bg-gray-700"
              onClick={() => console.log('수정 버튼 클릭')}
            >
              수정
            </button>
            <button
              className="focus:shadow-outline mr-2 h-12 w-24 appearance-none rounded-sm border bg-gray-500 font-ng text-white hover:bg-gray-700"
              onClick={() => console.log('삭제 버튼 클릭')}
            >
              삭제
            </button>
            <button
              className="focus:shadow-outline h-12 w-24 appearance-none rounded-sm bg-blue-500 font-ng text-white hover:bg-blue-700"
              onClick={() => console.log('글작성 버튼 클릭')}
            >
              글작성
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default ReadingPage;
