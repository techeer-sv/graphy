import React from 'react';
import FaceIcon from '@mui/icons-material/Face';

const NavBar = () => {
  return (
    <div className="flex-row">
      {/* 로고 */}
      <div className="text-4xl font-bold text-blue-800 ">Grapy</div>

      {/* 검색창 */}
      <input type="text" placeholder="search" />

      {/* 프로젝트 작성 버튼 */}
      <button className="rounded-full bg-blue-800 text-slate-50 font-semibold px-4 py-1">
        프로젝트 공유
      </button>
    </div>
  );
};

export default NavBar;
