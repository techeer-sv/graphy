'use client'

import React, { useEffect } from 'react'
import { useRouter } from 'next/navigation'
import { useRecoilState, useRecoilValue } from 'recoil'
import dynamic from 'next/dynamic'

// TODO: NavBar 만들어야 함
// import NavBar from '../../components/general/NavBar'
import ImageUploader from '../../../../components/project/submit/imageUploader'
import TechStackSelection from '../../../../components/project/submit/techStackSelection'
import {
  contentsState,
  projectIdState,
  selectedStackState,
  thumbnailUrlState,
  titleState,
  tldrState,
} from '../../../../utils/atoms'

const ToastUIEditor = dynamic(
  () => import('../../../../components/project/submit/toastUIEditor'),
  { ssr: false },
)

export default function NewPost() {
  const [title, setTitle] = useRecoilState(titleState)
  const [tldr, setTldr] = useRecoilState(tldrState)
  const [thumbnailUrl, setThumbnailUrl] = useRecoilState(thumbnailUrlState)
  const contents = useRecoilValue(contentsState)
  const selectedStack = useRecoilValue(selectedStackState)
  const projectId = useRecoilValue(projectIdState)

  const accessToken =
    typeof window !== 'undefined' ? sessionStorage.getItem('accessToken') : null
  const persistToken =
    typeof window !== 'undefined' ? localStorage.getItem('persistToken') : null

  const router = useRouter()

  // 제목 변경 함수
  const handleTitleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const inputValue = e.target.value
    if (inputValue.length > 255) {
      setTitle(inputValue.substring(0, 255))
      throw new Error('대댓글은 255자까지 입력하실 수 있습니다.')
    }
    setTitle(inputValue)
  }
  // 소개 변경 함수
  const handleTldrChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const inputValue = e.target.value
    if (inputValue.length > 255) {
      setTldr(inputValue.substring(0, 255))
      throw new Error('대댓글은 255자까지 입력하실 수 있습니다.')
    }
    setTldr(inputValue)
  }
  const putData = async () => {
    const data = {
      projectName: title,
      content: contents,
      description: tldr,
      techTags: selectedStack,
      thumbNail: thumbnailUrl,
    }

    const res = await fetch(
      `${process.env.NEXT_PUBLIC_BASE_URL}/projects/${projectId}`,
      {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${accessToken || persistToken}`,
        },
        body: JSON.stringify(data),
      },
    )

    if (!res.ok) {
      if (!navigator.onLine) {
        throw new Error('오프라인 상태입니다. 네트워크 연결을 확인해주세요.')
      } else if (title.trim().length === 0) {
        throw new Error('제목을 입력해주세요.')
      } else if (tldr.trim().length === 0) {
        throw new Error('한줄 소개를 입력해주세요.')
      } else if (contents.trim().length === 0) {
        throw new Error('내용을 입력해주세요.')
      } else {
        throw new Error('네트워크 오류')
      }
    }

    setThumbnailUrl('')
    router.push(`/project/post/${projectId}`)
  }

  // 취소 버튼 누를시 메인페이지 이동
  function toMain() {
    router.push('/project')
  }

  useEffect(() => {
    if (!navigator.onLine) {
      throw new Error('오프라인 상태입니다. 네트워크 연결을 확인해주세요.')
    }
    if (!(accessToken || persistToken)) {
      // eslint-disable-next-line no-alert
      alert('로그인시 이용하실 수 있습니다.')
      router.push('/project')
    }
  }, [])

  return (
    <div className="mt-0 flex h-auto w-screen justify-center overflow-hidden bg-[#F9F8F8] pb-10">
      {/* <NavBar /> */}
      {/* 젤 큰 박스 */}
      <div className="mt-16 w-11/12 max-w-1100 overflow-auto border border-black px-2 sm:flex sm:h-5/6  sm:flex-col">
        {/* 서식 구역 */}
        <div className="top-4 mt-2 flex h-228 flex-col justify-center sm:flex-row">
          {/* 텍스트 구역 */}
          <div className="mr-2 mt-64 mb-2 w-full overflow-visible sm:mt-0 sm:w-10/12">
            {/* 제목 상자 */}
            <input
              className="focus:shadow-outline m-0 mb-2.5 h-49 w-full appearance-none rounded border bg-[#F9F8F8] px-3 font-ng leading-tight text-gray-700 shadow focus:outline-none"
              id="title"
              type="text"
              placeholder="제목을 입력해주세요."
              value={title}
              onChange={handleTitleChange}
            />
            {/* 한줄 소개 상자 */}
            <input
              className="focus:shadow-outline m-0 mb-2.5 h-49 w-full appearance-none rounded border bg-[#F9F8F8] px-3 font-ng leading-tight text-gray-700 shadow focus:outline-none sm:h-49"
              id="tldr"
              type="text"
              placeholder="한 줄 소개를 입력해주세요."
              value={tldr}
              onChange={handleTldrChange}
            />
            {/* 사용 기술 상자 */}
            <div className="relative z-10 h-110 w-full bg-[#F9F8F8] font-ng">
              <TechStackSelection />
            </div>
          </div>
          {/* 사진 드롭박스 */}
          <ImageUploader />
        </div>
        {/* 글쓰기 구역 */}
        <div className="relative z-0 mt-60 sm:mt-2">
          <ToastUIEditor />
        </div>
        {/* 버튼 구역 */}
        <div className="mt-20 mb-4 flex justify-end sm:mt-20 lg:mt-12">
          <button
            className="focus:shadow-outline mr-2 h-12 w-24 appearance-none rounded-sm border bg-gray-500 font-ng text-white hover:bg-gray-700"
            onClick={() => toMain()}
            type="button"
          >
            취소
          </button>
          <button
            className="focus:shadow-outline h-12 w-24 appearance-none rounded-sm bg-graphyblue font-ng text-white hover:bg-blue-800"
            onClick={() => putData()}
            type="submit"
          >
            수정
          </button>
        </div>
      </div>
    </div>
  )
}
