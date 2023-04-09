import React, { ChangeEvent, useState } from 'react';
import ProfileIcon from '../assets/image/person-circle.svg';
import WriteIcon from '../assets/image/pencil-square.svg';
import { useNavigate } from 'react-router';

const NavBar = () => {
  const [searchText, SetSearchText] = useState<string>('');
  const getSearchData = (e: ChangeEvent<HTMLInputElement>) => {
    SetSearchText(e.target.value.toLowerCase());
    }

  const navigate = useNavigate(); // react-router-dom useNavigate 사용 선언

  function toWrite() {
    // react-router-dom을 이용한 글쓰기 페이지로 이동 함수
    navigate('/write');
  }

  function toMain() {
    // react-router-dom을 이용한 글쓰기 페이지로 이동 함수
    navigate('/');
  }


  return (
    <div className="fixed z-20 mb-5 flex w-screen flex-row content-center overflow-hidden border-b border-zinc-400 bg-white pt-3 pb-3 align-middle font-ng-eb">
      {/* 로고 */}
      <button onClick={() => toMain()} className="ml-10 hidden font-ng-eb text-4xl text-graphyblue sm:block">
        Graphy
      </button>
      <button onClick={() => toMain()} className="ml-10 font-ng-eb text-4xl text-graphyblue sm:hidden">
        G
      </button>

      {/* 검색창 */}
      <input
        value={searchText}
        onChange={getSearchData}
        type="text"
        placeholder="  search"
        className=" mx-5 h-[40px] w-[950px] appearance-none rounded-xl border"
      />

      {/* 프로젝트 작성 버튼 */}
      <button
        className="invisible flex h-0 w-0 shrink-0 flex-row flex-nowrap items-center rounded-full bg-graphyblue text-slate-50 sm:visible sm:mr-5 sm:ml-1
      sm:h-auto sm:w-auto sm:px-4 sm:py-1"
        onClick={() => toWrite()}
      >
        <img className="mr-2 h-[20px] w-[20px]" src={WriteIcon} />
        <span className="font-semibold">프로젝트 공유</span>
      </button>

      {/* 마이페이지 아이콘 */}
      <button className="mr-12">
        <img
          className="fixed top-4 right-4 h-[30px] w-[30px] appearance-none"
          src={ProfileIcon}
          alt=""
        />
      </button>
    </div>
  );
};

export default NavBar;
