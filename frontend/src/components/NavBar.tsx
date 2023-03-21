import React from 'react';
import FaceIcon from '@mui/icons-material/Face';

const NavBar = () => {
  return (
    <div className="flex-row">
      {/* 로고 */}
      <div className="text-4xl font-bold text-blue-800 ">Grapy</div>

      {/* 검색창 */}
      <input type="text" placeholder="search" />
    </div>
  );
};

export default NavBar;
