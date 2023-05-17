import project from '../assets/image/project.png';
import AllStacks from '../Stack';
import { useNavigate } from 'react-router-dom';
import { useRecoilState } from 'recoil';
import { projectIdState } from '../Recoil';

function ProjectCard(items: any) {
  const [projectId, setProjectId] = useRecoilState(projectIdState);

  function findImage(tag: string) {
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
    <button
      onClick={() => toRead()}
      className="w-64 overflow-hidden rounded-md drop-shadow-md transition duration-150 hover:scale-105 hover:shadow-lg"
    >
      <div className="flex flex-col justify-center rounded-t-lg border-b-2 bg-sky-100 text-center">
        {/* <div>우리 학교 동창회 서비스</div>
        <div>moyora</div> */}
        {items.items.thumbNail === '' ? (
          <img
            className="h-48 rounded-t-lg bg-sky-100 text-center"
            src={project}
            alt="프로젝트 이미지"
          />
        ) : (
          <img
            className="h-48 rounded-t-lg bg-sky-100 text-center"
            src={items.items.thumbNail}
            alt="프로젝트 이미지"
          />
        )}
      </div>
      <div className="flex h-28 flex-col rounded-b-lg bg-white">
        <p className="my-auto mt-2 ml-4 w-56 truncate text-left font-ng-eb text-xl">
          {items.items.projectName}
        </p>
        <p className="my-auto ml-4 w-56 truncate text-left font-ng-b">
          {items.items.description}
        </p>
        <div className="ml-3 mb-2 mt-2 flex flex-row font-ng">
          {items.items.techTags.map((x: string, y: number) => (
            <img className=" mr-2 h-8 w-8 " src={findImage(x)} key={y} />
          ))}
        </div>
      </div>
    </button>
  );
}

export default ProjectCard;
