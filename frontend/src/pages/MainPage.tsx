import React from 'react';

import NavBar from '../components/NavBar';
import Banner from '../components/Banner';
import ProjectCard from '../components/ProjectCard';

const MainPage = () => {
  return (
    <div className="bg-gray-50	">
      <NavBar />
      <Banner />
      <div className="text-2xl font-medium ml-10 mb-5">All</div>
      <div className="justify-center content-center">
        <div className="w-11/12 grid grid-cols-5 gap-10 ml-8">
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
