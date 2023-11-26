'use client'

import { useInfiniteQuery } from '@tanstack/react-query'
import Image from 'next/image'
import { useRouter } from 'next/navigation'
import { useEffect, useState } from 'react'
import { useRecoilValue } from 'recoil'
import MultipleFilter from '../../components/recruitment/main/MultipleFilter'
import { PositionType } from '../../utils/types'
import {
  multiplefilterState,
  keywordfilterState,
  recruitfilterState,
} from '../../utils/atoms'
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
  const multipleFilter = useRecoilValue(multiplefilterState)
  const keywordFilter = useRecoilValue(keywordfilterState)
  const recruitFilter = useRecoilValue(recruitfilterState)

  const [postCount, setPostCount] = useState(0)
  const router = useRouter()

  function toWrite() {
    router.push('/project/write')
  }

  const getData = async ({ pageParam = 1 }) => {
    const params = new URLSearchParams()
    params.set('page', String(pageParam))
    params.set('size', '12')

    const positions = multipleFilter
      .filter((v) => v.category === 'position')
      .map((v) => v.name)
    const skills = multipleFilter
      .filter((v) => v.category === 'skill')
      .map((v) => v.name)

    positions.forEach((position) => {
      params.append('positions', position)
    })

    skills.forEach((skill) => {
      params.append('tags', skill)
    })

    if (keywordFilter) {
      params.append('keyword', keywordFilter)
    }

    if (recruitFilter) {
      params.append('isRecruiting', String(recruitFilter))
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
    useInfiniteQuery(
      ['recruitments', multipleFilter, keywordFilter, recruitFilter],
      getData,
      {
        getNextPageParam: (lastPage, pages) =>
          lastPage.length < 12 ? undefined : pages.length + 1,
      },
    )

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
  }, [fetchNextPage, isFetchingNextPage, multipleFilter])

  if (status === 'loading') {
    return <span>Loading...</span>
  }

  if (status === 'error') {
    return <span>Error fetching data</span>
  }

  return (
    <div className="relative flex flex-col items-center w-screen h-auto min-h-screen pb-12 pt-28 ">
      <MultipleFilter />
      {/* 프로젝트 공유 버튼 */}
      <button
        className="fixed z-10 flex flex-row items-center px-4 py-1 pt-3 pb-3 my-auto mb-2 font-semibold rounded-full bottom-10 right-10 shrink-0 bg-graphyblue text-slate-50 drop-shadow-md sm:invisible"
        onClick={() => toWrite()}
        aria-label="toWritePage"
        type="button"
      >
        <Image
          className="w-5 h-5 mr-2"
          src={WriteIcon}
          alt="WriteIcon"
          quality={50}
        />
        <span className="font-semibold shrink-0">프로젝트 공유</span>
      </button>
      <Filter postCount={postCount} />
      {data.pages.map((group, i) => (
        <div
          className="relative flex flex-wrap justify-center mx-8"
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
