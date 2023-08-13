import axios from 'axios';
import React, { useState, PropsWithChildren, useEffect } from 'react';
import { useRecoilState, useRecoilValue } from 'recoil';
import { v4 as uuidv4 } from 'uuid';

import arrowLeftIcon from '../assets/image/arrow-left.svg';
import arrowRightIcon from '../assets/image/arrow-right.svg';
import desktop from '../assets/image/desktop.png';
import eyes from '../assets/image/eyes.png';
import monster from '../assets/image/monster.png';
import pick from '../assets/image/pick.png';
import plus from '../assets/image/plus-circle.svg';
import {
  featuresState,
  gptLoadingState,
  statusOpenState,
  modalContentState,
  persistTokenState,
  plansState,
  selectedStackState,
  techStacksState,
  tldrState,
  topicState,
} from '../Recoil';
import AllStacks from '../Stack';

function findImage(tag: string) {
  return AllStacks.map((x) => x.image)[
    AllStacks.map((x) => x.name).findIndex((x) => x === tag)
  ];
}

// 화면 컴포넌트
type Screen1Props = {
  onNext: () => void;
};

const Screen1 = ({ onNext }: Screen1Props) => {
  const [techStacks, setTechStacks] = useRecoilState(techStacksState);
  const selectedStack = useRecoilValue(selectedStackState);

  // 기존 선택 기술 스택 삭제 함수
  const handleDeleteStack = (stack: string) => {
    setTechStacks(techStacks.filter((s: any) => s !== stack));
  };

  // 새로운 기술 스택 추가 함수
  const handleAddStack = (stack: string) => {
    if (techStacks.length < 6 && !techStacks.includes(stack)) {
      setTechStacks([...techStacks, stack]);
    }
  };

  useEffect(() => {
    setTechStacks(selectedStack);
  }, []);

  return (
    <div
      className=" fixed bottom-1/2 right-1/2 z-50 box-border 
      h-660  w-[410px] translate-x-1/2 translate-y-1/2
      transform rounded-[30px] 
      bg-white sm:w-630"
    >
      {/* 이전/다음 */}
      <button
        onClick={onNext}
        className="mx-auto mt-5 mr-5 flex items-center font-lef text-gray-400 hover:text-gray-600 "
        type="button"
      >
        <div>다음</div>
        <img className="ml-2" src={arrowRightIcon} alt="arrowRight" />
      </button>

      {/* 1단계 */}
      <ol className="ml-16 mt-4 mb-8 flex w-80 items-center justify-center sm:ml-44">
        <li className="flex w-full items-center text-blue-600 after:inline-block after:h-1 after:w-full after:border-4 after:border-b after:border-blue-100 after:content-[''] dark:text-blue-500 dark:after:border-blue-800">
          <span className="flex h-10 w-10 shrink-0 items-center justify-center rounded-full bg-blue-100 dark:bg-blue-800 lg:h-12 lg:w-12">
            <img className="h-[20px] w-[20px] " src={monster} alt="monster" />
          </span>
        </li>
        <li className="flex w-full items-center after:inline-block after:h-1 after:w-full after:border-4 after:border-b after:border-gray-100 after:content-[''] dark:after:border-gray-700">
          <span className="flex h-10 w-10 shrink-0 items-center justify-center rounded-full bg-gray-100 dark:bg-gray-700 lg:h-12 lg:w-12">
            <img className="h-[20px] w-[20px] " src={desktop} alt="desktop" />
          </span>
        </li>
        <li className="flex w-full items-center after:inline-block after:h-1 after:w-full after:border-4 after:border-b after:border-gray-100 after:content-[''] dark:after:border-gray-700">
          <span className="flex h-10 w-10 shrink-0 items-center justify-center rounded-full bg-gray-100 dark:bg-gray-700 lg:h-12 lg:w-12">
            <img className="h-[20px] w-[20px] " src={pick} alt="pick" />
          </span>
        </li>
        <li className="flex w-full items-center">
          <span className="flex h-10 w-10 shrink-0 items-center justify-center rounded-full bg-gray-100 dark:bg-gray-700 lg:h-12 lg:w-12">
            <img className="h-[20px] w-[20px] " src={eyes} alt="eyes" />
          </span>
        </li>
      </ol>
      {/* screen1 */}
      {/* 질문 */}
      <div className="flex items-center justify-center font-lef-b text-[20px]">
        <img className="h-[20px] w-[20px] " src={monster} alt="monster" />
        어떤 기술을 사용했나요?
      </div>
      {/* 기술 선택 버튼 */}
      {/* 배열에 요소를 처음부터 하나씩 읽는 게 map, 하나씩 읽을 때 요소를 가리키는 게 x, y는 요소의 인덱스 */}
      <div className="mt-8 flex h-355 w-[400px] flex-wrap justify-center overflow-auto sm:w-630 ">
        {AllStacks.map((x) =>
          techStacks.includes(x.name) ? (
            <button
              key={x.name}
              className="mr-2 mb-2 flex h-auto shrink-0 flex-row items-center rounded-full border-[1.5px] border-zinc-300 bg-graphyblue py-1.5 pl-3 pr-3 text-white"
              type="button"
              onClick={() => handleDeleteStack(x.name)}
            >
              <img
                className="mr-1 h-[20px] w-[20px]"
                src={findImage(x.name)}
                alt="stack"
              />
              {x.name}
            </button>
          ) : (
            <button
              key={x.name}
              className="mr-2 mb-2 flex h-auto shrink-0 flex-row items-center rounded-full border-[1.5px] border-zinc-300 py-1.5 pl-3 pr-3 text-gray-500"
              type="button"
              onClick={() => handleAddStack(x.name)}
            >
              <img
                className="mr-1 h-[20px] w-[20px]"
                src={findImage(x.name)}
                alt="stack"
              />
              {x.name}
            </button>
          ),
        )}
      </div>
      {/* 하단 버튼 */}
      <button
        className="ml-7 mt-7 flex items-center justify-center
            rounded-[16px] bg-gptbutton
          px-40 py-1 pt-3 pb-3 font-lef-b text-slate-50 hover:bg-button sm:ml-504 sm:px-60"
        onClick={onNext}
        type="button"
      >
        다음
      </button>
    </div>
  );
};

