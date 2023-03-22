import React from 'react';
import FaceIcon from '@mui/icons-material/Face';

const NavBar = () => {
  return (
    <div className="flex-row mt-5 mb-5 ">
      {/* 로고 */}
      <span className="ml-6 text-4xl font-bold text-blue-800">Grapy</span>

      {/* 검색창 */}
      <input
        type="text"
        placeholder=" search"
        className="w-[800px] h-[30px] mx-5"
      />

      {/* 프로젝트 작성 버튼 */}
      <button className="rounded-full bg-blue-800 text-slate-50 font-semibold px-4 py-1">
        프로젝트 공유
      </button>

      {/* 마이페이지 아이콘 */}
      <button className="w-[50px] h-[30px] ">
        <FaceIcon />
      </button>
    </div>
  );
};

export default NavBar;
