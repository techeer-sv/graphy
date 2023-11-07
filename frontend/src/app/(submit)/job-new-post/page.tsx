import Image from 'next/image'

import bookMark from '../../../../public/images/svg/bookmark.svg'
import userIcon from '../../../../public/images/svg/userIcon.svg'
import AllStacks from '../../../utils/stacks'

export default function JobNewPost() {
  const items = {
    techTags: ['React', 'Spring'],
  }

  const positions = [
    { name: 'BACKEND', color: '#152A80' },
    { name: 'FRONTEND', color: '#FF5391' },
    { name: 'DESIGNER', color: '#FF9900' },
  ]

  function findImage(tag: string) {
    return AllStacks.map((x) => x.image)[
      AllStacks.map((x) => x.name).findIndex((x) => x === tag)
    ]
  }

  return (
    <div className="h-auto min-h-screen w-screen bg-graphybg pt-24 pb-10 px-8 lg:flex">
      <div className="bg-white lg:w-[70%] min:h-[700px] py-6 px-10">
        <div className="flex items-center">
          <span className="text-xl font-ng-b mr-10 overflow-hidden whitespace-nowrap text-ellipsis">
            토이 프로젝트 공유 서비스 (WEB/APP) 팀원 모집
          </span>
          <span className=" whitespace-nowrap w-auto bg-graphyblue text-white rounded-lg h-6 py-1 px-2 mr-2 text-xs font-ng-b">
            모집중
          </span>
        </div>
        <div className="flex mt-2 h-8 items-center">
          <Image
            className="w-8 h-6 pr-2 border-r-2"
            src={userIcon}
            alt="유저 아이콘"
          />
          <span className="ml-3 mr-8 text-graphyblue ">Yukeon</span>
          <span className="text-[#6E7277]">2023-09-30</span>
        </div>
        <div className="h-auto flex flex-col mt-5 pb-5 border-b-2 font-lef text-sm">
          <div className="flex  whitespace-nowrap items-center mb-5">
            <span className="w-32 text-[#6E7277]">모집 포지션</span>
            {positions.map((x) => (
              <div
                style={{ backgroundColor: x.color }}
                className=" rounded-md px-3 mr-2 py-1 shadow-2xs"
                key={x.name}
              >
                <span className=" text-xs text-white">{x.name}</span>
              </div>
            ))}
          </div>
          <div className="flex mb-6">
            <span className="w-32 text-[#6E7277]">모집 인원</span>
            <span>5명</span>
          </div>
          <div className="flex mb-6">
            <span className="w-32 text-[#6E7277]">진행 기간</span>
            <span>4개월</span>
          </div>
          <div className="flex mb-5">
            <span className="w-32 text-[#6E7277]">진행 방식</span>
            <span>온라인</span>
          </div>
          <div className="flex items-center">
            <span className="w-32 text-[#6E7277]">기술 스택</span>
            <div className="mr-3 flex flex-row font-ng">
              {items.techTags.map((x: string) => (
                <div
                  className="flex justify-center w-auto items-center px-2 py-1 mr-2 border-graphyblue shadow-2xs rounded-md"
                  key={x}
                >
                  <Image
                    className="h-5 w-5 mr-2"
                    src={findImage(x)}
                    alt="stack"
                    quality={20}
                  />
                  <span className="">{x}</span>
                </div>
              ))}
            </div>
          </div>
        </div>
        <div className="mt-4">
          <span className="text-lg font-ng-b">프로젝트 소개</span>
        </div>
      </div>
      <div className=" lg:w-[32%] h-auto lg:ml-10 lg:mt-0 mt-10">
        <div className="bg-white w-auto py-6 px-10 flex flex-col">
          <span className="text-2xl font-ng-b mb-3">00님,</span>
          <span>
            해당 프로젝트가 마음에 드시나요?
            <br />
            프로젝트 참여를 요청해 보세요
          </span>
          <button
            className="w-full h-10 bg-graphyblue text-white rounded-2xl mt-10 mb-4"
            type="button"
          >
            참여요청하기
          </button>
        </div>
        <div className="mt-10">
          <div className="font-ng-b">
            <span>유사한 </span>
            <span className=" text-graphyblue">추천 프로젝트</span>
          </div>
          <div className="bg-white py-6 pl-8 pr-6 mt-4">
            <div className="flex justify-between">
              <span className="font-ng-b">코딩 테스트 문제 관리 서비스</span>
              <Image className=" w-6 h-6" src={bookMark} alt="북마크" />
            </div>
            <div className="flex text-sm mt-4 overflow-hidden items-center  ">
              <span className="mr-4 text-[#6E7277] whitespace-nowrap">
                모집 포지션
              </span>
              {positions.map((x) => (
                <div
                  style={{ backgroundColor: x.color }}
                  className="rounded-md px-2 mr-1 py-[2px]"
                  key={x.name}
                >
                  <span className="whitespace-nowrap text-white text-[8px]">
                    {x.name}
                  </span>
                </div>
              ))}
            </div>
            <div className="flex text-sm mt-2">
              <span className="mr-4 text-[#6E7277]">모집 마감일</span>
              <span>2023-09-30</span>
            </div>
            <div className="mr-3 mt-4 flex">
              {items.techTags.map((x: string) => (
                <div key={x}>
                  <Image
                    className="mr-2 h-6 w-6"
                    src={findImage(x)}
                    alt="stack"
                    quality={20}
                  />
                </div>
              ))}
            </div>
          </div>
          <div className="bg-white py-6 pl-8 pr-6 mt-4">
            <div className="flex justify-between">
              <span className="font-ng-b">코딩 테스트 문제 관리 서비스</span>
              <Image className=" w-6 h-6" src={bookMark} alt="북마크" />
            </div>
            <div className="flex text-sm mt-4 overflow-hidden items-center">
              <span className="mr-4 text-[#6E7277] whitespace-nowrap">
                모집 포지션
              </span>
              {positions.map((x) => (
                <div
                  style={{ backgroundColor: x.color }}
                  className=" rounded-md px-2 mr-1 py-[2px]"
                  key={x.name}
                >
                  <span className="whitespace-nowrap text-white text-[8px]">
                    {x.name}
                  </span>
                </div>
              ))}
            </div>
            <div className="flex text-sm mt-2">
              <span className="mr-4 text-[#6E7277]">모집 마감일</span>
              <span>2023-09-30</span>
            </div>
            <div className="mr-3 mt-4 flex">
              {items.techTags.map((x: string) => (
                <div key={x}>
                  <Image
                    className="mr-2 h-6 w-6"
                    src={findImage(x)}
                    alt="stack"
                    quality={20}
                  />
                </div>
              ))}
            </div>
          </div>
          <div className="bg-white py-6 pl-8 pr-6 mt-4">
            <div className="flex justify-between">
              <span className="font-ng-b">코딩 테스트 문제 관리 서비스</span>
              <Image className=" w-6 h-6" src={bookMark} alt="북마크" />
            </div>
            <div className="flex text-sm mt-4 overflow-hidden items-center">
              <span className="mr-4 text-[#6E7277] whitespace-nowrap">
                모집 포지션
              </span>
              {positions.map((x) => (
                <div
                  style={{ backgroundColor: x.color }}
                  className=" rounded-md px-2 mr-1 py-[2px]"
                  key={x.name}
                >
                  <span className="whitespace-nowrap text-white text-[8px]">
                    {x.name}
                  </span>
                </div>
              ))}
            </div>
            <div className="flex text-sm mt-2">
              <span className="mr-4 text-[#6E7277]">모집 마감일</span>
              <span>2023-09-30</span>
            </div>
            <div className="mr-3 mt-4 flex">
              {items.techTags.map((x: string) => (
                <div key={x}>
                  <Image
                    className="mr-2 h-6 w-6"
                    src={findImage(x)}
                    alt="stack"
                    quality={20}
                  />
                </div>
              ))}
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}
