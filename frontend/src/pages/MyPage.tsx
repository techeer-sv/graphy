import { useNavigate } from 'react-router-dom';

import graphyIcon from '../assets/image/graphyIcon.png';
import Like from '../assets/image/Like.svg';
import myProfile from '../assets/image/myProfile.png';
import WriteIcon from '../assets/image/pencil-square.svg';
import NavBar from '../components/NavBar';

// import ProjectCard from '../components/ProjectCard';

function MyPage() {
  const navigate = useNavigate(); // react-router-dom useNavigate 사용 선언

  function toWrite() {
    // react-router-dom을 이용한 글쓰기 페이지로 이동 함수
    navigate('/write');
  }

  return (
    <div className="relative h-auto min-h-screen w-screen bg-graphybg">
      <NavBar />
      {/* 프로젝트 공유 버튼 */}
      <button
        className="fixed bottom-10 right-10 z-10 my-auto mb-2 flex shrink-0 flex-row items-center rounded-full
          bg-graphyblue px-4 py-1 pt-3 pb-3 font-semibold text-slate-50 drop-shadow-md
          sm:invisible"
        onClick={() => toWrite()}
        aria-label="toWritePage"
        type="button"
      >
        <img className="mr-2 h-5 w-5" src={WriteIcon} alt="WriteIcon" />
        <span className="shrink-0 font-semibold">프로젝트 공유</span>
      </button>

      <div className="flex">
        <div className="z-40 ml-28 mt-36 h-[320px] w-64 rounded-[25px] bg-white ">
          <div className="items-center justify-center">
            {/* 프로필카드 아이콘 */}
            <img
              className="mt-12 ml-20 h-24 "
              src={myProfile}
              alt="myProfile"
            />
            <div className="mt-3 text-center font-lato text-[23px] font-semibold text-graphyblue">
              닉네임
            </div>
            <div className="text-center">
              <button
                className="mt-2 font-lato text-[18px] font-semibold"
                type="button"
              >
                팔로우 46
              </button>
              <button
                className="ml-6 font-lato text-[18px] font-semibold"
                type="button"
              >
                팔로잉 34
              </button>
            </div>
            <div className="mt-4 text-center font-lato text-[15px] text-stone-500">
              본인을 소개하는 한 마디
            </div>
          </div>
        </div>

        {/* 배경 아이콘 */}
        <img
          className="fixed bottom-0 z-30"
          src={graphyIcon}
          alt="graphyIcon"
        />

        {/* ----------------------------- */}
        <div className="ml-16 flex-col">
          <div className="mt-32 font-lato text-[24px] font-bold">
            나의 작성 포스트
          </div>

          {/* 구분선 */}
          <div className="mt-3 border-b-2 border-b-neutral-200 " />

          {/* 내가 쓴 글 리스트 */}
          <div className="mt-8 ml-8 h-[230px] w-[850px] rounded-[25px] bg-white drop-shadow-md">
            {/* 제목 */}
            <div className="ml-7 font-lato text-[20px] font-semibold text-zinc-700">
              Graphy, 프로젝트를 기록하다
            </div>
            {/* 본문 미리보기 */}
            <div className="ml-12 mt-3  h-[100px] w-[750px] overflow-y-auto text-ellipsis font-lato text-[16px] font-normal text-zinc-700">
              개발자를 준비하다 보면, 좋은 프로젝트에 대한 고민이 많아진다.
              하지만 처음부터 좋은 프로젝트를 개발하는 것은 어렵기에 대부분
              프로젝트 레퍼런스를 참고하거나, 주변에 평가를 받아 개선하기도
              한다. Graphy는 이를 도와줄 수 있는 프로젝트 공유 플랫폼이다.
              개발자로 취업 준비 중인 사용자를 타겟팅한 포트폴리오 기록
              사이트로, 사람들이 보다 좋은 프로젝트를 개발할 수 있도록 도와주는
              것을 목표로 하고 있다. 개발자를 준비하다 보면, 좋은 프로젝트에
              대한 고민이 많아진다. 하지만 처음부터 좋은 프로젝트를 개발하는
              것은 어렵기에 대부분 프로젝트 레퍼런스를 참고하거나, 주변에 평가를
              받아 개선하기도 한다. Graphy는 이를 도와줄 수 있는 프로젝트 공유
              플랫폼이다. 개발자로 취업 준비 중인 사용자를 타겟팅한 포트폴리오
              기록 사이트로, 사람들이 보다 좋은 프로젝트를 개발할 수 있도록
              도와주는 것을 목표로 하고 있다.
            </div>
            {/* 좋아요 */}
            <button className="ml-[780px] mt-4 flex items-center" type="button">
              <img src={Like} alt="Like" />
              14
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default MyPage;
