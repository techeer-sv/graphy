import React, { useState } from 'react';
import { atom, useRecoilState } from 'recoil';

const titleState = atom({
  key: 'titleState',
  default: '',
});
const tldrState = atom({
  key: 'tldrState',
  default: '',
});

const WritingPage = () => {
  const [title, setTitle] = useRecoilState<string>(titleState);
  const [tldr, setTldr] = useRecoilState<string>(tldrState);

  const handleTitleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setTitle(e.target.value);
  };
  const handleTldrChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setTldr(e.target.value);
  };

  return (
    <div>
      {/*젤 큰 박스*/}
      <div>
        {/*서식 구역*/}
        <div>
          {/*텍스트 구역*/}
          <div>
            {/*제목 상자*/}
            <div className="mb-4">
              <input
                className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                id="title"
                type="text"
                placeholder="제목을 입력해주세요."
                value={title}
                onChange={handleTitleChange}
              />
            </div>
            {/*한줄 소개 상자*/}
            <div className="mb-4">
              <input
                className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                id="tldr"
                type="text"
                placeholder="한 줄 소개를 입력해주세요."
                value={tldr}
                onChange={handleTldrChange}
              />
            </div>
            {/*사용 기술 상자*/}
            <div></div>
          </div>
          {/*사진 드롭박스*/}
          <div></div>
        </div>
        {/*글쓰기 구역*/}
        <div></div>
        {/*버튼 구역*/}
        <div></div>
      </div>
    </div>
  );
};

export default WritingPage;
