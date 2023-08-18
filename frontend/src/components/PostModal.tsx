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

        <div className="my-6 flex items-center justify-items-start px-10 font-lato">
          sodyd
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
