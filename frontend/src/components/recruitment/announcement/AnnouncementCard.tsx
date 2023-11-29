import { AnnouncementDataType } from '@/utils/types'

type PropsType = {
  item: AnnouncementDataType
}

export default function AnnouncementCard({ item }: PropsType) {
  const extractMonthAndDay = (dateString: string): string => {
    if (dateString === '9999-12-31T00:00:00') {
      return '상시채용'
    }

    const date = new Date(dateString)

    const month = date.getMonth() + 1
    const day = date.getDate()

    return `~ ${month.toString().padStart(2, '0')}/${day
      .toString()
      .padStart(2, '0')} 까지`
  }

  return (
    <div className="w-[900px] flex h-28 items-center mb-2 rounded-2xl shadow-gray-300 shadow-sm">
      <div className="w-[180px] h-16 flex items-center border-r-2 border-gray-100 p-6">
        <span className="truncate text-[14px]">{item.companyName}</span>
      </div>
      <div className="flex flex-col w-[570px] p-6">
        <h1 className="truncate text-[15px] font-semibold mb-1">
          {item.title}
        </h1>
        <a
          target="_blank"
          href={item.url}
          rel="noreferrer"
          className="text-[13px] text-graphyblue underline "
        >
          채용공고 바로가기
        </a>
      </div>
      <div className="w-[150px] h-full flex justify-end items-end p-4">
        <span className="text-gray-400 text-[13px]">
          {extractMonthAndDay(item.expirationDate)}
        </span>
      </div>
    </div>
  )
}
