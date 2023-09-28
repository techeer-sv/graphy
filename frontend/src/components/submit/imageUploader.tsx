'use client'

import React, { useEffect, useRef, useState } from 'react'
import ReactS3Client from 'react-aws-s3-typescript'
import { useRecoilState } from 'recoil'

import Image from 'next/image'
import imginsert from '../../../public/images/svg/imginsert.svg'
import { thumbnailUrlState } from '../../utils/atoms'
import s3config from '../../utils/s3config'

const s3 = new ReactS3Client(s3config)

function ImageUploader() {
  const [image, setImage] = useState<File | null>()
  const [, setThumbnailUrl] = useRecoilState(thumbnailUrlState)
  const fileInputRef = useRef<HTMLInputElement>(null)

  async function uploadImage() {
    if (image) {
      try {
        const res = await s3.uploadFile(image)
        setThumbnailUrl(res.location)
      } catch (error: unknown) {
        if (!navigator.onLine) {
          throw new Error('오프라인 상태입니다. 네트워크 연결을 확인해주세요.')
        } else {
          // eslint-disable-next-line no-console
          console.log(error)
          throw new Error('이미지 업로드 실패')
        }
      }
    }
  }

  // 클릭시 파일 리스트에 이미지 넣는 함수
  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const fileList = e.target.files
    if (fileList && fileList.length > 0) {
      setImage(fileList[0])
    }
  }

  // 이미지 드롭시 이미지 넣는 함수
  const handleDrop = (e: React.DragEvent<HTMLButtonElement>) => {
    e.preventDefault()
    e.stopPropagation()
    const fileList = e.dataTransfer.files
    if (fileList && fileList.length > 0) {
      setImage(fileList[0])
    }
  }

  // div눌러도 input에 이미지 들어가게 해주는 함수
  const handleClick = () => {
    if (fileInputRef.current) {
      fileInputRef.current.click()
    }
  }

  // 이미지 변경마다 uploadImage함수 실행
  useEffect(() => {
    if (image) {
      uploadImage()
    }
  }, [image])

  return (
    <div className=" w-full bg-[#F9F8F8] sm:w-284">
      <button
        className="relative mb-4 flex h-228 w-full cursor-pointer items-center justify-center rounded-lg border-2 border-dashed border-gray-400"
        onDrop={handleDrop}
        onDragOver={(e) => e.preventDefault()}
        onClick={handleClick}
        type="button"
      >
        <input
          id="image"
          data-testid="image"
          type="file"
          className="hidden"
          ref={fileInputRef}
          onChange={handleFileChange}
        />
        <div className="absolute inset-0 flex flex-col items-center justify-center">
          {image ? (
            <Image
              className="h-full"
              src={URL.createObjectURL(image)}
              alt="이미지"
            />
          ) : (
            <div className="text-center font-ng text-gray-500">
              <Image
                className="ml-9 font-ng"
                src={imginsert}
                alt="이미지 삽입"
              />
              프로젝트 메인 이미지
            </div>
          )}
        </div>
      </button>
    </div>
  )
}

export default ImageUploader