type Screen2Props = {
  onPrev: () => void;
  onNext: () => void;
};

const Screen2 = ({ onPrev, onNext }: Screen2Props) => {
  const [topic, setTopic] = useRecoilState(topicState);
  const tldr = useRecoilValue(tldrState);

  useEffect(() => {
    setTopic(tldr);
  }, []);

  return (
    <div
      className=" fixed bottom-1/2 right-1/2 z-50 box-border 
      h-660  w-[410px] translate-x-1/2 translate-y-1/2
      transform rounded-[30px] 
      bg-white sm:w-630"
    >
      {/* 이전/다음 */}
      <div>
        <div className="flex">
          <button
            onClick={onPrev}
            className="mx-auto mt-5 ml-5 flex items-center font-lef text-gray-400 hover:text-gray-600"
            type="button"
          >
            <img className="mr-2" src={arrowLeftIcon} alt="arrowLeft" />
            이전
          </button>

          <button
            onClick={onNext}
            className="mx-auto mt-5 mr-5 flex items-center font-lef text-gray-400 hover:text-gray-600"
            type="button"
          >
            <div>다음</div>
            <img className="ml-2" src={arrowRightIcon} alt="arrowRight" />
          </button>
        </div>

        {/* 2단계 */}
        <ol className=" ml-16  mt-4 mb-8 flex w-80 items-center justify-center sm:ml-44">
          <li className="flex w-full items-center text-blue-600 after:inline-block after:h-1 after:w-full after:border-4 after:border-b after:border-blue-100 after:content-[''] dark:text-blue-500 dark:after:border-blue-800">
            <span className="flex h-10 w-10 shrink-0 items-center justify-center rounded-full bg-blue-100 dark:bg-blue-800 lg:h-12 lg:w-12">
              <img className="h-[20px] w-[20px] " src={monster} alt="monster" />
            </span>
          </li>
          <li className="flex w-full items-center text-blue-600 after:inline-block after:h-1 after:w-full after:border-4 after:border-b after:border-blue-100 after:content-[''] dark:text-blue-500 dark:after:border-blue-800">
            <span className="flex h-10 w-10 shrink-0 items-center justify-center rounded-full bg-blue-100 dark:bg-blue-800 lg:h-12 lg:w-12">
              <img className="h-[20px] w-[20px] " src={desktop} alt="desktop" />
            </span>
          </li>
          <li className="flex w-full items-center after:inline-block after:h-1 after:w-full after:border-4 after:border-b after:border-gray-100 after:content-[''] dark:after:border-gray-700">
            <span className="flex h-10 w-10 shrink-0 items-center justify-center rounded-full bg-gray-100 dark:bg-gray-700 lg:h-12 lg:w-12">
              <img className="h-[20px] w-[20px] " src={pick} alt="pick" />
            </span>
          </li>
          <li className="flex w-full items-center">
            <span className="flex h-10 w-10 shrink-0 items-center justify-center rounded-full bg-gray-100 dark:bg-gray-700 lg:h-12 lg:w-12">
              <img className="h-[20px] w-[20px] " src={eyes} alt="eyes" />
            </span>
          </li>
        </ol>
        {/* screen2 */}
        {/* 질문 */}
        <div className="flex items-center justify-center font-lef-b text-[20px]">
          <img className="h-[20px] w-[20px] " src={desktop} alt="desktop" />
          어떤 프로젝트를 구현했나요?
        </div>
        {/* 프로젝트 설명 입력창 */}
        <div className="relative mt-8" data-te-input-wrapper-init>
          <input
            type="text"
            maxLength={20}
            className="ml-9 flex w-10/12 justify-center border-b-2 focus:outline-none sm:ml-[60px]"
            placeholder=" 예시 - 팀원 모집 웹 사이트"
            value={topic}
            onChange={(e) => setTopic(e.target.value)}
          />
        </div>
        {/* 하단 버튼 */}
        <button
          onClick={onNext}
          className="fixed  mt-80
          ml-7 flex items-center justify-center rounded-[16px]  bg-gptbutton px-40
        py-1 pt-3 pb-3 font-lef-b text-slate-50 hover:bg-button sm:ml-504  sm:px-60"
          type="button"
        >
          다음
        </button>
      </div>
    </div>
  );
};

