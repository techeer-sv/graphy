import { useNavigate } from 'react-router-dom';
import { useRecoilState } from 'recoil';

import project from '../../assets/image/project.png';
import { projectIdState } from '../../Recoil';
import AllStacks from '../../Stack';

type ProjectCardProps = {
  items: {
    id: number;
    createdAt: string;
    projectName: string;
    description: string;
    techTags: string[];
    thumbNail: string;
  };
};

function ProjectCard({ items }: ProjectCardProps) {
  const [, setProjectId] = useRecoilState(projectIdState);
  const navigate = useNavigate();

  function findImage(tag: string) {
    return AllStacks.map((x) => x.image)[
      AllStacks.map((x) => x.name).findIndex((x) => x === tag)
    ];
  }

  function toRead() {
    const urlToCheck = `http://localhost:8080/api/v1/projects/${items.id}`;

    if (
      !navigator.onLine &&
      'serviceWorker' in navigator &&
      navigator.serviceWorker.controller
    ) {
      const messageChannel = new MessageChannel();

      messageChannel.port1.onmessage = (event) => {
        if (event.data.hasMatch) {
          navigate(`/read/${items.id}`);
          setProjectId(items.id);
        } else {
          alert('오프라인 상태입니다. 네트워크 연결을 확인해주세요.');
        }
      };

      Promise.resolve().then(() => {
        if (navigator.serviceWorker.controller) {
          navigator.serviceWorker.controller.postMessage(
            { action: 'cache-contains', url: urlToCheck },
            [messageChannel.port2],
          );
        }
      });
    } else {
      navigate(`/read/${items.id}`);
      setProjectId(items.id);
    }
  }

  return (
    <button
      onClick={() => toRead()}
      className="w-64 overflow-hidden rounded-md drop-shadow-md transition duration-150 hover:scale-105 hover:shadow-lg"
      aria-label="toReadPage"
      type="button"
    >
      <div className="flex flex-col justify-center rounded-t-lg border-b-2 bg-sky-100 text-center">
        {/* <div>우리 학교 동창회 서비스</div>
        <div>moyora</div> */}
        {items.thumbNail === '' ? (
          <img
            className="h-48 rounded-t-lg bg-sky-100 text-center"
            src={project}
            alt="프로젝트 이미지"
          />
        ) : (
          <img
            className="h-48 rounded-t-lg bg-sky-100 text-center"
            src={items.thumbNail}
            alt="프로젝트 이미지"
          />
        )}
      </div>
      <div className="flex h-28 flex-col rounded-b-lg bg-white">
        <p className="my-auto mt-2 ml-4 w-56 truncate text-left font-ng-eb text-xl">
          {items.projectName}
        </p>
        <p className="my-auto ml-4 w-56 truncate text-left font-ng-b">
          {items.description}
        </p>
        <div className="ml-3 mb-2 mt-2 flex flex-row font-ng">
          {items.techTags.map((x: string) => (
            <div key={x}>
              <img className="mr-2 h-8 w-8" src={findImage(x)} alt="stack" />
            </div>
          ))}
        </div>
      </div>
    </button>
  );
}

export default ProjectCard;
