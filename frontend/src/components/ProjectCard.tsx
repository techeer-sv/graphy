import React from 'react';
import project from '../assets/image/project.png';

function ProjectCard() {
  return (
    <div className="rounded-md drop-shadow-md">
      <div className="flex flex-col justify-center text-center w-[200px] h-[160px] bg-sky-100 rounded-t-lg">
        <img
          className="flex flex-col justify-center text-center w-[200px] h-[160px] bg-sky-100 rounded-t-lg"
          src={project}
          alt="프로젝트 이미지"
        />
      </div>

      <div className="flex justify-center w-[200px] h-[90px] bg-stone-50 rounded-b-lg">
        <div className="grow">
          <h1 className="font-bold text-lg">Title</h1>
          <p className="font-semibold">explain</p>
          <div>
            <span>#Spring</span>
            <span>#React</span>
            <span>#Typescript</span>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProjectCard;
