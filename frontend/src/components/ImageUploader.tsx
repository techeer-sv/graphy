import React, { useEffect, useRef, useState } from 'react';
import { useRecoilState } from 'recoil';
import ReactS3Client from 'react-aws-s3-typescript';
import { thumbnailUrlState } from '../Recoil';
import s3config from '../s3config';

import imginsert from '../assets/image/imginsert.svg';

//s3 변수 선언
const s3 = new ReactS3Client(s3config);

function ImageUploader() {
  const [image, setImage] = useState<File | null>();
  const [thumbnailUrl, setThumbnailUrl] = useRecoilState(thumbnailUrlState);
  const fileInputRef = useRef<HTMLInputElement>(null);

  //이미지 S3로 업로드 하는 함수
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

  // 이미지 변경마다 uploadImage함수 실행
  useEffect(() => {
    if (image) {
      uploadImage();
    }
  }, [image]);
  //클릭시 파일 리스트에 이미지 넣는 함수
  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const fileList = e.target.files;
    if (fileList && fileList.length > 0) {
      setImage(fileList[0]);
    }
  };
  //이미지 드롭시 이미지 넣는 함수
  const handleDrop = (e: React.DragEvent<HTMLDivElement>) => {
    e.preventDefault();
    e.stopPropagation();
    const fileList = e.dataTransfer.files;
    if (fileList && fileList.length > 0) {
      setImage(fileList[0]);
    }
  };
  //div눌러도 input에 이미지 들어가게 해주는 함수
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
