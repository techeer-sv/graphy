import React from 'react';
import project from '../assets/image/project.png';

function ProjectCard() {
  return (
    <div className="rounded-md drop-shadow-md">
      <div className="flex h-[160px] w-[200px] flex-col justify-center rounded-t-lg bg-sky-100 text-center">
        {/* <div>우리 학교 동창회 서비스</div>
        <div>moyora</div> */}
        <img
          className="flex h-[160px] w-[200px] flex-col justify-center rounded-t-lg bg-sky-100 text-center"
          src={project}
          alt="프로젝트 이미지"
        />
      </div>

      <div className="flex h-[90px] w-[200px] justify-center rounded-b-lg bg-stone-50">
        <div className="grow">
          <h1 className="font-ng-eb text-lg">Title</h1>
          <p className="font-ng-b">explain</p>
          <div className="font-ng">
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
