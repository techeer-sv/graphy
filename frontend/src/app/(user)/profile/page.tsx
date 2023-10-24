'use client'

import { useEffect } from 'react'
import { useRouter } from 'next/navigation'

type GetProjectInfoResponse = {
  id: number
  content: string
  projectName: string
}

type Data = {
  followerCount: number
  followingCount: number
  introduction: string
  nickname: string
  getProjectInfoResponseList: GetProjectInfoResponse[]
}

type Response = {
  data?: Data
  message: string
  code: string
}

export default function MyPage() {
  const accessToken =
    typeof window !== 'undefined' ? sessionStorage.getItem('accessToken') : null
  const persistToken =
    typeof window !== 'undefined' ? localStorage.getItem('persistToken') : null

  const router = useRouter()

  async function getMyData() {
    const res = await fetch(
      `${process.env.NEXT_PUBLIC_BASE_URL}/members/mypage`,
      {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${accessToken || persistToken}`,
        },
      },
    )

    const resData: Response = await res.json()

    if (!res.ok) {
      throw new Error(resData.message)
    }

    if (resData.data !== undefined) {
      router.push(`/profile/${resData.data.nickname}`)
    }
  }

  useEffect(() => {
    getMyData()
  }, [])

  return <div />
}
