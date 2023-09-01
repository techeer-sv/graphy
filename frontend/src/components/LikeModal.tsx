import React from 'react';

type LikeModalProps = {
  onClickLikeToggleModal: () => void;
  isLike: boolean;
};

function likeModal({ onClickLikeToggleModal, isLike }: LikeModalProps) {
  return (
    <div>
      <div
        className="-translate-y-1/2-translate-y-1/2 fixed top-20 right-1/2 z-50 box-border h-660
    w-[410px] translate-x-1/2  transform rounded-[30px] 
    bg-white sm:w-630 sm:py-5"
      >
        {isLike}
      </div>

      {/* 모달 배경 */}
      <button
        aria-label="Toggle modal"
        className="fixed top-0 left-0 right-0 bottom-0 z-40 h-full w-screen  bg-black opacity-70"
        onClick={(e: React.MouseEvent) => {
          e.preventDefault();

          if (onClickLikeToggleModal) {
            onClickLikeToggleModal();
          }
        }}
        type="button"
      />
    </div>
  );
}

export default likeModal;
