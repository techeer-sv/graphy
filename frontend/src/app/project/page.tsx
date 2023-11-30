'use client'

import { useInfiniteQuery } from '@tanstack/react-query'
import { useEffect, useState } from 'react'
import { useRouter } from 'next/navigation'
import { useRecoilValue } from 'recoil'
import Image from 'next/image'

import WriteIcon from '../../../public/images/svg/pencil-square.svg'
import Banner from '../../components/project/main/Banner'
import ProjectCard from '../../components/project/main/ProjectCard'
import { searchTextState } from '../../utils/atoms'

type DataObject = {
  id: number
  createdAt: string
  projectName: string
  description: string
  techTags: string[]
  thumbNail: string
}

export default function ProjectMain() {
  const searchText = useRecoilValue(searchTextState)
  const [rankData, setRankData] = useState<DataObject[]>([])

  const router = useRouter()

  function toWrite() {
    router.push('/project/write')
  }

  async function getData({ pageParam = 1 }) {
    const params = new URLSearchParams()
    params.set('page', String(pageParam))
    params.set('size', '12')

    const res = await fetch(
      `${process.env.NEXT_PUBLIC_BASE_URL}/projects?${params.toString()}`,
      {
        headers: {
          'Content-Type': 'application/json',
        },
      },
    )

    if (!res.ok) {
      throw new Error('프로젝트 검색에 실패했습니다.')
    }

    const data = await res.json()

    return data.data
  }

  async function getRankData() {
    const res = await fetch(
      `${process.env.NEXT_PUBLIC_BASE_URL}/projects/rank`,
      {
        headers: {
          'Content-Type': 'application/json',
        },
      },
    )

    if (!res.ok) {
      throw new Error('프로젝트 검색에 실패했습니다.')
    }

    const data = await res.json()
    setRankData(data.data)
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
    getRankData()
  }, [fetchNextPage, isFetchingNextPage, searchText])

  if (status === 'loading') {
    return <span>Loading...</span>
  }

  if (status === 'error') {
    return <span>Error fetching data</span>
  }

  return (
    <div className="relative h-auto min-h-screen w-screen bg-gray-50">
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
          <Image
            className="mr-2 h-5 w-5"
            src={WriteIcon}
            alt="WriteIcon"
            quality={50}
          />
          <span className="shrink-0 font-semibold">프로젝트 공유</span>
        </button>

        <span className="mx-28 text-lg font-ng-b">🔥 금주의 인기 프로젝트</span>
        <div className="mx-28 flex sm:pt-8 border-b-lime-800 overflow-x-auto scrollbar-hide">
          {rankData.map((item: DataObject) => (
            <div className="mx-8 mb-10" key={item.id}>
              <ProjectCard items={item} index={0} />
            </div>
          ))}
        </div>

        <div className="mt-8">
          <div className="mx-28 pl-2 text-xl font-ng-b border-b-2">All</div>
          {data.pages.map((group, i) => (
            <div
              className="mx-8 flex flex-wrap justify-center pt-6 sm:pt-8"
              key={group[i]?.id}
            >
              {group.map((item: DataObject) => (
                <div className="mx-8 mb-10" key={item.id}>
                  <ProjectCard items={item} index={i} />
                </div>
              ))}
            </div>
          ))}
          {hasNextPage && isFetchingNextPage && <span>Loading more...</span>}
        </div>
      </div>
    </div>
  )
}
