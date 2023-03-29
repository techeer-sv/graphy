import React from 'react';
import ProfileIcon from "../assets/person-circle.svg"
import WriteIcon from "../assets/pencil-square.svg"


const NavBar = () => {
  return (
    <div className="flex flex-row fixed z-20 bg-white pt-3 pb-3 mb-5 w-screen align-middle content-center border-b border-zinc-400 ">
      {/* 로고 */}
      <span className="hidden ml-10 sm:block text-4xl font-bold text-graphyblue">
        Graphy
      </span>
      <span className="ml-10 sm:hidden text-4xl font-bold text-graphyblue">
        G
      </span>

      {/* 검색창 */}
      <input
        type="text"
        placeholder="  search"
        className="w-[950px] h-[40px] mx-5 rounded-xl border appearance-none"
      />

      {/* 프로젝트 작성 버튼 */}
      <button className="hidden flex flex-row flex-nowrap ml-1 mr-5 sm:ml-1 sm:block rounded-full bg-graphyblue text-slate-50 font-semibold
      px-10 py-1 shrink-0">
      <img
        className="w-[20px] h-[20px]"
        src={WriteIcon}/>
        프로젝트 공유
      </button>

      {/* 마이페이지 아이콘 */}
      <button className="mr-10">
        <img
        className="w-[30px] h-[30px] appearance-none"
        src={ProfileIcon}
        alt="" />
      </button>
    </div>
  );
};

export default NavBar;
