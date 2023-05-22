import React, { useState, PropsWithChildren } from 'react';
import arrowLeftIcon from '../assets/image/arrow-left.svg';
import arrowRightIcon from '../assets/image/arrow-right.svg';
import monster from '../assets/image/monster.png';
import desktop from '../assets/image/desktop.png';
import pick from '../assets/image/pick.png';
import eyes from '../assets/image/eyes.png';
import plus from '../assets/image/plus-circle.svg';
import AllStacks from '../Stack';

function findImage(tag: string) {
  return AllStacks.map((x) => x.image)[
    AllStacks.map((x) => x.name).findIndex((x) => x == tag)
  ];
}

// 화면 컴포넌트
interface Screen1Props {
  onNext: () => void;
}

const Screen1: React.FC<Screen1Props> = ({ onNext }) => {
  return (
    <div
      className="-translate-y-1/2-translate-y-1/2 fixed top-20 right-1/2 z-50 box-border h-660
        w-630 translate-x-1/2 transform 
        rounded-[30px] bg-white"
    >
      {/* 이전/다음 */}
      <div className="mt-5">
        <div className="ml-555 flex font-lef text-gray-400 ">
          <button onClick={onNext} className="right-5 flex w-56 items-center">
            <div>다음</div>
            <img className="ml-2" src={arrowRightIcon} />
          </button>
        </div>

        {/* 1단계 */}
        <ol className="ml-44 mt-4 mb-8 flex w-80 items-center justify-center">
          <li className="flex w-full items-center text-blue-600 after:inline-block after:h-1 after:w-full after:border-4 after:border-b after:border-blue-100 after:content-[''] dark:text-blue-500 dark:after:border-blue-800">
            <span className="flex h-10 w-10 shrink-0 items-center justify-center rounded-full bg-blue-100 dark:bg-blue-800 lg:h-12 lg:w-12">
              <img className="h-[20px] w-[20px] " src={monster} />
            </span>
          </li>
          <li className="flex w-full items-center after:inline-block after:h-1 after:w-full after:border-4 after:border-b after:border-gray-100 after:content-[''] dark:after:border-gray-700">
            <span className="flex h-10 w-10 shrink-0 items-center justify-center rounded-full bg-gray-100 dark:bg-gray-700 lg:h-12 lg:w-12">
              <img className="h-[20px] w-[20px] " src={desktop} />
            </span>
          </li>
          <li className="flex w-full items-center after:inline-block after:h-1 after:w-full after:border-4 after:border-b after:border-gray-100 after:content-[''] dark:after:border-gray-700">
            <span className="flex h-10 w-10 shrink-0 items-center justify-center rounded-full bg-gray-100 dark:bg-gray-700 lg:h-12 lg:w-12">
              <img className="h-[20px] w-[20px] " src={pick} />
            </span>
          </li>
          <li className="flex w-full items-center">
            <span className="flex h-10 w-10 shrink-0 items-center justify-center rounded-full bg-gray-100 dark:bg-gray-700 lg:h-12 lg:w-12">
              <img className="h-[20px] w-[20px] " src={eyes} />
            </span>
          </li>
        </ol>

        {/* screen1 */}
        {/* 질문 */}
        <div className="flex items-center justify-center font-lef-b text-[20px]">
          <img className="h-[20px] w-[20px] " src={monster} />
          &nbsp; 어떤 기술을 사용했나요?
        </div>
        {/* 기술 선택 버튼 */}
        {/* 배열에 요소를 처음부터 하나씩 읽는 게 map, 하나씩 읽을 때 요소를 가리키는 게 x, y는 요소의 인덱스 */}
        <div className="mt-8 flex justify-center ">
          {AllStacks.map((x, y) => (
            <button
              key={y}
              className="mr-2 mb-2 flex h-auto shrink-0 flex-row items-center rounded-full border-[1.5px] border-zinc-300 py-1.5 pl-3 pr-3 text-gray-500"
            >
              <img className="mr-1 h-[20px] w-[20px]" src={findImage(x.name)} />
              {x.name}
            </button>
          ))}
        </div>

        {/* 하단 버튼 */}
        <button
          className="ml-16 mt-80 flex items-center
            justify-center 
          rounded-[16px] bg-gptbutton px-60 py-1 pt-3 pb-3 font-lef-b text-slate-50"
          onClick={onNext}
        >
          다음
        </button>
      </div>
      {/* {children} */}
    </div>
  );
};

interface Screen2Props {
  onPrev: () => void;
  onNext: () => void;
}

