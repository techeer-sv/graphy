import React from "react";

import TopBar from "../components/MainPage/TopBar";
import Banner from "../components/MainPage/Banner";
import Card from "../components/MainPage/Card";


const MainPage = () => {
  //return <div className="text-blue-600">메인페이지 입니다</div>;
  return(
    <div>
      <TopBar/>
      <Banner/>
      <Card/>
    </div>);
};

export default MainPage;