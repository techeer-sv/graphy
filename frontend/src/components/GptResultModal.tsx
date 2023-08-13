import { useCallback, useState } from 'react';
import { useRecoilState, useRecoilValue } from 'recoil';

import { gptLoadingState, statusOpenState, modalContentState } from '../Recoil';

function GptResultModal() {
  const [resultOpen, setResultOpen] = useState<boolean>(false);
  const [statusOpen, setStatusOpen] = useRecoilState(statusOpenState);
  const gptLoading = useRecoilValue(gptLoadingState);
  const modalContent = useRecoilValue(modalContentState);

  const closeStatusModal = () => {
    setStatusOpen(false);
  };

  const openResultModal = () => {
    setResultOpen(true);
  };

  const closeResultModal = useCallback(() => {
    setResultOpen(!resultOpen);
  }, [resultOpen]);

  return (
    <div>
      {statusOpen ? (
        <div className="fixed right-4 bottom-8 z-10 flex h-16 w-[450px] items-center rounded bg-white py-4 pl-4 shadow-lg ">
          {gptLoading ? (
            <p className="ml-7 font-ng-b">
              AI 고도화 계획을 생성중입니다. 잠시만 기다려주세요.
            </p>
          ) : (
            <>
              <button
                className=" ml-1 w-96 font-ng-b"
                type="button"
                onClick={openResultModal}
              >
                고도화 계획 생성 완료. 결과를 보시려면 클릭해주세요.
              </button>
              <button
                className="ml-2 mr-4 mb-1 font-ng-eb text-2xl"
                onClick={closeStatusModal}
                type="button"
              >
                x
              </button>
            </>
          )}
        </div>
      ) : null}
      {resultOpen && (
        <div className="h-screen w-screen">
          <button
            aria-label="Toggle modal"
            className="fixed inset-0 z-40 h-full w-full  bg-black opacity-70"
            onClick={(e: React.MouseEvent) => {
              e.preventDefault();
              closeResultModal();
            }}
            type="button"
          />
          <div
            className=" fixed bottom-1/2 right-1/2 z-50 box-border
            h-auto w-[410px] translate-x-1/2 translate-y-1/2
            transform rounded-[30px] bg-white p-4 sm:w-630"
          >
            <div className="z-50 flex flex-col items-center justify-center font-lef-b text-[20px]">
              AI 고도화 계획 추천 결과
              <div className="mt-2 max-h-660 overflow-auto font-lef">
                {modalContent}
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default GptResultModal;
