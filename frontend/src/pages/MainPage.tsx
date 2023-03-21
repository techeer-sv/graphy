import React from 'react';

import TopBar from '../components/TopBar';
import Banner from '../components/Banner';
import ProjectCard from '../components/ProjectCard';

const MainPage = () => {
  return (
    <div>
      {/* <TopBar/> */}
      <Banner />
      <div className="justify-center content-center grid grid-cols-4 gap-10 mb-8">
        <ProjectCard />
        <ProjectCard />
        <ProjectCard />
        <ProjectCard />
        <ProjectCard />
        <ProjectCard />
        <ProjectCard />
        <ProjectCard />
        <ProjectCard />
        <ProjectCard />
        <ProjectCard />
        <ProjectCard />
        <ProjectCard />
        <ProjectCard />
        <ProjectCard />
        <ProjectCard />
      </div>
    </div>
  );
};

export default MainPage;