const Screen2: React.FC<Screen2Props> = ({ onPrev, onNext }) => {
  return (
    <div
      className="-translate-y-1/2-translate-y-1/2 fixed top-20 right-1/2 z-50 box-border h-660
        w-630 translate-x-1/2 transform 
        rounded-[30px] bg-white"
    >
      {/* 이전/다음 */}
      <div className="mt-5">
        <div className="ml-5 flex font-lef text-gray-400 ">
          <button onClick={onPrev} className="flex w-56 items-center">
            <img className="mr-2" src={arrowLeftIcon} />
            이전
          </button>

          <button onClick={onNext} className="ml-460 flex w-56 items-center">
            <div>다음</div>
            <img className="ml-2" src={arrowRightIcon} />
          </button>
        </div>

        {/* 2단계 */}
        <ol className="ml-44 mt-4 mb-8 flex w-80 items-center justify-center">
          <li className="flex w-full items-center text-blue-600 after:inline-block after:h-1 after:w-full after:border-4 after:border-b after:border-blue-100 after:content-[''] dark:text-blue-500 dark:after:border-blue-800">
            <span className="flex h-10 w-10 shrink-0 items-center justify-center rounded-full bg-blue-100 dark:bg-blue-800 lg:h-12 lg:w-12">
              <img className="h-[20px] w-[20px] " src={monster} />
            </span>
          </li>
          <li className="flex w-full items-center text-blue-600 after:inline-block after:h-1 after:w-full after:border-4 after:border-b after:border-blue-100 after:content-[''] dark:text-blue-500 dark:after:border-blue-800">
            <span className="flex h-10 w-10 shrink-0 items-center justify-center rounded-full bg-blue-100 dark:bg-blue-800 lg:h-12 lg:w-12">
              <img className="h-[20px] w-[20px] " src={desktop} />
            </span>
          </li>
          <li className="flex w-full items-center after:inline-block after:h-1 after:w-full after:border-4 after:border-b after:border-gray-100 after:content-[''] dark:after:border-gray-700">
            <span className="flex h-10 w-10 shrink-0 items-center justify-center rounded-full bg-gray-100 dark:bg-gray-700 lg:h-12 lg:w-12">
              <img className="h-[20px] w-[20px] " src={pick} />
            </span>
          </li>
          <li className="flex w-full items-center">
            <span className="flex h-10 w-10 shrink-0 items-center justify-center rounded-full bg-gray-100 dark:bg-gray-700 lg:h-12 lg:w-12">
              <img className="h-[20px] w-[20px] " src={eyes} />
            </span>
          </li>
        </ol>

        {/* screen2 */}
        {/* 질문 */}
        <div className="flex items-center justify-center font-lef-b text-[20px]">
          <img className="h-[20px] w-[20px] " src={desktop} />
          &nbsp; 어떤 프로젝트를 구현했나요?
        </div>
        {/* 프로젝트 설명 입력창 */}
        <div className="relative mt-8" data-te-input-wrapper-init>
          <input
            type="text"
            maxLength={20}
            className="ml-[60px] flex w-10/12 justify-center border-b-2 focus:outline-none"
            placeholder=" 예시 - 팀원 모집 웹 사이트"
          />
        </div>
        {/* 하단 버튼 */}
        <button
          onClick={onNext}
          className="fixed ml-16 mt-80
          flex items-center justify-center
        rounded-[16px] bg-gptbutton px-60 py-1 pt-3 pb-3 font-lef-b text-slate-50"
        >
          다음
        </button>
      </div>
      {/* {children} */}
    </div>
  );
};

interface Screen3Props {
  onPrev: () => void;
  onNext: () => void;
}

