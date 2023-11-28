'use client'

import { ChangeEvent, useState, useEffect } from 'react'
import { useRouter, usePathname } from 'next/navigation'
import { useRecoilState } from 'recoil'
import Image from 'next/image'

import WriteIcon from '../../../public/images/svg/pencil-square.svg'
import ProfileIcon from '../../../public/images/svg/profileIcon.svg'
import SearchIcon from '../../../public/images/png/searchIcon.png'
import { searchTextState, usernameState } from '../../utils/atoms'

export default function ProjectNavBar({
  children,
}: {
  children: React.ReactNode
}) {
  const accessToken =
    typeof window !== 'undefined' ? sessionStorage.getItem('accessToken') : null
  const persistToken =
    typeof window !== 'undefined' ? localStorage.getItem('persistToken') : null
  const [searchText, SetSearchText] = useRecoilState(searchTextState)
  const [username, setUsername] = useRecoilState(usernameState)
  const [btnText, setBtnText] = useState<string>('로그인')

  const router = useRouter()
  const pathname = usePathname()

  const getSearchData = (e: ChangeEvent<HTMLInputElement>) => {
    SetSearchText(e.target.value)
  }

  function signOut() {
    setUsername('')
    if (accessToken) {
      sessionStorage.removeItem('accessToken')
    } else {
      localStorage.removeItem('persistToken')
    }
  }

  const handleSearch = () => {
    if (searchText === '' || searchText === '@') {
      router.push('/')
    } else if (searchText.charAt(0) === '@') {
      const searchUserName = searchText.substring(1)
      router.push(`/project/search-user/${searchUserName}`)
    } else {
      router.push(`/project/search-post/${searchText}`)
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
      <div className="fixed z-20 mb-5 flex w-screen flex-row content-center overflow-hidden border-b border-zinc-400 bg-white pt-3 pb-3 align-middle font-ng-eb">
        {/* 로고 */}
        <button
          onClick={() => router.push('/')}
          className="ml-8 hidden font-lato-b text-4xl text-graphyblue sm:block"
          type="button"
        >
          Graphy
        </button>
        <button
          onClick={() => router.push('/')}
          className="ml-8 font-lato-b text-4xl text-graphyblue sm:hidden"
          type="button"
        >
          G
        </button>

        {/* 검색창 */}
        <div className="relative mx-4 flex h-auto w-full items-center rounded-xl border">
          <input
            value={searchText}
            onChange={getSearchData}
            type="text"
            alt="search"
            placeholder="search"
            className="h-auto w-full appearance-none pl-2 outline-none"
            onKeyPress={(e) => {
              if (e.key === 'Enter') {
                handleSearch()
              }
            }}
          />
          <button
            className="mr-2"
            onClick={handleSearch}
            aria-label="SearchButton"
            type="button"
          >
            <Image className="h-6 w-auto" src={SearchIcon} alt="SearchIcon" />
          </button>
        </div>
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
        {/* 마이페이지 아이콘 */}
        <button
          className="mr-12"
          style={{ display: btnText === '로그인' ? 'none' : 'block' }}
          type="button"
          onClick={() => router.push(`/project/profile/${username}`)}
        >
          <Image
            className="fixed top-4 right-4 h-8 w-8 appearance-none"
            src={ProfileIcon}
            alt="ProfileIcon"
          />
        </button>
      </div>
      {children}
    </div>
  )
}
