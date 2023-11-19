'use client'

import { useInfiniteQuery } from '@tanstack/react-query'
import { useEffect, useState } from 'react'
import { useRecoilValue } from 'recoil'
import RecruitmentCard, {
  PositionType,
} from '../../components/recruitment/RecruitmentCard'
import RecruitmentFilter, {
  FilterType,
} from '../../components/recruitment/RecruitmentFilter'
import { selectedPositionsState, selectedSkillsState } from '../../utils/atoms'

export type RecruitmentDataType = {
  id: number
  nickname: string
  title: string
  position: PositionType
  techTags: string[]
}

export default function Recruitment() {
  // const [data, setData] = useState<RecruitmentDataType[]>([]) // 여기를 수정했습니다.
  const [filter, setFilter] = useState<FilterType>({
    position: '',
    skill: '',
    searchQuery: '',
  })

  const selectedPositions = useRecoilValue(selectedPositionsState)
  const selectedSkills = useRecoilValue(selectedSkillsState)

  const getData = async ({ pageParam = 1 }) => {
    const params = new URLSearchParams()
    params.set('page', String(pageParam))
    params.set('size', '12')

    selectedPositions.forEach((position) => {
      params.append('positions', position)
    })

    selectedSkills.forEach((skill) => {
      params.append('tags', skill)
    })

    if (filter.searchQuery !== '') {
      params.set('keyword', filter.searchQuery)
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
      throw new Error('채용공고 검색에 실패했습니다.')
    }

    const data = await res.json()

    return data.data
  }

  const { data, fetchNextPage, hasNextPage, isFetchingNextPage, status } =
    useInfiniteQuery(
      ['recruitments', selectedPositions, selectedSkills],
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
  }, [fetchNextPage, isFetchingNextPage, selectedPositions, selectedSkills])

  if (status === 'loading') {
    return <span>Loading...</span>
  }

  if (status === 'error') {
    return <span>Error fetching data</span>
  }

  return (
    <div className="relative h-auto min-h-screen w-screen pt-32 flex flex-col items-center bg-recruitmentbg">
      <RecruitmentFilter />
      <div className="recruitment-cards-container">
        {data.pages.map((group, i) => (
          <div
            className="relative mx-8 flex flex-wrap justify-center pt-6 sm:pt-8"
            key={group[i]?.id}
          >
            {group.map((item: RecruitmentDataType) => (
              <div className="mx-8 mb-10" key={item.id}>
                <RecruitmentCard item={item} index={i} />
              </div>
            ))}
          </div>
        ))}
        {hasNextPage && isFetchingNextPage && <span>Loading more...</span>}
      </div>
    </div>
  )
}
