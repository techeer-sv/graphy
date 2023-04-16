import React from 'react';
import project from '../assets/image/project.png';
import AllStacks from '../Stack';
import { useNavigate } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { useRecoilState } from 'recoil';
import { projectIdState } from '../Recoil';

function ProjectCard(items: any) {
  console.log(items.items.projectName);
  const [projectId, setProjectId] = useRecoilState(projectIdState);

  function findImage(tag: string) {
    //AllStacks.map(x => x.name).findIndex(x => x == tag)
    //AllStacks.map(x => x.image)[AllStacks.map(x => x.name).findIndex(x => x == tag)]
    return AllStacks.map((x) => x.image)[
      AllStacks.map((x) => x.name).findIndex((x) => x == tag)
    ];
  }
  const navigate = useNavigate();

  function toRead() {
    navigate('/read');
    setProjectId(items.items.id);
  }
  return (
    <button onClick={() => toRead()} className=" rounded-md drop-shadow-md">
      <div className="flex h-[180px] w-[260px] flex-col justify-center rounded-t-lg bg-sky-100 text-center">
        {/* <div>우리 학교 동창회 서비스</div>
        <div>moyora</div> */}
        {items.items.thumbNail === '' ? (
          <img
            className="flex h-[180px] w-[260px] flex-col justify-center rounded-t-lg bg-sky-100 text-center"
            src={project}
            alt="프로젝트 이미지"
          />
        ) : (
          <img
            className="flex h-[180px] w-[260px] flex-col justify-center rounded-t-lg bg-sky-100 text-center"
            src={items.items.thumbNail}
            alt="프로젝트 이미지"
          />
        )}
      </div>

      <div className="flex h-[120px] w-[260px] flex-col rounded-b-lg bg-stone-50">
        <p className="my-auto mx-auto mt-2 ml-4 font-ng-eb text-lg">
          {items.items.projectName}
        </p>
        <p className="mx-auto my-auto ml-4 font-ng-b">
          {items.items.description}
        </p>
        <div className="ml-3 mb-2 mt-2   flex flex-row font-ng">
          {items.items.techTags.map((x: string, y: number) => (
            <img className=" mr-2 h-8 w-8 " src={findImage(x)} key={y} />
          ))}
        </div>
      </div>
    </button>
  );
}

export default ProjectCard;
