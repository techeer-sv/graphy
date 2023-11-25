'use client'

import { useInfiniteQuery } from '@tanstack/react-query'
import Image from 'next/image'
import { useRouter } from 'next/navigation'
import { useEffect, useState } from 'react'
import { useRecoilValue } from 'recoil'
import MultipleFilter from '../../components/recruitment/main/MultipleFilter'
import { PositionType } from '../../utils/types'
import { filterState } from '../../utils/atoms'
import Filter from '../../components/recruitment/main/Filter'
import RecruitmentCard from '../../components/recruitment/main/RecruitmentCard'
import WriteIcon from '../../../public/images/svg/pencil-square.svg'

export type RecruitmentDataType = {
  id: number
  nickname: string
  title: string
  position: PositionType
  techTags: string[]
  recruiting: boolean
}

export default function Recruitment() {
  const filter = useRecoilValue(filterState)
  const [postCount, setPostCount] = useState(0)
  const router = useRouter()

  function toWrite() {
    router.push('/project/write')
  }

  const getData = async ({ pageParam = 1 }) => {
    const params = new URLSearchParams()
    params.set('page', String(pageParam))
    params.set('size', '12')

    const positions = filter
      .filter((v) => v.category === 'position')
      .map((v) => v.name)
    const skills = filter
      .filter((v) => v.category === 'skill')
      .map((v) => v.name)

    const keyword = filter.find((v) => v.category === 'keyword')

    const isRecruiting = filter.find((v) => v.category === 'isRecruiting')

    positions.forEach((position) => {
      params.append('positions', position)
    })

    skills.forEach((skill) => {
      params.append('tags', skill)
    })

    if (keyword) {
      params.append('keyword', keyword.name)
    }

    if (isRecruiting) {
      params.append('isRecruiting', String(isRecruiting.name))
    }

    const res = await fetch(
      `${process.env.NEXT_PUBLIC_BASE_URL}/recruitments?${params.toString()}`,
      {
        headers: {
          'Content-Type': 'application/json',
        },
      },
    )

    if (!res.ok) {
      const data = await res.json()
      throw new Error(data.message)
    }

    const data = await res.json()
    setPostCount(data.data.length)
    return data.data
  }

  const { data, fetchNextPage, hasNextPage, isFetchingNextPage, status } =
    useInfiniteQuery(['recruitments', filter], getData, {
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
  }, [fetchNextPage, isFetchingNextPage, filter])

  if (status === 'loading') {
    return <span>Loading...</span>
  }

  if (status === 'error') {
    return <span>Error fetching data</span>
  }

  return (
    <div className="relative h-auto min-h-screen w-screen pt-28 pb-12 flex flex-col items-center ">
      <MultipleFilter />
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
      <Filter postCount={postCount} />
      {data.pages.map((group, i) => (
        <div
          className="relative mx-8 flex flex-wrap justify-center"
          key={group[i]?.id}
        >
          {group.map((item: RecruitmentDataType) => (
            <div className="mx-8" key={item.id}>
              <RecruitmentCard item={item} />
            </div>
          ))}
        </div>
      ))}
      {hasNextPage && isFetchingNextPage && <span>Loading more...</span>}
    </div>
  )
}
