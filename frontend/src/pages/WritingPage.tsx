import React, { useRef } from 'react';
import { atom, useRecoilState } from 'recoil';

import imginsert from '/src/images/imginsert.svg';

const titleState = atom({
  key: 'titleState',
  default: '',
});
const tldrState = atom({
  key: 'tldrState',
  default: '',
});
const imageState = atom<File | null>({
  key: 'imageState',
  default: null,
});

const WritingPage = () => {
  const [title, setTitle] = useRecoilState<string>(titleState);
  const [tldr, setTldr] = useRecoilState<string>(tldrState);
  const [image, setImage] = useRecoilState(imageState);
  const fileInputRef = useRef<HTMLInputElement>(null);

  const handleTitleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setTitle(e.target.value);
  };
  const handleTldrChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setTldr(e.target.value);
  };
  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const fileList = e.target.files;
    if (fileList && fileList.length > 0) {
      setImage(fileList[0]);
    }
  };

  const handleDrop = (e: React.DragEvent<HTMLDivElement>) => {
    e.preventDefault();
    e.stopPropagation();
    const fileList = e.dataTransfer.files;
    if (fileList && fileList.length > 0) {
      setImage(fileList[0]);
    }
  };

  const handleClick = () => {
    if (fileInputRef.current) {
      fileInputRef.current.click();
    }
  };

  return (
    <div className="flex justify-center w-screen h-screen bg-white">
      {/*젤 큰 박스*/}
      <div className="px-2 w-11/12 h-5/6 border border-black">
        {/*서식 구역*/}
        <div className="flex flex-row justify-center h-228 border">
          {/*텍스트 구역*/}
          <div className="mr-2 w-10/12 h-228">
            {/*제목 상자*/}
            <input
              autoFocus
              className="shadow appearance-none border rounded mb-2.5 w-full h-49 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline bg-white"
              id="title"
              type="text"
              placeholder="제목을 입력해주세요."
              value={title}
              onChange={handleTitleChange}
            />
            {/*한줄 소개 상자*/}
            <input
              className="shadow appearance-none border rounded w-full mb-2.5 h-49 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline bg-white"
              id="tldr"
              type="text"
              placeholder="한 줄 소개를 입력해주세요."
              value={tldr}
              onChange={handleTldrChange}
            />
            {/*사용 기술 상자*/}
            <div className=" h-110 border">사용기술 자리</div>
          </div>
          {/*사진 드롭박스*/}
          <div
            className="relative flex items-center justify-center border-2 border-dashed border-gray-400 rounded-lg mb-4 w-284 h-228 cursor-pointer"
            onDrop={handleDrop}
            onDragOver={(e) => e.preventDefault()}
            onClick={handleClick}
          >
            <input
              id="image"
              type="file"
              className="hidden"
              ref={fileInputRef}
              onChange={handleFileChange}
            />
            <div className="absolute inset-0 flex flex-col items-center justify-center">
              {image ? (
                <img
                  className="h-full"
                  src={URL.createObjectURL(image)}
                  alt="이미지"
                />
              ) : (
                <div className="text-gray-500 text-center">
                  <img className=" ml-9" src={imginsert} />
                  프로젝트 메인 이미지
                </div>
              )}
            </div>
          </div>
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
