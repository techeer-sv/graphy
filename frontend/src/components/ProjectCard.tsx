import React from 'react';
import project from '../assets/image/project.png';
import AllStacks from '../Stack'

function ProjectCard(items:any) {
  console.log(items.items.projectName);

function findImage(tag:string) {
  //AllStacks.map(x => x.name).findIndex(x => x == tag)
  //AllStacks.map(x => x.image)[AllStacks.map(x => x.name).findIndex(x => x == tag)]
  return AllStacks.map(x => x.image)[AllStacks.map(x => x.name).findIndex(x => x == tag)];
}

  return (
    <div className="rounded-md drop-shadow-md">
      <div className="flex h-[160px] w-[200px] flex-col justify-center rounded-t-lg bg-sky-100 text-center">
        {/* <div>우리 학교 동창회 서비스</div>
        <div>moyora</div> */}
        <img
          className="flex h-[160px] w-[200px] flex-col justify-center rounded-t-lg bg-sky-100 text-center"
          src={items.items.thumbNail}
          alt="프로젝트 이미지"
        />
      </div>

      <div className="flex h-[90px] w-[200px] justify-center rounded-b-lg bg-stone-50">
        <div className="grow">
          <h1 className="font-ng-eb text-lg">{items.items.projectName}</h1>
          <p className="font-ng-b">{items.items.description}</p>
          <div className="font-ng ">
          {items.items.techTags.map((x:string, y:number)=> <img className='mx-3 my-1 h-8 w-8' src={findImage(x)} key={y} />)}
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProjectCard;
