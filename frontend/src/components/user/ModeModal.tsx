'use client'

import { useRecoilValue } from 'recoil'
import Image from 'next/image'

import followerIcon from '../../../public/images/svg/followerIcon.svg'
import followingIcon from '../../../public/images/svg/followingIcon.svg'
import LikeIcon from '../../../public/images/svg/likeIcon.svg'
import { nicknameState } from '../../utils/atoms'

function mode(modenumber: number) {
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
  isFollowing: number
}

export default function ModeModal({
  onClickToggleModal,
  isFollowing,
}: ModeModalProps) {
  const nickname = useRecoilValue(nicknameState)

  return (
    <div>
      <div
        className="-translate-y-1/2-translate-y-1/2 fixed top-20 right-1/2 z-50 box-border h-660
    w-[410px] translate-x-1/2  transform rounded-[30px] 
    bg-white sm:w-630 sm:py-5"
      >
        <div className="flex justify-center font-lato text-[23px] font-semibold">
          <div className="text-graphyblue">{nickname}</div>
          {mode(isFollowing)}
        </div>

        {/* 구분선 */}
        <div className="my-3 w-auto border-b-2 border-b-neutral-200" />

        <div className="my-6 flex items-center justify-items-start px-10 font-lato">
          {modeImage(isFollowing)}
          <div className="mr-2 whitespace-nowrap  text-[21px]  font-semibold text-graphyblue">
            강민아
          </div>
          <div className=" text-left text-[17px] font-normal text-zinc-500">
            본인을 소개하는 한 마디 두 마디 세 마디
          </div>
        </div>
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
