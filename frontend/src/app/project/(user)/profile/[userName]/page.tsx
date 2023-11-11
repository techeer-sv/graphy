'use client'

import { useEffect, useState, useCallback } from 'react'
import { useRouter } from 'next/navigation'
import Image from 'next/image'

import WritedPost from '../../../../../components/project/user/WritedPost'
import myProfile from '../../../../../../public/images/png/myProfile.png'
import WriteIcon from '../../../../../../public/images/svg/pencil-square.svg'
import FollowListModal from '../../../../../components/project/user/FollowListModal'

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

export default function ProfilePage({
  params,
}: {
  params: { userName: string }
}) {
  const [introduction, setIntroduction] = useState<string>('')
  const [followerCount, setFollowerCount] = useState<number>(0)
  const [followingCount, setFollowingCount] = useState<number>(0)
  const [getProjectInfoResponseList, setGetProjectInfoResponseList] = useState<
    GetProjectInfoResponse[]
  >([])
  const [isOpenModal, setOpenModal] = useState<boolean>(false)
  const [modeNumber, setModeNumber] = useState<number>(0)
  const [projectId, setProjectId] = useState<number | null>(null)

  const router = useRouter()

  const onClickToggleModal = useCallback(() => {
    setOpenModal(!isOpenModal)
  }, [isOpenModal])

  const onClickFollower = () => {
    setModeNumber(0)
    setProjectId(null)
    onClickToggleModal()
  }

  const onClickFollowing = () => {
    setModeNumber(1)
    setProjectId(null)
    onClickToggleModal()
  }

  const onClickLike = (project_id: number) => {
    setModeNumber(2)
    setProjectId(project_id)
    onClickToggleModal()
  }

  function toWrite() {
    router.push('/write')
  }

  async function getMyData() {
    const res = await fetch(
      `${process.env.NEXT_PUBLIC_BASE_URL}/members/mypage/${params.userName}`,
      {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        },
      },
    )

    const resData: Response = await res.json()

    if (!res.ok) {
      throw new Error(resData.message)
    }

    if (resData.data !== undefined) {
      setIntroduction(resData.data.introduction)
      setFollowerCount(resData.data.followerCount)
      setFollowingCount(resData.data.followingCount)
      setGetProjectInfoResponseList(resData.data.getProjectInfoResponseList)
    }
  }

  useEffect(() => {
    getMyData()
  }, [])

  return (
    <div className="relative h-auto min-h-screen w-screen bg-graphybg">
      <div className="w-screen px-8">
        <button
          className="fixed bottom-10 right-10 z-10 my-auto mb-2 flex shrink-0 flex-row items-center rounded-full
          bg-graphyblue px-4 py-1 pt-3 pb-3 font-semibold text-slate-50 drop-shadow-md
          sm:invisible"
          onClick={() => toWrite()}
          aria-label="toWritePage"
          type="button"
        >
          <Image className="mr-2 h-5 w-5" src={WriteIcon} alt="WriteIcon" />
          <span className="shrink-0 font-semibold">프로젝트 공유</span>
        </button>
        <div className="flex flex-col lg:flex-row">
          <div className="min-w-64 mx-auto mt-28 flex h-[220px] w-auto items-center justify-center rounded-[25px] bg-white px-7 lg:mt-36 lg:mr-8 lg:h-[320px] lg:flex-col lg:px-12">
            <Image
              className="mx-3 h-24 w-auto"
              src={myProfile}
              alt="myProfile"
            />
            <div>
              <div className="flex items-center lg:flex-col">
                <div className="mr-3 text-center font-lato text-[23px] font-semibold text-graphyblue lg:mx-auto lg:mt-3">
                  {params.userName}
                </div>

                <div className="flex flex-row text-center lg:mt-2">
                  <button
                    className="whitespace-nowrap font-lato text-[18px] font-semibold "
                    type="button"
                    onClick={onClickFollower}
                  >
                    팔로워 {followerCount}
                  </button>

                  <button
                    className="ml-6 mr-3 whitespace-nowrap font-lato text-[18px] font-semibold lg:mr-0"
                    type="button"
                    onClick={onClickFollowing}
                  >
                    팔로잉 {followingCount}
                  </button>
                  {isOpenModal ? (
                    <FollowListModal
                      onClickToggleModal={onClickToggleModal}
                      modeNumber={modeNumber}
                      userName={params.userName}
                      projectId={projectId}
                    />
                  ) : null}
                </div>
              </div>
              <div className="mt-4 whitespace-nowrap text-center font-lato text-[15px] text-stone-500">
                {introduction}
              </div>
            </div>
          </div>
          <div className="mx-auto">
            <div className="mt-10 font-lato text-[24px] font-bold lg:mt-32">
              나의 작성 포스트
            </div>
            <div className="mt-3 border-b-2 border-b-neutral-200 " />
            {getProjectInfoResponseList.map((project) => (
              <WritedPost
                key={project.id}
                getProjectInfoResponse={project}
                onClickLike={() => onClickLike(project.id)}
              />
            ))}
          </div>
        </div>
      </div>
    </div>
  )
}
