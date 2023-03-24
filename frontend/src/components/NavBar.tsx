import React from 'react';
import FaceIcon from '@mui/icons-material/Face';

const NavBar = () => {
  return (
    <div className="flex flex-row pt-3 pb-3 mb-5 w-screen align-middle content-center border-b ">
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
        placeholder=" search"
        className="w-[900px] h-[40px] mx-5 ml-15"
      />

      {/* 프로젝트 작성 버튼 */}
      <button className="ml-5 sm:ml-10 rounded-full bg-graphyblue text-slate-50 font-semibold px-4 py-1 shrink-0">
        프로젝트 공유
      </button>

      {/* 마이페이지 아이콘 */}
      <button className="w-[50px] h-[30px] mx-auto ml-7 mr-10">
        <FaceIcon />
      </button>
    </div>
  );
};

export default NavBar;
