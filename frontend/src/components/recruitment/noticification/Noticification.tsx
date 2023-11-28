import { HiOutlineUserCircle } from 'react-icons/hi2'

type NotificationType = {
  item: {
    id: number
    type: string
    content: string
    read: boolean
  }
}

export default function RecruitmentCard({ item }: NotificationType) {
  return (
    <div className="border-b-[1px] border-gray-200 flex items-center w-[285px] h-[50px] text-lightgray text-[11px] py-2 px-4 bg-white border-solid">
      <div>
        <HiOutlineUserCircle strokeWidth="1" size="30" />
      </div>
      <span className="ml-2">{item.content}</span>
    </div>
  )
}
