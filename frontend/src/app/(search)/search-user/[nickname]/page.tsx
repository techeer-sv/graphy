'use client'

import { useEffect, useState } from 'react'
import { useRouter } from 'next/navigation'
import { v4 as uuidv4 } from 'uuid'
import Image from 'next/image'

import WriteIcon from '../../../../../public/images/svg/pencil-square.svg'
import ProfileIcon from '../../../../../public/images/svg/profileIcon.svg'
// TODO: import NavBar from '../../../components/general/NavBar'
import Banner from '../../../../components/main/Banner'

type DataObject = {
  nickname: string
  email: string
}

type ParamsType = {
  params: {
    nickname: string
  }
}

export default function SearchUserPage({ params }: ParamsType) {
  const [data, setData] = useState<DataObject[]>([]) // 데이터를 담을 state 선언
  const [hoveredEmail, setHoveredEmail] = useState('')
  const router = useRouter()
  const accessToken =
    typeof window !== 'undefined' ? sessionStorage.getItem('accessToken') : null
  const persistToken =
    typeof window !== 'undefined' ? localStorage.getItem('persistToken') : null

  function handleMouseEnter(email: string) {
    setHoveredEmail(email)
  }

  function handleMouseLeave() {
    setHoveredEmail('')
  }

  function toWrite() {
    router.push('/write')
  }

  async function getData() {
    const res = await fetch(
      `${process.env.NEXT_PUBLIC_BASE_URL}/members?nickname=${params.nickname}`,
      {
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${accessToken || persistToken}`,
        },
      },
    )

    const resData = await res.json()

    if (resData.data.length === 0) {
      setData([{ nickname: '검색 결과가 없습니다.', email: '' }])
    } else {
      setData(resData.data)
    }

    if (!res.ok) {
      alert('검색 결과가 없습니다.')
      router.push('/')
      throw new Error('검색 결과가 없습니다.')
    }
  }

  useEffect(() => {
    getData()
  }, [params])

  return (
    <div className="relative h-auto min-h-screen w-screen bg-gray-50">
      {/* TODO: <NavBar /> */}
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

        <div className="mx-10 border-b-2 border-b-neutral-300 pt-0 font-ng-b text-2xl sm:mx-28 sm:mb-5 sm:pt-5">
          {/* All */}
        </div>
        <div>
          <div className="ml-0 flex flex-wrap justify-center min-[680px]:ml-10 min-[680px]:justify-start">
            {data.map((item) => (
              <div key={uuidv4()}>
                {item.nickname === '검색 결과가 없습니다.' ? (
                  <div className="mx-3 mt-9 flex min-[680px]:mx-0 min-[680px]:ml-16 ">
                    <span>{item.nickname}</span>
                  </div>
                ) : (
                  <div
                    className="mx-3 mt-9 flex min-[680px]:mx-0 min-[680px]:ml-16 "
                    onMouseEnter={() => handleMouseEnter(item.email)}
                    onMouseLeave={handleMouseLeave}
                  >
                    <Image
                      className="mr-2 h-6 w-6"
                      src={ProfileIcon}
                      alt="ProfileIcon"
                    />
                    <span className="relative">
                      {item.nickname}
                      {hoveredEmail === item.email && (
                        <span className="absolute -top-5 -left-0.5 bg-gray-200 px-1 text-xs">
                          {item.email}
                        </span>
                      )}
                    </span>
                  </div>
                )}
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  )
}
