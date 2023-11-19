import { HiOutlineUserCircle } from 'react-icons/hi2'
import { TfiComment } from 'react-icons/tfi'

type RecruitmentCardProps = {
  item: {
    id: number
    nickname: string
    title: string
    position: PositionType
    techTags: string[]
  }
  index: number
}

export enum PositionType {
  FRONTEND = 'FRONTEND',
  BACKEND = 'BACKEND',
  DESIGNER = 'DESIGNER',
  PM = 'PM',
  DEVOPS = 'DEVOPS',
  AI = 'AI',
}

export enum PositionColorClass {
  FRONTEND = 'bg-frontendtag',
  BACKEND = 'bg-backendtag',
  DESIGNER = 'bg-designertag',
  PM = 'bg-pmtag',
  DEVOPS = 'bg-devopstag',
  AI = 'bg-aitag',
}

export default function RecruitmentCard({ item, index }: RecruitmentCardProps) {
  return (
    <div className="flex h-[270px] w-[1100px] flex-col justify-between rounded-xl pt-12 pb-4 px-16 bg-white drop-shadow-sm mb-12">
      <div className="flex">
        <p className="text-[23px]">{item.title}</p>
        <div className="flex items-center max-w-fit ml-4 py-1.5 px-3.5 rounded-3xl border-2 border-solid border-recruitmentpink text-[15px] font-semibold text-recruitmentpink">
          모집 중
        </div>
        <div
          className={`flex items-center max-w-fit ml-4 py-1 px-3 rounded-3xl ${
            PositionColorClass[item.position]
          } text-white text-[15px] font-semibold`}
        >
          {item.position}
        </div>
      </div>

      <div className="mb-2 mt-2 flex font-ng">
        {item.techTags.map((x: string) => (
          <div key={x}>
            <div className="flex items-center max-w-fit mr-2.5 py-1.5 px-3.5 rounded-md bg-gray-200 opacity-70 text-black text-[15px]">
              <span className="">{x}</span>
            </div>
          </div>
        ))}
      </div>

      <hr className="ml-0.5 border-gray-200" />
      <div className="flex justify-between text-lightgray">
        <div className="flex items-center">
          <HiOutlineUserCircle size="34" strokeWidth="1" />
          <span className="ml-2">{item.nickname}</span>
        </div>
        <div className="flex items-center">
          <TfiComment size="22" strokeWidth="0.1" />
          <span className="ml-2.5 mb-0.5">27</span>
        </div>
      </div>
    </div>
  )
}