type Screen3Props = {
  onPrev: () => void;
  onNext: () => void;
};

const Screen3 = ({ onPrev, onNext }: Screen3Props) => {
  const [featureObject, setFeatureObject] = useState<
    { id: string; value: string }[]
  >([]);
  const [firstFeature, setFirstFeature] = useState('');
  const [, setFeatures] = useRecoilState(featuresState);

  function Plus() {
    if (featureObject.length < 4) {
      setFeatureObject((oldFeatures) => [
        ...oldFeatures,
        { id: uuidv4(), value: '' },
      ]);
    }
  }

  const handleInputChange = (id: string, value: string) => {
    setFeatureObject((oldFeatures) =>
      oldFeatures.map((feature) =>
        feature.id === id ? { ...feature, value } : feature,
      ),
    );
  };

  const handleFirstInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFirstFeature(e.target.value);
  };

  useEffect(() => {
    setFeatures([firstFeature, ...featureObject.map((x) => x.value)]);
  }, [firstFeature, featureObject]);

  return (
    <div
      className=" fixed bottom-1/2 right-1/2 z-50 box-border 
      h-660  w-[410px] translate-x-1/2 translate-y-1/2
      transform rounded-[30px] 
      bg-white sm:w-630"
    >
      {/* 이전/다음 */}
      <div className="">
        <div className="flex">
          <button
            onClick={onPrev}
            className="mx-auto mt-5 ml-5 flex  items-center font-lef text-gray-400 hover:text-gray-600"
            type="button"
          >
            <img className="mr-2" src={arrowLeftIcon} alt="arrowLeft" />
            이전
          </button>

          <button
            onClick={onNext}
            className="mx-auto mt-5 mr-5 flex  items-center font-lef text-gray-400 hover:text-gray-600"
            type="button"
          >
            <div>다음</div>
            <img className="ml-2" src={arrowRightIcon} alt="arrowRight" />
          </button>
        </div>
        {/* 3단계 */}
        <ol className="ml-16  mt-4  mb-8 flex w-80 items-center justify-center sm:ml-44">
          <li className="flex w-full items-center text-blue-600 after:inline-block after:h-1 after:w-full after:border-4 after:border-b after:border-blue-100 after:content-[''] dark:text-blue-500 dark:after:border-blue-800">
            <span className="flex h-10 w-10 shrink-0 items-center justify-center rounded-full bg-blue-100 dark:bg-blue-800 lg:h-12 lg:w-12">
              <img className="h-[20px] w-[20px] " src={monster} alt="monster" />
            </span>
          </li>
          <li className="flex w-full items-center text-blue-600 after:inline-block after:h-1 after:w-full after:border-4 after:border-b after:border-blue-100 after:content-[''] dark:text-blue-500 dark:after:border-blue-800">
            <span className="flex h-10 w-10 shrink-0 items-center justify-center rounded-full bg-blue-100 dark:bg-blue-800 lg:h-12 lg:w-12">
              <img className="h-[20px] w-[20px] " src={desktop} alt="desktop" />
            </span>
          </li>
          <li className="flex w-full items-center text-blue-600 after:inline-block after:h-1 after:w-full after:border-4 after:border-b after:border-blue-100 after:content-[''] dark:text-blue-500 dark:after:border-blue-800">
            <span className="flex h-10 w-10 shrink-0 items-center justify-center rounded-full bg-blue-100 dark:bg-blue-800 lg:h-12 lg:w-12">
              <img className="h-[20px] w-[20px] " src={pick} alt="pick" />
            </span>
          </li>
          <li className="flex w-full items-center">
            <span className="flex h-10 w-10 shrink-0 items-center justify-center rounded-full bg-gray-100 dark:bg-gray-700 lg:h-12 lg:w-12">
              <img className="h-[20px] w-[20px] " src={eyes} alt="eyes" />
            </span>
          </li>
        </ol>

        {/* screen3 */}
        {/* 질문 */}
        <div className="flex items-center justify-center font-lef-b text-[20px]">
          <img className="h-[20px] w-[20px] " src={pick} alt="pick" />
          어떤 기능을 구현했나요?
        </div>
        {/* 기능 구현 입력창 */}
        <div className="relative mt-8 h-355" data-te-input-wrapper-init>
          <input
            type="text"
            maxLength={20}
            className=" ml-9  mb-7 flex w-10/12 justify-center border-b-2 focus:outline-none sm:ml-12"
            placeholder="예시 - 구인글 작성 (최대 5개 작성 가능)"
            value={firstFeature}
            onChange={handleFirstInputChange}
          />
          {featureObject.map((x) => (
            <input
              key={x.id}
              type="text"
              maxLength={20}
              className=" ml-9  mb-7 flex w-10/12 justify-center border-b-2 focus:outline-none sm:ml-12"
              placeholder="기능을 입력해주세요"
              value={x.value}
              onChange={(e) => handleInputChange(x.id, e.target.value)}
            />
          ))}
          {featureObject.length < 4 ? (
            <button
              className="ml-48 mt-5 flex items-center justify-center sm:ml-[297px]"
              onClick={() => Plus()}
              type="button"
            >
              <img src={plus} alt="plus" />
            </button>
          ) : null}
        </div>
        {/* 하단버튼 */}
        <button
          onClick={onNext}
          className="fixed z-40 my-auto mb-3
          ml-7 flex items-center justify-center rounded-[16px]  bg-gptbutton px-40
        py-1 pt-3 pb-3 font-lef-b text-slate-50 hover:bg-button sm:ml-504  sm:px-60"
          type="button"
        >
          다음
        </button>
      </div>
    </div>
  );
};

