import React from 'react';

// import TopBar from '../components/TopBar';
import Banner from '../components/Banner';
import ProjectCard from '../components/ProjectCard';

const MainPage = () => {
  return (
    <div>
      {/* <TopBar/> */}
      <Banner />
      <div className="justify-center content-center">
        <div className="w-full grid grid-cols-4 gap-10">
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
    </div>
  );
};

export default MainPage;
