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
    <div className="mx-0 flex h-screen w-screen justify-center bg-white">
      {/*젤 큰 박스*/}
      <div className="w-11/12 border border-black px-2 sm:h-5/6">
        {/*서식 구역*/}
        <div className="mt-2 flex h-228 flex-col justify-center sm:flex-row">
          {/*텍스트 구역*/}
          <div className="mr-2 mt-64 mb-2 w-full sm:mt-0 sm:w-10/12">
            {/*제목 상자*/}
            <input
              autoFocus
              className="focus:shadow-outline m-0 mb-2.5 h-49 w-full appearance-none rounded border bg-white px-3 leading-tight text-gray-700 shadow focus:outline-none"
              id="title"
              type="text"
              placeholder="제목을 입력해주세요."
              value={title}
              onChange={handleTitleChange}
            />
            {/*한줄 소개 상자*/}
            <input
              className="focus:shadow-outline m-0 mb-2.5 h-49 w-full appearance-none rounded border bg-white px-3 leading-tight text-gray-700 shadow focus:outline-none sm:h-49"
              id="tldr"
              type="text"
              placeholder="한 줄 소개를 입력해주세요."
              value={tldr}
              onChange={handleTldrChange}
            />
            {/*사용 기술 상자*/}
            <div className="h-110 border">사용기술 자리</div>
          </div>
          {/*사진 드롭박스*/}
          <div className="w-full sm:w-284">
            <div
              className="relative mb-4 flex h-228 w-full cursor-pointer items-center justify-center rounded-lg border-2 border-dashed border-gray-400"
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
                  <div className="text-center text-gray-500">
                    <img className="ml-9" src={imginsert} alt="이미지 삽입" />
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
