import { AnnouncementDataType } from '@/utils/types'

type PropsType = {
  item: AnnouncementDataType
}

export default function AnnouncementCard({ item }: PropsType) {
  const extractMonthAndDay = (dateString: string): string => {
    // ISO 형식의 문자열을 Date 객체로 변환
    const date = new Date(dateString)

    // 월과 일을 추출 (월은 0부터 시작하므로 1을 더함)
    const month = date.getMonth() + 1
    const day = date.getDate()

    // 'MM-DD' 형식의 문자열로 반환
    return `${month.toString().padStart(2, '0')}/${day
      .toString()
      .padStart(2, '0')}`
  }

  return (
    <div className="w-[860px] flex h-24 items-center border-b-2">
      <div className="w-40 h-full flex items-center border-r-2 p-4">
        <span className="truncate">{item.companyName}</span>
      </div>
      <div className="flex flex-col w-[580px] p-4">
        <h1 className="truncate text-[17px] font-semibold mb-1">
          {item.title}
        </h1>
        <a
          target="_blank"
          href={item.url}
          rel="noreferrer"
          className="text-[14px] text-graphyblue underline "
        >
          채용공고 바로가기
        </a>
      </div>
      <div className="w-[120px] h-full flex justify-end items-end p-2">
        <span className="text-gray-400 text-[13px]">
          ~{extractMonthAndDay(item.expirationDate)}
        </span>
      </div>
    </div>
  )
}
