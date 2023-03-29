import React from 'react';

import NavBar from '../components/NavBar';
import Banner from '../components/Banner';
import ProjectCard from '../components/ProjectCard';

const MainPage = () => {
  return (
    <div className="bg-gray-50 w-screen h-screen relative">
      <NavBar />
      <div>
        <Banner />
        <button
          className="fixed bottom-10 right-10 z-10 sm:hidden shrink-0 my-auto mb-2
          pt-3 pb-3 rounded-full bg-graphyblue text-slate-50 font-semibold px-4 py-1
          drop-shadow-md
          "
        >
          프로젝트 공유
        </button>
        <div className="text-2xl font-medium ml-10 mb-5 pt-20">All</div>
        <div className="w-11/12 flex flex-wrap justify-center gap-10 ml-8">
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