type Screen4Props = {
  onPrev: () => void;
  onClickToggleModal: () => void;
};

const Screen4 = ({ onPrev, onClickToggleModal }: Screen4Props) => {
  const accessToken = sessionStorage.getItem('accessToken');
  const persistToken = useRecoilValue(persistTokenState);

  const [plans, setPlans] = useRecoilState(plansState);
  const [, setGptLoading] = useRecoilState(gptLoadingState);
  const [, setStatusOpen] = useRecoilState(statusOpenState);
  const [, setModalContent] = useRecoilState(modalContentState);

  const techStacks = useRecoilValue(techStacksState);
  const topic = useRecoilValue(topicState);
  const features = useRecoilValue(featuresState);

  const [planObject, setPlanObject] = useState<{ id: string; value: string }[]>(
    [],
  );
  const [firstPlan, setFirstPlans] = useState('');

  function Plus() {
    if (planObject.length < 4) {
      setPlanObject((oldFeatures) => [
        ...oldFeatures,
        { id: uuidv4(), value: '' },
      ]);
    }
  }

  const handleInputChange = (id: string, value: string) => {
    setPlanObject((oldFeatures) =>
      oldFeatures.map((feature) =>
        feature.id === id ? { ...feature, value } : feature,
      ),
    );
  };

  const handleFirstInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFirstPlans(e.target.value);
  };

  async function toSubmit() {
    const url = 'http://localhost:8080/api/v1/projects/plans';
    const data = {
      plans,
      techStacks,
      topic,
      features,
    };
    try {
      setGptLoading(true);
      setStatusOpen(true);
      if (onClickToggleModal) {
        onClickToggleModal();
      }
      const res = await axios.post(url, data, {
        headers: {
          Authorization: `Bearer ${accessToken || persistToken}`,
        },
      });
      setGptLoading(false);
      setModalContent(res.data.data);
    } catch (err) {
      setGptLoading(false);
      console.log(err);
    }
  }

  useEffect(() => {
    setPlans([firstPlan, ...planObject.map((x) => x.value)]);
  }, [firstPlan, planObject]);

  return (
    <div
      className=" fixed bottom-1/2 right-1/2 z-50 box-border 
      h-660  w-[410px] translate-x-1/2 translate-y-1/2
      transform rounded-[30px] 
      bg-white sm:w-630"
    >
      {/* 이전/다음 */}
      <button
        onClick={onPrev}
        className="mx-auto mt-5 ml-5 flex  items-center font-lef text-gray-400 hover:text-gray-600 "
        type="button"
      >
        <img className="mr-2" src={arrowLeftIcon} alt="arrowLeft" />
        <div>이전</div>
      </button>

      {/* 4단계 */}
      <ol className="ml-16  mt-4 mb-8 flex w-80 items-center justify-center sm:ml-44">
        <li className="flex w-full items-center text-blue-600 after:inline-block after:h-1 after:w-full after:border-4 after:border-b after:border-blue-100 after:content-[''] dark:text-blue-500 dark:after:border-blue-800">
          <span className="flex h-10 w-10 shrink-0 items-center justify-center rounded-full bg-blue-100 dark:bg-blue-800 lg:h-12 lg:w-12">
            <img className="h-[20px] w-[20px] " src={monster} alt="monster" />
          </span>
        </li>
        <li className="flex w-full items-center text-blue-600 after:inline-block after:h-1 after:w-full after:border-4 after:border-b after:border-blue-100 after:content-[''] dark:text-blue-500 dark:after:border-blue-800">
          <span className="flex h-10 w-10 shrink-0 items-center justify-center rounded-full bg-blue-100 dark:bg-blue-800 lg:h-12 lg:w-12">
            <img className="h-[20px] w-[20px] " src={desktop} alt="desktop" />
          </span>
        </li>
        <li className="flex w-full items-center text-blue-600 after:inline-block after:h-1 after:w-full after:border-4 after:border-b after:border-blue-100 after:content-[''] dark:text-blue-500 dark:after:border-blue-800">
          <span className="flex h-10 w-10 shrink-0 items-center justify-center rounded-full bg-blue-100 dark:bg-blue-800 lg:h-12 lg:w-12">
            <img className="h-[20px] w-[20px] " src={pick} alt="pick" />
          </span>
        </li>
        <li className="flex w-full items-center ">
          <span className="flex h-10 w-10 shrink-0 items-center justify-center rounded-full bg-blue-100 dark:bg-blue-800 lg:h-12 lg:w-12">
            <img className="h-[20px] w-[20px] " src={eyes} alt="eyes" />
          </span>
        </li>
      </ol>

      {/* screen4 */}
      {/* 질문 */}
      <div className="flex items-center justify-center font-lef-b text-[20px]">
        <img className="h-[20px] w-[20px] " src={eyes} alt="eyes" />
        관심있는 고도화 계획이 있나요?
      </div>
      {/* 고도화 기술 입력창 */}
      <div className="relative mt-8 h-355" data-te-input-wrapper-init>
        <input
          type="text"
          maxLength={20}
          className=" ml-9  mb-7 flex w-10/12 justify-center border-b-2 focus:outline-none sm:ml-12"
          placeholder="예시 - 캐싱 (최대 5개 작성 가능)"
          value={firstPlan}
          onChange={handleFirstInputChange}
        />
        {planObject.map((x) => (
          <input
            key={x.id}
            maxLength={20}
            type="text"
            className="ml-9 mb-7 flex w-10/12 justify-center border-b-2 focus:outline-none sm:ml-12"
            placeholder="고도화 계획을 입력해주세요"
            value={x.value}
            onChange={(e) => handleInputChange(x.id, e.target.value)}
          />
        ))}
        {planObject.length < 4 ? (
          <button
            className="ml-48 mt-5 flex h-5 w-5 items-center justify-center sm:ml-[297px]"
            onClick={() => Plus()}
            type="button"
          >
            <img src={plus} alt="plus" />
          </button>
        ) : null}
      </div>
      {/* 하단버튼 */}
      <button
        className="fixed ml-7
          items-center justify-center  rounded-[16px] bg-gptbutton
        px-32 py-1 pt-3 pb-3 font-lef-b text-slate-50 hover:bg-button sm:ml-14  sm:px-52"
        type="submit"
        onClick={() => toSubmit()}
      >
        AI 고도화 추천
      </button>
    </div>
  );
};

