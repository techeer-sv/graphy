'use client'

import Image from 'next/image'
import { useEffect, useState } from 'react'

import followerIcon from '../../../../public/images/svg/followerIcon.svg'
import followingIcon from '../../../../public/images/svg/followingIcon.svg'
import LikeIcon from '../../../../public/images/svg/likeIcon.svg'

function modeText(modenumber: number) {
  if (modenumber === 0) {
    return <div>의 팔로워</div>
  }
  if (modenumber === 1) {
    return <div>의 팔로잉</div>
  }
  return <div>의 포스트에 반응한 사람</div>
}

function modeImage(modenumber: number) {
  if (modenumber === 0) {
    return (
      <Image className="mr-4 w-14" src={followingIcon} alt="followerIcon" />
    )
  }
  if (modenumber === 1) {
    return <Image className="mr-4 w-14" src={followerIcon} alt="followerIcon" />
  }
  return <Image className="mr-4 w-14" src={LikeIcon} alt="LikeIcon" />
}

type ModeModalProps = {
  onClickToggleModal: () => void
  modeNumber: number
  userName: string
  projectId: number | null
}

export default function FollowListModal({
  onClickToggleModal,
  modeNumber,
  userName,
  projectId,
}: ModeModalProps) {
  const accessToken =
    typeof window !== 'undefined' ? sessionStorage.getItem('accessToken') : null
  const persistToken =
    typeof window !== 'undefined' ? localStorage.getItem('persistToken') : null
  const [userList, setUserList] = useState<{ id: number; nickname: string }[]>(
    [],
  )

  async function modeList(modenumber: number) {
    if (modenumber === 0) {
      const res = await fetch(
        `${process.env.NEXT_PUBLIC_BASE_URL}/follow/following`,
        {
          headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${accessToken || persistToken}`,
          },
        },
      )

      const resData = await res.json()

      if (!res.ok) {
        throw new Error(resData.message)
      }

      setUserList(resData.data)
    } else if (modenumber === 1) {
      const res = await fetch(
        `${process.env.NEXT_PUBLIC_BASE_URL}/follow/follower`,
        {
          headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${accessToken || persistToken}`,
          },
        },
      )

      const resData = await res.json()

      if (!res.ok) {
        throw new Error(resData.message)
      }

      setUserList(resData.data)
    } else {
      const res = await fetch(
        `${process.env.NEXT_PUBLIC_BASE_URL}/likes/${projectId}`,
        {
          headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${accessToken || persistToken}`,
          },
        },
      )

      const resData = await res.json()

      if (!res.ok) {
        throw new Error(resData.message)
      }

      setUserList(resData.data)
    }
  }

  useEffect(() => {
    modeList(modeNumber)
  }, [modeNumber])

  return (
    <div>
      <div
        className="-translate-y-1/2-translate-y-1/2 fixed top-20 right-1/2 z-50 box-border h-660
    w-[410px] translate-x-1/2  transform rounded-[30px] 
    bg-white sm:w-630 sm:py-5"
      >
        <div className="flex justify-center font-lato text-[23px] font-semibold">
          <div className="text-graphyblue">{userName}</div>
          {modeText(modeNumber)}
        </div>

        {/* 구분선 */}
        <div className="my-3 w-auto border-b-2 border-b-neutral-200" />

        {userList.map((user) => (
          <div
            className="my-6 flex items-center justify-items-start px-10 font-lato"
            key={user.id}
          >
            {modeImage(modeNumber)}
            <div className="mr-2 whitespace-nowrap text-[21px] font-semibold text-graphyblue">
              {user.nickname}
            </div>
            <div className=" text-left text-[17px] font-normal text-zinc-500">
              본인을 소개하는 한 마디 두 마디 세 마디
            </div>
          </div>
        ))}
      </div>

      {/* 모달 배경 버튼 */}
      <button
        aria-label="Toggle modal"
        className="fixed top-0 left-0 right-0 bottom-0 z-40 h-full w-screen  bg-black opacity-70"
        onClick={(e: React.MouseEvent) => {
          e.preventDefault()

          if (onClickToggleModal) {
            onClickToggleModal()
          }
        }}
        type="button"
      />
    </div>
  )
}
