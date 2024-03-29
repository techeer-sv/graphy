import SortToggleButton from './SortToggleButton'
import ToggleButton from './ToggleButton'

export type FilterPropsType = {
  postCount: number
}

export default function Filter({ postCount }: FilterPropsType) {
  return (
    <div className="flex items-center h-[30px] w-[900px] px-4 justify-between text-[13px] pb-4 border-b-[1px]">
      <div className="flex">
        <span className="w-[73px] border-r-2 mr-3">게시글 {postCount}개</span>
        <SortToggleButton />
      </div>
      <div className="flex items-center">
        <span className="mr-2">모집 중인 프로젝트만 보기</span>
        <ToggleButton />
      </div>
    </div>
  )
}
