import React from 'react';

import NavBar from '../components/NavBar';
import Banner from '../components/Banner';
import ProjectCard from '../components/ProjectCard';
import WriteIcon from '../assets/pencil-square.svg';

const MainPage = () => {
  return (
    <div className="relative h-screen w-screen bg-gray-50">
      <NavBar />
      <div>
        <Banner />
        <button
          className="fixed bottom-10 right-10 z-10 my-auto mb-2 flex shrink-0 flex-row items-center rounded-full
          bg-graphyblue px-4 py-1 pt-3 pb-3 font-semibold text-slate-50 drop-shadow-md
          sm:invisible"
        >
          <img className="mr-2 h-[20px] w-[20px]" src={WriteIcon} />
          <span className="font-semibold">프로젝트 공유</span>
        </button>
        <div className="ml-10 mb-5 pt-20 text-2xl font-medium">All</div>
        <div className="ml-8 flex w-11/12 flex-wrap justify-center gap-10">
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
