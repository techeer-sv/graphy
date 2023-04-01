import React, { useEffect, useState } from 'react';
import { useRecoilState } from 'recoil';
import QuillWrtten from '../components/QuillWritten';
import { readContentsState } from '../Recoil';

function ReadingPage() {
  const [title, setTitle] = useState<string>('제목');
  const [tldr, setTldr] = useState<string>('한 줄 소개');
  const [stacks, setStacks] = useState([]);
  const [readContents, setReadContents] = useRecoilState(readContentsState);

  useEffect(() => {
    if (title) {
      setTitle(title);
    }
  }, [title]);

  useEffect(() => {
    if (tldr) {
      setTldr(tldr);
    }
  }, [tldr]);

  useEffect(() => {
    if (stacks.length !== 0) {
      setStacks(stacks);
    }
  }, [stacks]);

  return (
    <div className="mt-0 flex h-screen w-screen justify-center bg-[#F9F8F8] pb-10">
      {/**전체 컨텐츠 영역**/}
      <div className="w-11/12 max-w-1100 px-2 sm:flex sm:h-5/6 sm:flex-col">
        {/**텍스트 영역**/}
        <div>
          {/**제목**/}
          <div className=" mt-10 mb-4 text-center font-ng-eb text-4xl">
            {title}
          </div>
          <div className="flex flex-col border-b border-black sm:flex-row">
            {/**한줄소개**/}
            <div className="mb-2 font-ng-b text-2xl">{tldr}</div>
            {/**사용기술**/}
            {stacks.length !== 0 ? (
              <div className="mb-2 font-ng-b text-2xl sm:mx-auto sm:mr-2">
                {stacks}
              </div>
            ) : (
              <div className="mb-2 font-ng-b text-2xl sm:mx-auto sm:mr-2">
                사용 기술
              </div>
            )}
          </div>
        </div>
        {/**글 영역**/}
        <div>
          <QuillWrtten />
        </div>
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
