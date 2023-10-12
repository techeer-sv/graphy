import Image from 'next/image'
import { useRouter } from 'next/navigation'

import Like from '../../../public/images/svg/Like.svg'

type GetProjectInfoResponse = {
  id: number
  description: string
  projectName: string
}

type WritedPostProps = {
  getProjectInfoResponse: GetProjectInfoResponse
  onClickLike: () => void
}

export default function WritedPost({
  getProjectInfoResponse,
  onClickLike,
}: WritedPostProps) {
  const router = useRouter()
  return (
    <div className="relative mt-8 ml-8 h-[200px] w-auto rounded-[25px] bg-white pt-6 drop-shadow-md">
      {/* 좋아요 */}
      <button
        className="absolute top-6 right-10 z-10 flex items-center justify-center"
        type="button"
        onClick={onClickLike}
      >
        <Image src={Like} alt="Like" />
        14
      </button>
      <button
        className="flex flex-col "
        type="button"
        onClick={() => router.push(`/post/${getProjectInfoResponse.id}`)}
      >
        {/* 제목 */}
        <div className="ml-10 font-lato text-[20px] font-bold text-zinc-700">
          {getProjectInfoResponse.projectName}
        </div>
        {/* 본문 미리보기 */}
        <div className="mx-12 mt-3 h-[100px] max-w-[750px] overflow-hidden text-ellipsis font-lato text-[16px] font-normal text-zinc-700 ">
          {getProjectInfoResponse.description}
        </div>
        <div className="absolute left-12 bottom-7 m-auto h-12 w-full max-w-[750px] whitespace-nowrap bg-gradient-to-b from-transparent to-white" />
      </button>
    </div>
  )
}
