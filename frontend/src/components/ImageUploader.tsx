import React, { useRef } from 'react';
import { useRecoilState } from 'recoil';
import { imageState } from '../Recoil';

import imginsert from '/src/images/imginsert.svg';

function ImageUploader() {
  const [image, setImage] = useRecoilState(imageState);
  const fileInputRef = useRef<HTMLInputElement>(null);

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
              <img className="ml-9 font-ng" src={imginsert} alt="이미지 삽입" />
              프로젝트 메인 이미지
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
export default ImageUploader;
