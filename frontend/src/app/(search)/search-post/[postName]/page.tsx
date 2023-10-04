'use client'

import { useEffect, useState } from 'react'
import { useRouter } from 'next/navigation'

import WriteIcon from '../../../../../public/images/svg/pencil-square.svg'
// TODO: import NavBar from '../../../components/general/NavBar'
import Banner from '../../../../components/main/Banner'
import ProjectCard from '../../../../components/main/ProjectCard'

type DataObject = {
  id: number
  createdAt: string
  projectName: string
  description: string
  techTags: string[]
  thumbNail: string
}

export type ParamsType = {
  params: {
    postName: string
  }
}

export default function SearchProjectPage({ params }: ParamsType) {
  const [data, setData] = useState<DataObject[]>([]) // 데이터를 담을 state 선언
  const router = useRouter() // react-router-dom useNavigate 사용 선언

  function toWrite() {
    router.push('/write')
  }

  async function getData() {
    const res = await fetch(
      `${process.env.NEXT_PUBLIC_BASE_URL}/projects?${params.postName}`,
      {
        headers: {
          'Content-Type': 'application/json',
        },
      },
    )

    if (!res.ok) {
      alert('검색 결과가 없습니다.')
      router.push('/')
      throw new Error('프로젝트 검색에 실패했습니다.')
    }

    const resData = await res.json()

    setData(resData.data)
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
          <img className="mr-2 h-5 w-5" src={WriteIcon} alt="WriteIcon" />
          <span className="shrink-0 font-semibold">프로젝트 공유</span>
        </button>

        <div className="mx-10 border-b-2 border-b-neutral-300 pt-0 font-ng-b text-2xl sm:mx-28 sm:mb-5 sm:pt-5">
          {/* All */}
        </div>
        <div>
          <div className="ml-0 flex flex-wrap justify-center min-[680px]:ml-10 min-[680px]:justify-start">
            {data.map((item, i) => (
              <div
                className="mx-3 mt-9 min-[680px]:mx-0 min-[680px]:ml-16 "
                key={item.id}
              >
                <ProjectCard items={item} index={i} />
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  )
}
