import React from 'react';
import ProfileIcon from '../assets/person-circle.svg';
import WriteIcon from '../assets/pencil-square.svg';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { useNavigate } from 'react-router';
import WritingPage from '../pages/WritingPage';

function NavBar() {
  const navigate = useNavigate();

  const goToWriting = () => {
    navigate("/WritingPage");
  };

  return (
    <div className="fixed z-20 mb-5 flex w-screen flex-row content-center border-b border-zinc-400 bg-white pt-3 pb-3 align-middle ">
      {/* 로고 */}
      <button className="ml-10 hidden text-4xl font-bold text-graphyblue sm:block">
        Graphy
      </button>
      <span className="ml-10 text-4xl font-bold text-graphyblue sm:hidden">
        G
      </span>

      {/* 검색창 */}
      <input
        type="text"
        placeholder="  search"
        className=" mx-5 h-[40px] w-[950px] appearance-none rounded-xl border"
      />

      {/* 프로젝트 작성 버튼 */}
      <button onClick={goToWriting}
        className="invisible sm:ml-1 sm:mr-5 h-0 w-0 sm:h-auto sm:w-auto flex shrink-0 flex-row flex-nowrap items-center rounded-full bg-graphyblue sm:px-4
      sm:py-1 text-slate-50 sm:visible sm:ml-1"
      >
        <img className="mr-2 h-[20px] w-[20px]" src={WriteIcon} />
        <span className="font-semibold">프로젝트 공유</span>
      </button>

      {/* 마이페이지 아이콘 */}
      <button className="mr-12">
        <img
        className="w-[30px] h-[30px] appearance-none fixed top-4 right-4"
        src={ProfileIcon}
        alt="" />
      </button>
    </div>
  );
};

export default NavBar;
