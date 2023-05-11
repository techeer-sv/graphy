import React, { PropsWithChildren } from 'react';
import arrowLeftIcon from '../assets/image/arrow-left.svg';
import arrowRightIcon from '../assets/image/arrow-right.svg';
import monster from '../assets/image/monster.png';
import desktop from '../assets/image/desktop.png';
import pick from '../assets/image/pick.png';
import eyes from '../assets/image/eyes.png';

//onClickToggleModal이라는 함수 타입을 정의한 인터페이스
interface ModalDefaultType {
  onClickToggleModal: () => void;
}

function Modal({
  onClickToggleModal, //모달을 닫기 위한 함수, 클릭 이벤트 발생 시 실행
  children, //모달 내부에 표시될 내용
}: PropsWithChildren<ModalDefaultType>) {
  return (
    // 모달을 가리키는 외부 컨테이너, 화면 전체를 덮음
    <div className="fixed flex h-full w-full items-center justify-center">
      {/* 실제 모달을 나타내는 컨테이너 */}
      <div
        className="z-50 box-border h-[550px] w-[720px] items-center justify-center
        rounded-[30px] bg-white shadow-stone-700"
      >
        {/* 이전/다음 */}
        <div className="mt-5">
          <div className="ml-5 font-lef text-gray-400 ">
            <button className="flex items-center">
              <img className="mr-2" src={arrowLeftIcon} />
              이전
            </button>

            <button className="ml-[620px] flex items-center">
              다음
              <img className="ml-2" src={arrowRightIcon} />
            </button>
          </div>

          {/* 단계 */}
          <ol className="ml-52 mb-8 flex w-80 items-center justify-center">
            <li className="flex w-full items-center text-blue-600 after:inline-block after:h-1 after:w-full after:border-4 after:border-b after:border-blue-100 after:content-[''] dark:text-blue-500 dark:after:border-blue-800">
              <span className="flex h-10 w-10 shrink-0 items-center justify-center rounded-full bg-blue-100 dark:bg-blue-800 lg:h-12 lg:w-12">
                <img className="h-[20px] w-[18px] " src={monster} />
              </span>
            </li>
            <li className="flex w-full items-center after:inline-block after:h-1 after:w-full after:border-4 after:border-b after:border-gray-100 after:content-[''] dark:after:border-gray-700">
              <span className="flex h-10 w-10 shrink-0 items-center justify-center rounded-full bg-gray-100 dark:bg-gray-700 lg:h-12 lg:w-12">
                <img className="h-[20px] w-[18px] " src={desktop} />
              </span>
            </li>
            <li className="flex w-full items-center after:inline-block after:h-1 after:w-full after:border-4 after:border-b after:border-gray-100 after:content-[''] dark:after:border-gray-700">
              <span className="flex h-10 w-10 shrink-0 items-center justify-center rounded-full bg-gray-100 dark:bg-gray-700 lg:h-12 lg:w-12">
                <img className="h-[20px] w-[18px] " src={pick} />
              </span>
            </li>
            <li className="flex w-full items-center">
              <span className="flex h-10 w-10 shrink-0 items-center justify-center rounded-full bg-gray-100 dark:bg-gray-700 lg:h-12 lg:w-12">
                <img className="h-[20px] w-[18px] " src={eyes} />
              </span>
            </li>
          </ol>

          <div className="flex items-center justify-center font-lef-b text-[20px]">
            <img className="h-[20px] w-[18px] " src={pick} />
            어떤 기술을 사용했나요?
          </div>
          <div className="flex justify-center">내용</div>
          <button
            className="mt-80 ml-24 flex items-center
            justify-center
          rounded-[16px] bg-gptbutton px-60 py-1 pt-3 pb-3 font-lef-b text-slate-50"
          >
            다음
          </button>
        </div>
        {children}
      </div>

      {/* 모달 영역 외의 배경을 클릭하면 모달이 닫히게 하는 컨테이너, 이벤트핸들러를 사용하여 클릭 이벤트 발생 시 onClickToggleModal 함수 호출하여 모달 닫음 */}
      <div
        className="fixed z-40 h-full w-full bg-zinc-600 backdrop-invert backdrop-opacity-10"
        onClick={(e: React.MouseEvent) => {
          e.preventDefault();

          if (onClickToggleModal) {
            onClickToggleModal();
          }
        }}
      />
    </div>
  );
}

export default Modal;

// export default function Modal({ open, onClose, children}) {

//     return (
//         //backdrop
//         <div onClick={onClose} className={`
//          fixed inset-0 flex justify-center items-center
//          transition-colors
//          ${open ? "visible bg-black/20" : "invisible"}
//         `}>
//             {children}
//         </div>
//     )
// }

//   return (
