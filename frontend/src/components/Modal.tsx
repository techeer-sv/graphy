import React, { PropsWithChildren } from 'react';
import arrowLeftIcon from '../assets/image/arrow-left.svg';
import arrowRightIcon from '../assets/image/arrow-right.svg';
import monster from '../assets/image/monster.png';
import desktop from '../assets/image/desktop.png';
import pick from '../assets/image/pick.png';
import eyes from '../assets/image/eyes.png';
import plus from '../assets/image/plus-circle.svg';
import Android_studio from '../assets/image/stacklogo/Android_studio.svg';
import Apache_cassandra from '../assets/image/stacklogo/Apache_cassandra.svg';
import Apache_kafka from '../assets/image/stacklogo/Apache_kafka.svg';
import Apache_spark from '../assets/image/stacklogo/Apache_spark.svg';
import Aws from '../assets/image/stacklogo/AWS.svg';
import Clang from '../assets/image/stacklogo/Clang.svg';
import Cpp from '../assets/image/stacklogo/Cpp.svg';
import CSharp from '../assets/image/stacklogo/CSharp.svg';
import Django from '../assets/image/stacklogo/Django.svg';
import Docker from '../assets/image/stacklogo/Docker.svg';
import Express from '../assets/image/stacklogo/Express.svg';
import Fastapi from '../assets/image/stacklogo/Fastapi.svg';
import Firebase from '../assets/image/stacklogo/Firebase.svg';
import Flask from '../assets/image/stacklogo/Flask.svg';
import Flutter from '../assets/image/stacklogo/Flutter.svg';
import Github_actions from '../assets/image/stacklogo/Github_actions.svg';
import Go from '../assets/image/stacklogo/Go.svg';
import GraphQL from '../assets/image/stacklogo/GraphQL.svg';
import Java from '../assets/image/stacklogo/Java.svg';
import JavaScript from '../assets/image/stacklogo/JavaScript.svg';
import Jenkins from '../assets/image/stacklogo/Jenkins.svg';
import Julia from '../assets/image/stacklogo/Julia.svg';
import Kotlin from '../assets/image/stacklogo/Kotlin.svg';
import Kubernetes from '../assets/image/stacklogo/Kubernetes.svg';
import Mongodb from '../assets/image/stacklogo/Mongodb.svg';
import Mysql from '../assets/image/stacklogo/Mysql.svg';
import Nextjs from '../assets/image/stacklogo/Nextjs.svg';
import Nodejs from '../assets/image/stacklogo/Nodejs.svg';
import Nestjs from '../assets/image/stacklogo/Nestjs.svg';
import Php from '../assets/image/stacklogo/Php.svg';
import Python from '../assets/image/stacklogo/Python.svg';
import PyTorch from '../assets/image/stacklogo/PyTorch.svg';
import react from '../assets/image/stacklogo/React.svg';
import React_native from '../assets/image/stacklogo/React_native.svg';
import Redux from '../assets/image/stacklogo/Redux.svg';
import Redis from '../assets/image/stacklogo/Redis.svg';
import Rust from '../assets/image/stacklogo/Rust.svg';
import Spring from '../assets/image/stacklogo/Spring.svg';
import Svelte from '../assets/image/stacklogo/Svelte.svg';
import Swift from '../assets/image/stacklogo/Swift.svg';
import TensorFlow from '../assets/image/stacklogo/TensorFlow.svg';
import Typescript from '../assets/image/stacklogo/Typescript.svg';
import Unity from '../assets/image/stacklogo/Unity.svg';
import Vue from '../assets/image/stacklogo/Vue.svg';

//onClickToggleModal이라는 함수 타입을 정의한 인터페이스
interface ModalDefaultType {
  onClickToggleModal: () => void;
}

function Modal({
  onClickToggleModal, //모달을 닫기 위한 함수, 클릭 이벤트 발생 시 실행
  children, //모달 내부에 표시될 내용
}: PropsWithChildren<ModalDefaultType>) {
  return (
    <>
      {/* 실제 모달을 나타내는 컨테이너 */}
      <div
        className="-translate-y-1/2-translate-y-1/2 fixed top-20 right-1/2 z-50 box-border h-[620px]
        w-[720px] translate-x-1/2 transform 
        rounded-[30px] bg-white"
      >
        {/* 이전/다음 */}
        <div className="mt-5">
          <div className="ml-5 flex font-lef text-gray-400 ">
            <button className="flex w-56 items-center">
              <img className="mr-2" src={arrowLeftIcon} />
              이전
            </button>

            <button className="ml-[550px] flex w-56 items-center">
              <div>다음</div>
              <img className="ml-2" src={arrowRightIcon} />
            </button>
          </div>

          {/* 단계 */}
          <ol className="ml-52 mb-8 flex w-80 items-center justify-center">
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
          <div className="mt-8 flex justify-center">
            <button className="mr-2 flex items-center rounded-full border-[1.5px] border-zinc-300 px-3 py-1 text-gray-500">
              <img className="mr-1 h-[20px] w-[20px]" src={react} />
              React
            </button>
            <button className="mr-2 flex items-center rounded-full border-[1.5px] border-zinc-300 px-3 py-1 text-gray-500">
              <img className="mr-1 h-[20px] w-[20px]" src={Spring} />
              Spring
            </button>
            <button className="mr-2 flex items-center rounded-full border-[1.5px] border-zinc-300 px-3 py-1 text-gray-500">
              <img className="mr-1 h-[20px] w-[20px]" src={Redux} />
              Redux
            </button>
            <button className="flex items-center rounded-full border-[1.5px] border-zinc-300 px-3 py-1 text-gray-500">
              <img className="mr-1 h-[20px] w-[20px]" src={Android_studio} />
              AndroidStudio
            </button>
            <button className="mr-2 flex items-center rounded-full border-[1.5px] border-zinc-300 px-3 py-1 text-gray-500">
              <img className="mr-1 h-[20px] w-[20px]" src={Firebase} />
              Firebase
            </button>
            <button className="flex items-center rounded-full border-[1.5px] border-zinc-300 px-3 py-1 text-gray-500">
              <img className="mr-1 h-[20px] w-[20px]" src={Aws} />
              Aws
            </button>
          </div>
          {/* 하단 버튼 */}
          <button
            className="mt-60 ml-24 flex items-center
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
        className="fixed top-0 left-0 right-0 bottom-0 z-40 h-full w-screen  bg-black opacity-70"
        onClick={(e: React.MouseEvent) => {
          e.preventDefault();

          if (onClickToggleModal) {
            onClickToggleModal();
          }
        }}
      />
    </>
  );
}

export default Modal;
