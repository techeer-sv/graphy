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
          <div className="mb-4 w-284 h-228">
            <div
              className="relative flex items-center justify-center border-2 border-dashed border-gray-400 rounded-lg h-228 cursor-pointer"
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
