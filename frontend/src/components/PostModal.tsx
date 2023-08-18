import React from 'react';

// import closeIcon from '../assets/image/closeIcon.svg';

interface Props {
  onClickPostToggleModal: () => void;
  // isPost: boolean;
}

function PostModal({ onClickPostToggleModal }: Props) {
  return (
    <div>
      <div
        className="-translate-y-1/2-translate-y-1/2 fixed top-20 right-1/2 z-50 box-border h-660
    w-[410px] translate-x-1/2  transform rounded-[30px] 
    bg-white sm:w-630 sm:py-5"
      >
        {/* {isPost} */}
        <div className="flex justify-center font-lato text-[23px] font-semibold">
          <div className="text-graphyblue">제목</div>
          {/* <img className="" src={closeIcon} alt="closeIcon" /> */}
        </div>

        <div className="my-6 flex items-center justify-items-start px-10 font-lato text-[18px]">
          개발자를 준비하다 보면, 좋은 프로젝트에 대한 고민이 많아진다. 하지만
          처음부터 좋은 프로젝트를 개발하는 것은 어렵기에 대부분 프로젝트
          레퍼런스를 참고하거나, 주변에 평가를 받아 개선하기도 한다. Graphy는
          이를 도와줄 수 있는 프로젝트 공유 플랫폼이다. 개발자로 취업 준비 중인
          사용자를 타겟팅한 포트폴리오 기록 사이트로, 사람들이 보다 좋은
          프로젝트를 개발할 수 있도록 도와주는 것을 목표로 하고 있다. 개발자를
          준비하다 보면, 좋은 프로젝트에 대한 고민이 많아진다. 하지만 처음부터
          좋은 프로젝트를 개발하는 것은 어렵기에 대부분 프로젝트 레퍼런스를
          참고하거나, 주변에 평가를 받아 개선하기도 한다. Graphy는 이를 도와줄
          수 있는 프로젝트 공유 플랫폼이다. 개발자로 취업 준비 중인 사용자를
          타겟팅한 포트폴리오 기록 사이트로, 사람들이 보다 좋은 프로젝트를
          개발할 수 있도록 도와주는 것을 목표로 하고 있다.
        </div>
      </div>

      {/* 모달 영역 외의 배경을 클릭하면 모달이 닫히게 하는 컨테이너, 이벤트핸들러를 사용하여 클릭 이벤트 발생 시 onClickToggleModal 함수 호출하여 모달 닫음 */}
      <button
        aria-label="Toggle modal"
        className="fixed top-0 left-0 right-0 bottom-0 z-40 h-full w-screen  bg-black opacity-70"
        onClick={(e: React.MouseEvent) => {
          e.preventDefault();

          if (onClickPostToggleModal) {
            onClickPostToggleModal();
          }
        }}
        type="button"
      />
    </div>
  );
}

export default PostModal;
