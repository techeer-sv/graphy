import React, { useRef } from 'react';
import { useRecoilState } from 'recoil';

import QuillEditor from '../components/QuillEditor';
import TechStackSelection from '../components/TechStackSelection';
import imginsert from '/src/images/imginsert.svg';
import { titleState, tldrState, imageState } from '../Recoil';

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
    <div className="mt-0 mb-10 flex h-auto w-screen justify-center bg-[#F9F8F8]">
      {/*젤 큰 박스*/}
      <div className="w-11/12 border border-black px-2 sm:flex sm:h-5/6 sm:flex-col">
        {/*서식 구역*/}
        <div className="mt-2 flex h-228 flex-col justify-center sm:flex-row">
          {/*텍스트 구역*/}
          <div className="mr-2 mt-64 mb-2 w-full overflow-visible sm:mt-0 sm:w-10/12">
            {/*제목 상자*/}
            <input
              autoFocus
              className="focus:shadow-outline m-0 mb-2.5 h-49 w-full appearance-none rounded border bg-[#F9F8F8] px-3 font-ng leading-tight text-gray-700 shadow focus:outline-none"
              id="title"
              type="text"
              placeholder="제목을 입력해주세요."
              value={title}
              onChange={handleTitleChange}
            />
            {/*한줄 소개 상자*/}
            <input
              className="focus:shadow-outline m-0 mb-2.5 h-49 w-full appearance-none rounded border bg-[#F9F8F8] px-3 font-ng leading-tight text-gray-700 shadow focus:outline-none sm:h-49"
              id="tldr"
              type="text"
              placeholder="한 줄 소개를 입력해주세요."
              value={tldr}
              onChange={handleTldrChange}
            />
            {/*사용 기술 상자*/}
            <div className="relative z-50 h-110 w-full bg-[#F9F8F8] font-ng">
              <TechStackSelection />
            </div>
          </div>
          {/*사진 드롭박스*/}
          <div className=" w-full bg-[#F9F8F8] sm:w-284">
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
                  <div className="text-center font-ng text-gray-500">
                    <img
                      className="ml-9 font-ng"
                      src={imginsert}
                      alt="이미지 삽입"
                    />
                    프로젝트 메인 이미지
                  </div>
                )}
              </div>
            </div>
          </div>
        </div>
        {/*글쓰기 구역*/}
        <div className="relative z-0 mt-60 sm:mt-2">
          <QuillEditor />
        </div>
        {/*버튼 구역*/}
        <div className="mt-20 mb-4 flex justify-end sm:mt-20 lg:mt-12">
          <button
            className="focus:shadow-outline mr-2 h-12 w-24 appearance-none rounded-sm border bg-gray-500 font-ng text-white hover:bg-gray-700"
            onClick={() => console.log('취소 버튼 클릭')}
          >
            취소
          </button>
          <button
            className="focus:shadow-outline h-12 w-24 appearance-none rounded-sm bg-blue-500 font-ng text-white hover:bg-blue-700"
            onClick={() => console.log('저장 버튼 클릭')}
          >
            저장
          </button>
        </div>
      </div>
    </div>
  );
};

export default WritingPage;
