'use client'

import Image from 'next/image'
import { useEffect, useState, useCallback } from 'react'
import { useRouter } from 'next/navigation'
import { useRecoilState, useRecoilValue } from 'recoil'
import gptIcon from '../../../../../public/images/svg/gptIcon.svg'
import ProfileIcon from '../../../../../public/images/svg/profileIcon.svg'
import GptModal from '../../../../components/read/GptModal'
import ToastViewer from '../../../../components/read/ToastViewer'
import Reply from '../../../../components/read/Reply'

import {
  contentsState,
  selectedStackState,
  titleState,
  tldrState,
  refreshState,
} from '../../../../utils/atoms'
import AllStacks from '../../../../utils/stacks'

type ReadReplyObject = {
  commentId: number
  childCount: number
  content: string
  createdAt: string
}

type ParamsType = {
  params: {
    postId: number
  }
}

export default function ReadingPage({ params }: ParamsType) {
  const [title, setTitle] = useRecoilState(titleState)
  const [tldr, setTldr] = useRecoilState(tldrState)
  const [selectedStack, setSelectedStack] = useRecoilState(selectedStackState)
  const [, setContents] = useRecoilState(contentsState)
  const [readReply, setReadReply] = useState<ReadReplyObject[]>([])
  const refresh = useRecoilValue(refreshState)
  const accessToken =
    typeof window !== 'undefined' ? sessionStorage.getItem('accessToken') : null
  const persistToken =
    typeof window !== 'undefined' ? localStorage.getItem('persistToken') : null
  const router = useRouter()

  const [isOpenModal, setOpenModal] = useState<boolean>(false)
  const [nickname, setNickname] = useState<string>('')
  const [createdAt, setCreatedAt] = useState<string>('')

  const onClickToggleModal = useCallback(() => {
    setOpenModal(!isOpenModal)
  }, [isOpenModal])

  function toWrite() {
    router.push('/write')
  }

  function toModify() {
    router.push('/modify')
  }
  // GET요청 보내서 데이터 가져오고 받은 데이터 변수에 넣어주는 함수
  async function getData() {
    const res = await fetch(
      `${process.env.NEXT_PUBLIC_BASE_URL}/projects/${Number(params.postId)}`,
      {
        headers: {
          'Content-Type': 'application/json',
        },
      },
    )

    if (!res.ok) {
      const error = await res.json()
      if (error.data.message === '이미 삭제되거나 존재하지 않는 프로젝트') {
        alert('이미 삭제되거나 존재하지 않는 프로젝트입니다.')
      } else {
        alert('프로젝트 상세 조회 실패')
        throw new Error('프로젝트 상세 조회 실패')
      }
      router.push('/')
    }

    const resData = await res.json()

    setTitle(resData.data.projectName)
    setTldr(resData.data.description)
    setSelectedStack(resData.data.techTags)
    setContents(resData.data.content)
    setReadReply(resData.data.commentsList)
    setNickname(resData.data.member.nickname)
    setCreatedAt(resData.data.createdAt)
  }
  // DELETE 요청 보내는 함수
  async function deleteData() {
    if (!navigator.onLine) {
      alert('오프라인 상태입니다. 네트워크 연결을 확인해주세요.')
    } else {
      const res = await fetch(
        `${process.env.NEXT_PUBLIC_BASE_URL}/projects/${Number(params.postId)}`,
        {
          method: 'DELETE',
          headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${accessToken || persistToken}`,
          },
        },
      )

      router.push('/')

      if (!res.ok) {
        const error = await res.json()

        if (error.error === '이미 삭제되거나 존재하지 않는 프로젝트') {
          alert('이미 삭제되거나 존재하지 않는 프로젝트입니다.')
        } else {
          alert('프로젝트 삭제 실패')
          throw new Error('프로젝트 삭제 실패')
        }
        router.push('/')
      } else {
        alert('프로젝트 삭제 성공')
        router.push('/')
      }
    }
  }

  // 이미지 찾는 함수
  function findImage(tag: string) {
    return AllStacks.map((x) => x.image)[
      AllStacks.map((x) => x.name).findIndex((x) => x === tag)
    ]
  }

  // 렌더링할때 데이터 가져옴
  useEffect(() => {
    getData()
  }, [refresh])
  // 제목 변경시 재 렌더링
  useEffect(() => {
    if (title) {
      setTitle(title)
    }
  }, [title])
  // 소개 변경시 재 렌더링
  useEffect(() => {
    if (tldr) {
      setTldr(tldr)
    }
  }, [tldr])
  // 선택 스택 변경시 재 렌더링
  useEffect(() => {
    if (selectedStack.length !== 0) {
      setSelectedStack(selectedStack)
    }
  }, [selectedStack])

  return (
    <div className="mt-0 flex h-auto w-screen justify-center bg-graphybg pb-10">
      {/** 전체 컨텐츠 영역* */}
      <div className="mt-16 w-11/12 max-w-1100 px-2 sm:flex sm:h-5/6 sm:flex-col">
        {/* AI 고도화 버튼 */}
        {accessToken || persistToken ? (
          <button
            className="fixed bottom-10 right-10 z-10 my-auto mb-2 flex shrink-0 flex-row items-center rounded-full  bg-graphyblue
          px-4 py-1 pt-3 pb-3 font-semibold text-slate-50 drop-shadow-md hover:bg-button"
            onClick={onClickToggleModal}
            type="button"
          >
            <Image className="mr-2 h-5 w-5" src={gptIcon} alt="gptIcon" />
            <span className="shrink-0 font-semibold">AI 고도화 추천</span>
          </button>
        ) : null}

        {isOpenModal ? (
          <GptModal onClickToggleModal={onClickToggleModal} />
        ) : null}
        {/** 텍스트 영역* */}
        <div className="h-auto border-b-2 border-graphyblue pb-2">
          {/** 제목* */}
          <div className="mt-10 mb-4 text-center font-ng-eb text-4xl">
            {title}
          </div>
          <div className="mb-4 flex items-center border-b-2 border-graphyblue pb-2">
            <Image className="h-6 w-6" src={ProfileIcon} alt="ProfileIcon" />
            <span className="ml-1 font-ng-b text-lg">{nickname}</span>
            <span className="text-ml font-ng text-lg text-gray-500">
              &nbsp; |&nbsp; {createdAt.slice(0, 10)}
            </span>
          </div>
          <div className="mb-2 flex flex-row overflow-hidden hover:overflow-x-auto">
            <div className=" mb-2 mr-3 shrink-0 font-ng-b text-xl text-zinc-500 sm:text-2xl ">
              한줄 소개
            </div>
            {/** 한줄소개* */}
            <div className="mb-2 font-ng-b text-xl sm:text-2xl">{tldr}</div>
          </div>
          {/** 사용기술* */}
          {selectedStack.length !== 0 ? (
            <div className="flex flex-row items-center overflow-hidden hover:overflow-x-auto">
              <div className="mb-2 mr-3 shrink-0 font-ng-b text-xl text-zinc-500 sm:text-2xl">
                기술 스택
              </div>
              {selectedStack.map((x: string) => (
                <div
                  key={x}
                  className="mr-2 mb-2 flex h-auto shrink-0 flex-row items-center rounded-full border py-1 pr-3"
                >
                  <Image
                    className="mx-3 my-1 h-8 w-8"
                    src={findImage(x)}
                    alt="Stack"
                  />
                  <p className="shrink-0 font-ng-b">{x}</p>
                </div>
              ))}
            </div>
          ) : (
            <div className="flex flex-row">
              <div className="mb-2 mr-2 font-ng-b text-xl text-zinc-500 sm:text-2xl">
                기술 스택
              </div>
              <div className="mb-2 font-ng-b text-xl sm:text-2xl">없음</div>
            </div>
          )}
        </div>
        {/** 글 영역* */}
        <ToastViewer />
        {/** 버튼 영역* */}
        {accessToken || persistToken ? (
          <div className="mt-20 flex justify-end pb-4 sm:mt-20 lg:mt-12">
            <button
              className="focus:shadow-outline mr-2 h-12 w-24 appearance-none rounded-sm border bg-gray-500 font-ng text-white hover:bg-gray-700"
              onClick={() => toModify()}
              type="submit"
            >
              수정
            </button>
            <button
              className="focus:shadow-outline mr-2 h-12 w-24 appearance-none rounded-sm border bg-gray-500 font-ng text-white hover:bg-gray-700"
              onClick={() => deleteData()}
              type="button"
            >
              삭제
            </button>
            <button
              className="focus:shadow-outline h-12 w-24 appearance-none rounded-sm bg-graphyblue font-ng text-white hover:bg-blue-800"
              onClick={() => toWrite()}
              type="submit"
            >
              글작성
            </button>
          </div>
        ) : null}
        <div className="mt-4">
          <Reply contents={readReply} setReadReply={setReadReply} />
        </div>
      </div>
    </div>
  )
}
