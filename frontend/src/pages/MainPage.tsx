import React from "react";

import TopBar from "../components/MainPage/TopBar";
import Banner from "../components/MainPage/Banner";
import ProjectCard from "../components/MainPage/ProjectCard";


const MainPage = () => {
  //return <div className="text-blue-600">메인페이지 입니다</div>;
  return(
    <div>
      <TopBar/>
      <Banner/>
      <div className="justify-center content-center grid grid-cols-4 gap-4 mb-8">
        <ProjectCard/>
        <ProjectCard/>
        <ProjectCard/>
        <ProjectCard/>
        <ProjectCard/>
        <ProjectCard/>
        <ProjectCard/>
        <ProjectCard/>
      </div>
    </div>);
};

export default MainPage;