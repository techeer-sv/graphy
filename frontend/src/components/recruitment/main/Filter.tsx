import ToggleButton from './ToggleButton'

export type FilterPropsType = {
  postCount: number
}

export default function Filter({ postCount }: FilterPropsType) {
  return (
    <div className="flex items-center h-[30px] w-[900px] px-4 justify-between text-[13px] pb-4 border-b-[1px]">
      <div className="flex">
        <span className="pr-3 border-r-2">게시글 {postCount}개</span>
        <div className="ml-3">최신 순</div>
      </div>
      <div className="flex items-center">
        <span className="mr-2">모집 중인 프로젝트만 보기</span>
        <ToggleButton />
      </div>
    </div>
  )
}
