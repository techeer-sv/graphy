'use client'

import { useInfiniteQuery } from '@tanstack/react-query'
import { useEffect } from 'react'
import { useRouter } from 'next/navigation'
import { useRecoilValue } from 'recoil'

import WriteIcon from '../../public/images/svg/pencil-square.svg'
// TODO: import NavBar from '../components/general/NavBar'
import Banner from '../components/main/Banner'
// TODO: import ProjectCard from '../components/main/ProjectCard'
import searchTextState from '../utils/atoms'

// TODO: type DataObject

export default function Main() {
  const searchText = useRecoilValue(searchTextState)

  const router = useRouter() // react-router-dom useNavigate 사용 선언

  function toWrite() {
    // react-router-dom을 이용한 글쓰기 페이지로 이동 함수
    router.push('/write')
  }

  async function getData({ pageParam = 1 }) {
    const params = { page: pageParam, size: 12 }
    const res = await fetch(
      `${process.env.NEXT_PUBLIC_BASE_URL}/projects/search`,
      {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(params),
      },
    )
    if (!res.ok) {
      throw new Error('프로젝트 검색에 실패했습니다.')
    }

    const data = await res.json()

    return data.data
  }

  const { data, fetchNextPage, hasNextPage, isFetchingNextPage, status } =
    useInfiniteQuery(['projects'], getData, {
      getNextPageParam: (lastPage, pages) =>
        lastPage.length < 12 ? undefined : pages.length + 1,
    })

  useEffect(() => {
    if (!isFetchingNextPage) {
      const handleScroll = () => {
        if (
          document.documentElement.scrollHeight -
            document.documentElement.scrollTop ===
          document.documentElement.clientHeight
        ) {
          fetchNextPage()
        }
      }
      window.addEventListener('scroll', handleScroll)
    }
  }, [fetchNextPage, isFetchingNextPage, searchText])

  if (status === 'loading') {
    return <span>Loading...</span>
  }

  if (status === 'error') {
    return <span>Error fetching data</span>
  }

  return (
    <div className="relative h-auto min-h-screen w-screen bg-gray-50">
      {/* TODO: NavBar */}
      <Banner />
      <div>
        {/* 프로젝트 공유 버튼 */}
        <button
          className="fixed bottom-10 right-10 z-10 my-auto mb-2 flex shrink-0 flex-row items-center rounded-full
          bg-graphyblue px-4 py-1 pt-3 pb-3 font-semibold text-slate-50 drop-shadow-md
          sm:invisible"
          onClick={() => toWrite()}
          aria-label="toWritePage"
          type="button"
        >
          <img className="mr-2 h-5 w-5" src={WriteIcon} alt="WriteIcon" />
          <span className="shrink-0 font-semibold">프로젝트 공유</span>
        </button>

        <div className="mx-10 border-b-2 border-b-neutral-300 pt-0 font-ng-b text-2xl sm:mx-28 sm:mb-5 sm:pt-5">
          {/* All */}
        </div>

        <div>
          {/* TODO: ProjectCard */}
          {hasNextPage && isFetchingNextPage && <span>Loading more...</span>}
        </div>
      </div>
    </div>
  )
}
