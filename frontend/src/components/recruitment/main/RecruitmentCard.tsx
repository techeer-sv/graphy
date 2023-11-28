import { HiOutlineUserCircle } from 'react-icons/hi2'
import { TfiComment } from 'react-icons/tfi'
import { PositionType } from '../../../utils/types'

type RecruitmentCardProps = {
  item: {
    id: number
    nickname: string
    title: string
    position: PositionType
    techTags: string[]
    recruiting: boolean
  }
}

export enum PositionColorClass {
  FRONTEND = 'bg-frontendtag',
  BACKEND = 'bg-backendtag',
  DESIGNER = 'bg-designertag',
  PM = 'bg-pmtag',
  DEVOPS = 'bg-devopstag',
  AI = 'bg-aitag',
}

export default function RecruitmentCard({ item }: RecruitmentCardProps) {
  return (
    <div className="flex h-[150px] w-[900px] flex-col justify-between pt-6 pb-4 px-12 bg-white border-solid border-b-[1px]">
      <div className="flex">
        <p className="text-[16px] font-semibold max-w-[600px] truncate">
          {item.title}
        </p>
        <div className="flex items-center ml-4 px-2 rounded-3xl border-2 border-solid border-recruitmentpink text-[11px] font-semibold text-recruitmentpink">
          {item.recruiting ? '모집중' : '마감'}
        </div>
        <div
          className={`flex items-center max-w-fit ml-4 px-2.5 rounded-3xl ${
            PositionColorClass[item.position]
          } text-white text-[11px]`}
        >
          {item.position}
        </div>
      </div>

      <div className="mb-2 mt-2 flex font-ng">
        {item.techTags.map((x: string) => (
          <div key={x}>
            <div className="flex items-center mr-2.5 py-1 px-2 rounded-md bg-gray-200 opacity-70 text-black text-[11px]">
              <span className="">{x}</span>
            </div>
          </div>
        ))}
      </div>

      <hr className="ml-0.5 border-gray-200" />
      <div className="flex justify-between text-lightgray text-[11px]">
        <div className="flex items-center">
          <HiOutlineUserCircle size="20" strokeWidth="1" />
          <span className="ml-2">{item.nickname}</span>
        </div>
        <div className="flex items-center">
          <TfiComment size="13" strokeWidth="0.1" />
          <span className="ml-2.5 mb-0.5">27</span>
        </div>
      </div>
    </div>
  )
}