const Screen3: React.FC<Screen3Props> = ({ onPrev, onNext }) => {
  const [Arr, setArr] = useState<number[]>([]);

  function Plus() {
    let Arr2 = [...Arr];
    if (Arr.length < 4) {
      Arr2.push(0);
      setArr(Arr2);
    } else {
      null;
    }
  }
  return (
    <div
      className="-translate-y-1/2-translate-y-1/2 fixed top-20 right-1/2 z-50 box-border h-660
        w-630 translate-x-1/2 transform 
        rounded-[30px] bg-white"
    >
      {/* 이전/다음 */}
      <div className="mt-5">
        <div className="ml-5 flex font-lef text-gray-400 ">
          <button onClick={onPrev} className="flex w-56 items-center">
            <img className="mr-2" src={arrowLeftIcon} />
            이전
          </button>

          <button onClick={onNext} className="ml-460 flex w-56 items-center">
            <div>다음</div>
            <img className="ml-2" src={arrowRightIcon} />
          </button>
        </div>

        {/* 3단계 */}
        <ol className="ml-44 mt-4 mb-8 flex w-80 items-center justify-center">
          <li className="flex w-full items-center text-blue-600 after:inline-block after:h-1 after:w-full after:border-4 after:border-b after:border-blue-100 after:content-[''] dark:text-blue-500 dark:after:border-blue-800">
            <span className="flex h-10 w-10 shrink-0 items-center justify-center rounded-full bg-blue-100 dark:bg-blue-800 lg:h-12 lg:w-12">
              <img className="h-[20px] w-[20px] " src={monster} />
            </span>
          </li>
          <li className="flex w-full items-center text-blue-600 after:inline-block after:h-1 after:w-full after:border-4 after:border-b after:border-blue-100 after:content-[''] dark:text-blue-500 dark:after:border-blue-800">
            <span className="flex h-10 w-10 shrink-0 items-center justify-center rounded-full bg-blue-100 dark:bg-blue-800 lg:h-12 lg:w-12">
              <img className="h-[20px] w-[20px] " src={desktop} />
            </span>
          </li>
          <li className="flex w-full items-center text-blue-600 after:inline-block after:h-1 after:w-full after:border-4 after:border-b after:border-blue-100 after:content-[''] dark:text-blue-500 dark:after:border-blue-800">
            <span className="flex h-10 w-10 shrink-0 items-center justify-center rounded-full bg-blue-100 dark:bg-blue-800 lg:h-12 lg:w-12">
              <img className="h-[20px] w-[20px] " src={pick} />
            </span>
          </li>
          <li className="flex w-full items-center">
            <span className="flex h-10 w-10 shrink-0 items-center justify-center rounded-full bg-gray-100 dark:bg-gray-700 lg:h-12 lg:w-12">
              <img className="h-[20px] w-[20px] " src={eyes} />
            </span>
          </li>
        </ol>

        {/* screen3 */}
        {/* 질문 */}
        <div className="flex items-center justify-center font-lef-b text-[20px]">
          <img className="h-[20px] w-[20px] " src={pick} />
          &nbsp; 어떤 기능을 구현했나요?
        </div>
        {/* 기능 구현 입력창 */}
        <div className="relative mt-8" data-te-input-wrapper-init>
          <input
            type="text"
            maxLength={20}
            className="ml-12 mb-7 flex w-10/12 justify-center border-b-2 focus:outline-none"
            placeholder=" 예시 - 구인글 작성 (최대 5개 작성 가능)"
          />
          {Arr.map((x, y) => (
            <input
              key={y}
              type="text"
              maxLength={20}
              className="ml-12 mb-7 flex w-10/12 justify-center border-b-2 focus:outline-none"
              placeholder="기능을 입력해주세요"
            />
          ))}
          {Arr.length < 4 ? (
            <button
              className="ml-[297px] mt-5 flex items-center justify-center"
              onClick={() => Plus()}
            >
              <img src={plus} />
            </button>
          ) : null}
        </div>
        {/* 하단버튼 */}
        <button
          onClick={onNext}
          className="fixed z-40 mt-20 ml-14
          flex items-center justify-center
        rounded-[16px] bg-gptbutton px-60 py-1 pt-3 pb-3 font-lef-b text-slate-50"
        >
          다음
        </button>
      </div>
      {/* {children} */}
    </div>
  );
};

interface Screen4Props {
  onPrev: () => void;
}

