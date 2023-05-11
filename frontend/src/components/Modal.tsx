import React, { PropsWithChildren } from 'react';

interface ModalDefaultType {
  onClickToggleModal: () => void;
}

function Modal({
  onClickToggleModal,
  children,
}: PropsWithChildren<ModalDefaultType>) {
  return (
    <div className="fixed flex h-full w-full items-center justify-center">
      <div
        className="z-50 box-border flex h-[400px] w-[800px] flex-col items-center
      rounded border-0 bg-white shadow-stone-700"
      >
        dkssud {children}
      </div>
      <div
        className="fixed z-40 mt-0 h-screen w-screen "
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
// <div>
//   <div>
//     <button>이전</button>
//     <span>다음</span>
//   </div>

//   <div>응답 목록</div>
//   <div className="font-lef-b">어떤 기술을 사용했나요?</div>
//   <div>내용</div>
//   <button className="rounded-[16px] bg-gptbutton px-60 py-1 pt-3 pb-3 font-lef-b text-slate-50">
//     다음
//   </button>
// </div>
