import React, { useEffect, useRef } from 'react';
import { useRecoilState } from 'recoil';
import ReactS3Client from 'react-aws-s3-typescript';
import { imageState, thumbnailUrlState } from '../Recoil';
import s3config from '../s3config';

import imginsert from '/src/images/imginsert.svg';

const s3 = new ReactS3Client(s3config);

function ImageUploader() {
  const [image, setImage] = useRecoilState(imageState);
  const [thumbnailUrl, setThumbnailUrl] = useRecoilState(thumbnailUrlState);
  const fileInputRef = useRef<HTMLInputElement>(null);

  const uploadImage = async () => {
    if (image) {
      try {
        const res = await s3.uploadFile(image);
        setThumbnailUrl(res.location);
        console.log(res);
      } catch (error) {
        console.log(error);
      }
    }
  };

  useEffect(() => {
    if (image) {
      uploadImage();
    }
  }, [image]);

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
