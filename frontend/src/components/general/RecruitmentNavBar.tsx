'use client'

import { useState, useEffect, useCallback } from 'react'
import { useRouter, usePathname } from 'next/navigation'
import { useRecoilState } from 'recoil'
import Image from 'next/image'
import { PiPaperPlaneTilt } from 'react-icons/pi'
import { TfiBell } from 'react-icons/tfi'

import WriteIcon from '../../../public/images/svg/pencil-square.svg'
import ProfileIcon from '../../../public/images/svg/profileIcon.svg'
import { usernameState } from '../../utils/atoms'
import NoticeModal from '../recruitment/noticification/NoticeModal'

export default function RecruitmentNavBar({
  children,
}: {
  children: React.ReactNode
}) {
  const accessToken =
    typeof window !== 'undefined' ? sessionStorage.getItem('accessToken') : null
  const persistToken =
    typeof window !== 'undefined' ? localStorage.getItem('persistToken') : null
  const [username, setUsername] = useRecoilState(usernameState)
  const [btnText, setBtnText] = useState<string>('로그인')

  const [isOpenModal, setOpenModal] = useState<boolean>(false)

  const router = useRouter()
  const pathname = usePathname()

  const onClickToggleModal = useCallback(() => {
    setOpenModal(!isOpenModal)
  }, [isOpenModal])

  function signOut() {
    setUsername('')
    if (accessToken) {
      sessionStorage.removeItem('accessToken')
    } else {
      localStorage.removeItem('persistToken')
    }
  }

  const handleLogin = () => {
    if (accessToken || persistToken) {
      signOut()
      setBtnText('로그인')
    } else {
      router.push('/project/login')
    }
  }

  useEffect(() => {
    if (accessToken || persistToken) {
      setBtnText('로그아웃')
    } else {
      setBtnText('로그인')
    }
  }, [pathname])

  if (pathname === '/project/login' || pathname === '/project/registration') {
    return children
  }

  return (
    <div>
      <div className="fixed z-20 mb-5 w-screen flex justify-between content-center overflow-hidden border-b border-zinc-400 bg-white pt-3 pb-3 px-8 align-middle font-ng-eb">
        {/* 로고 */}
        <button
          onClick={() => router.push('/')}
          className="hidden font-lato-b text-4xl text-graphyblue sm:block"
          type="button"
        >
          Graphy
        </button>
        <button
          onClick={() => router.push('/')}
          className="font-lato-b text-4xl text-graphyblue sm:hidden"
          type="button"
        >
          G
        </button>
        <div className="flex">
          <button
            className=" mr-4 whitespace-nowrap rounded-full bg-graphyblue px-4 text-white"
            type="button"
            onClick={() => handleLogin()}
          >
            {btnText}
          </button>

          {/* 프로젝트 작성 버튼 */}
          <button
            className="invisible mx-auto mr-4 flex h-0 w-0 shrink-0 flex-row flex-nowrap items-center rounded-full bg-graphyblue text-white sm:visible sm:mr-5
        sm:h-auto sm:w-auto sm:px-4 sm:py-1"
            onClick={() => router.push('/project/new-post')}
            aria-label="toWritePage"
            type="button"
          >
            <Image className="mr-2 h-5 w-5" src={WriteIcon} alt="WriteIcon" />
            <span className="font-semibold">프로젝트 공유</span>
          </button>

          <div className="flex h-full items-between relative gap-4">
            <button type="button" className="text-[10px] font-light">
              <PiPaperPlaneTilt size="28" />
            </button>
            <button
              onClick={onClickToggleModal}
              type="button"
              className="text-[10px] font-light"
            >
              <TfiBell size="28" />
            </button>
            {/* 마이페이지 아이콘 */}
            <button
              style={{ display: btnText === '로그인' ? 'none' : 'block' }}
              type="button"
              onClick={() => router.push(`/project/profile/${username}`)}
            >
              <Image
                className="h-8 w-8 appearance-none"
                src={ProfileIcon}
                alt="ProfileIcon"
              />
            </button>
          </div>
        </div>
      </div>
      {isOpenModal ? (
        <NoticeModal onClickToggleModal={onClickToggleModal} />
      ) : null}
      {children}
    </div>
  )
}