type ModalDefaultType = {
  onClickToggleModal: () => void;
};

function GptModal({ onClickToggleModal }: PropsWithChildren<ModalDefaultType>) {
  const [currentScreen, setCurrentScreen] = useState<number>(1);

  const handleNext = () => {
    setCurrentScreen(currentScreen + 1);
  };

  const handlePrev = () => {
    setCurrentScreen(currentScreen - 1);
  };

  function renderScreen() {
    switch (currentScreen) {
      case 1:
        return <Screen1 onNext={handleNext} />;
      case 2:
        return <Screen2 onPrev={handlePrev} onNext={handleNext} />;
      case 3:
        return <Screen3 onPrev={handlePrev} onNext={handleNext} />;
      case 4:
        return (
          <Screen4
            onPrev={handlePrev}
            onClickToggleModal={onClickToggleModal}
          />
        );
      default:
        return null;
    }
  }

  return (
    <div>
      <div>{renderScreen()}</div>
      {/* 모달 영역 외의 배경을 클릭하면 모달이 닫히게 하는 컨테이너, 이벤트핸들러를 사용하여 클릭 이벤트 발생 시 onClickToggleModal 함수 호출하여 모달 닫음 */}
      <button
        aria-label="Toggle modal"
        className="fixed top-0 left-0 right-0 bottom-0 z-40 h-full w-screen  bg-black opacity-70"
        onClick={(e: React.MouseEvent) => {
          e.preventDefault();

          if (onClickToggleModal) {
            onClickToggleModal();
          }
        }}
        type="button"
      />
    </div>
  );
}

export default GptModal;