const Screen4: React.FC<Screen4Props> = ({ onPrev }) => {
  const [Arr, setArr] = useState<number[]>([]);

  function Plus() {
    let Arr2 = [...Arr];
    if (Arr.length < 4) {
      Arr2.push(0);
      setArr(Arr2);
    } else {
      null;
    }
  }
  return (
    <div
      className="-translate-y-1/2-translate-y-1/2 fixed top-20 right-1/2 z-50 box-border h-660
        w-630 translate-x-1/2 transform 
        rounded-[30px] bg-white"
    >
      {/* 이전/다음 */}
      <div className="mt-5">
        <div className="ml-5 flex font-lef text-gray-400 ">
          <button onClick={onPrev} className="flex w-56 items-center">
            <img className="mr-2" src={arrowLeftIcon} />
            이전
          </button>
        </div>

        {/* 4단계 */}
        <ol className="ml-44 mt-4 mb-8 flex w-80 items-center justify-center">
          <li className="flex w-full items-center text-blue-600 after:inline-block after:h-1 after:w-full after:border-4 after:border-b after:border-blue-100 after:content-[''] dark:text-blue-500 dark:after:border-blue-800">
            <span className="flex h-10 w-10 shrink-0 items-center justify-center rounded-full bg-blue-100 dark:bg-blue-800 lg:h-12 lg:w-12">
              <img className="h-[20px] w-[20px] " src={monster} />
            </span>
          </li>
          <li className="flex w-full items-center text-blue-600 after:inline-block after:h-1 after:w-full after:border-4 after:border-b after:border-blue-100 after:content-[''] dark:text-blue-500 dark:after:border-blue-800">
            <span className="flex h-10 w-10 shrink-0 items-center justify-center rounded-full bg-blue-100 dark:bg-blue-800 lg:h-12 lg:w-12">
              <img className="h-[20px] w-[20px] " src={desktop} />
            </span>
          </li>
          <li className="flex w-full items-center text-blue-600 after:inline-block after:h-1 after:w-full after:border-4 after:border-b after:border-blue-100 after:content-[''] dark:text-blue-500 dark:after:border-blue-800">
            <span className="flex h-10 w-10 shrink-0 items-center justify-center rounded-full bg-blue-100 dark:bg-blue-800 lg:h-12 lg:w-12">
              <img className="h-[20px] w-[20px] " src={pick} />
            </span>
          </li>
          <li className="flex w-full items-center ">
            <span className="flex h-10 w-10 shrink-0 items-center justify-center rounded-full bg-blue-100 dark:bg-blue-800 lg:h-12 lg:w-12">
              <img className="h-[20px] w-[20px] " src={eyes} />
            </span>
          </li>
        </ol>

        {/* screen4 */}
        {/* 질문 */}
        <div className="flex items-center justify-center font-lef-b text-[20px]">
          <img className="h-[20px] w-[20px] " src={eyes} />
          &nbsp; 관심있는 고도화 계획이 있나요?
        </div>
        {/* 고도화 기술 입력창 */}
        <div className="relative mt-8" data-te-input-wrapper-init>
          <input
            type="text"
            maxLength={20}
            className="ml-12 mb-7 flex w-10/12 justify-center border-b-2 focus:outline-none"
            placeholder=" 예시 - 캐싱 (최대 5개 작성 가능)"
          />{' '}
          {Arr.map((x, y) => (
            <input
              key={y}
              maxLength={20}
              type="text"
              className="ml-12 mb-7 flex w-10/12 justify-center border-b-2 focus:outline-none"
              placeholder="고도화 계획을 입력해주세요"
            />
          ))}
          {Arr.length < 4 ? (
            <button
              className=" ml-[297px] mt-5 flex h-5 w-5 items-center justify-center"
              onClick={() => Plus()}
            >
              <img src={plus} />
            </button>
          ) : null}
          {/* <img
            className=" ml-[21rem] mt-5 flex h-5 w-5 items-center justify-center"
            src={plus}
          /> */}
        </div>
        {/* 하단버튼 */}
        <button
          className="fixed mt-20 ml-14
          items-center justify-center
        rounded-[16px] bg-gptbutton px-52 py-1 pt-3 pb-3 font-lef-b text-slate-50"
        >
          AI 고도화 추천
        </button>
      </div>
      {/* {children} */}
    </div>
  );
};

// 모달 컴포넌트
interface ModalDefaultType {
  onClickToggleModal: () => void;
}

function renderModal({
  onClickToggleModal,
  children,
}: PropsWithChildren<ModalDefaultType>) {
  const [currentScreen, setCurrentScreen] = useState<number>(1);

  const handleNext = () => {
    setCurrentScreen(currentScreen + 1);
  };

  const handlePrev = () => {
    setCurrentScreen(currentScreen - 1);
  };

  const renderScreen = () => {
    switch (currentScreen) {
      case 1:
        return <Screen1 onNext={handleNext} />;
      case 2:
        return <Screen2 onPrev={handlePrev} onNext={handleNext} />;
      case 3:
        return <Screen3 onPrev={handlePrev} onNext={handleNext} />;
      case 4:
        return <Screen4 onPrev={handlePrev} />;
      default:
        return null;
    }
  };

  return (
    <div>
      <div>{renderScreen()}</div>
      {/* 모달 영역 외의 배경을 클릭하면 모달이 닫히게 하는 컨테이너, 이벤트핸들러를 사용하여 클릭 이벤트 발생 시 onClickToggleModal 함수 호출하여 모달 닫음 */}
      <div
        className="fixed top-0 left-0 right-0 bottom-0 z-40 h-full w-screen  bg-black opacity-70"
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

export default renderModal;
