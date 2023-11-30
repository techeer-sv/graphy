'use client'

import AnnouncementCard from '@/components/recruitment/announcement/AnnouncementCard'
import { AnnouncementDataType } from '@/utils/types'
import { useInfiniteQuery } from '@tanstack/react-query'
import { useEffect } from 'react'

export default function Announcement() {
  const accessToken =
    typeof window !== 'undefined' ? sessionStorage.getItem('accessToken') : null
  const persistToken =
    typeof window !== 'undefined' ? localStorage.getItem('persistToken') : null

  const getData = async ({ pageParam = 1 }) => {
    const params = new URLSearchParams()
    params.set('page', String(pageParam))
    params.set('size', '12')
    params.set('direction', 'ASC')

    const res = await fetch(
      `${process.env.NEXT_PUBLIC_BASE_URL}/jobs?${params.toString()}`,
      {
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${accessToken || persistToken}`,
        },
      },
    )

    if (!res.ok) {
      const data = await res.json()
      throw new Error(data.message)
    }

    const data = await res.json()
    return data.data
  }

  const { data, fetchNextPage, hasNextPage, isFetchingNextPage, status } =
    useInfiniteQuery(['announcement'], getData, {
      getNextPageParam: (lastPage, pages) =>
        lastPage.length < 12 ? undefined : pages.length + 1,
    })

  useEffect(() => {
    if (!isFetchingNextPage) {
      const handleScroll = () => {
        if (
          window.innerHeight + document.documentElement.scrollTop !==
          document.documentElement.offsetHeight
        )
          return
        fetchNextPage()
      }
      window.addEventListener('scroll', handleScroll)
    }
  }, [fetchNextPage, isFetchingNextPage])

  if (status === 'loading') {
    return <span>Loading...</span>
  }

  if (status === 'error') {
    return <span>Error fetching data</span>
  }

  return (
    <div className="relative flex flex-col items-center w-screen h-auto min-h-screen pb-12 pt-24">
      <div className="w-[900px] mb-6 ml-2 border-b-[1px] pb-2">
        <span className="text-[14px]">신입 채용 공고</span>
      </div>
      {data.pages.map((group, i) => (
        <div
          className="relative flex flex-col justify-center"
          key={group[i]?.id}
        >
          {group.map((item: AnnouncementDataType) => (
            <div className="" key={item.id}>
              <AnnouncementCard item={item} />
            </div>
          ))}
        </div>
      ))}
      {hasNextPage && isFetchingNextPage && <span>Loading more...</span>}
    </div>
  )
}
