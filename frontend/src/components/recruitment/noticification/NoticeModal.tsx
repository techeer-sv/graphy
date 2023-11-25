import React, { PropsWithChildren, useEffect, useState } from 'react'
import Noticification from './Noticification'

type PropsType = {
  onClickToggleModal: () => void
}

type NotificationDataType = {
  id: number
  type: string
  content: string
  read: boolean
}

export default function NoticeModal({
  onClickToggleModal,
}: PropsWithChildren<PropsType>) {
  const accessToken =
    typeof window !== 'undefined' ? sessionStorage.getItem('accessToken') : null
  const persistToken =
    typeof window !== 'undefined' ? localStorage.getItem('persistToken') : null

  const [data, setData] = useState<NotificationDataType[]>([])

  const getData = async ({ pageParam = 1 }) => {
    const params = new URLSearchParams()
    params.set('page', String(pageParam))
    params.set('size', '12')
    params.set('direction', 'ASC')
    const res = await fetch(
      `${process.env.NEXT_PUBLIC_BASE_URL}/notifications?${params.toString()}`,
      {
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${accessToken || persistToken}`,
        },
      },
    )

    if (!res.ok) {
      const resData = await res.json()
      throw new Error(resData.message)
    }

    const resData = await res.json()
    setData(resData.data)
  }

  useEffect(() => {
    getData({ pageParam: 1 })
  }, [])

  const sortedData = [...data].sort((a, b) => Number(a.read) - Number(b.read))

  return (
    <div className="h-full w-full relative">
      <div className="overflow-y-auto fixed z-50 w-[300px] h-[350px] rounded-[15px] bg-white shadow-md top-[70px] right-0">
        {sortedData.map((item: NotificationDataType) => (
          <Noticification item={item} />
        ))}
      </div>
      <button
        aria-label="Toggle modal"
        className="fixed top-0 left-0 right-0 bottom-0 z-40 h-full w-screen"
        onClick={(e: React.MouseEvent) => {
          e.preventDefault()
          onClickToggleModal?.()
        }}
        type="button"
      />
    </div>
  )
}
