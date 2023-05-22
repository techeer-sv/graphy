import NavBar from '../components/NavBar';
import Banner from '../components/Banner';
import ProjectCard from '../components/ProjectCard';
import WriteIcon from '../assets/image/pencil-square.svg';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { useEffect, useState } from 'react';
import { searchTextState } from '../Recoil';
import { useRecoilState } from 'recoil';

function MainPage() {
  const [data, setData] = useState<any[]>([]);
  const [searchText, setSearchText] = useRecoilState(searchTextState);

  const navigate = useNavigate(); // react-router-dom useNavigate 사용 선언

  function toWrite() {
    // react-router-dom을 이용한 글쓰기 페이지로 이동 함수
    navigate('/write');
  }

  async function getCard() {
    const url = 'http://localhost:8080/api/v1/projects/search';
    const params =
      searchText == ''
        ? {}
        : {
            projectName: searchText,
          };
    try {
      const res = await axios.get(url, { params });
      setData(res.data.data);
      console.log(res.data.data);
    } catch (error) {
      if (!navigator.onLine) {
        alert('오프라인 상태입니다. 네트워크 연결을 확인해주세요.');
        setSearchText('');
      }
      console.log(error);
    }
  }

  useEffect(() => {
    getCard(); //랜더링이 될 때 실행되는 함수
  }, [searchText]); //변수가 들어가있으면 변수가 바뀔 때마다 useEffect 안에 있는 함수를 실행시킴

  useEffect(() => {
    setData(data);
  }, [data]);

  return (
    <div className="relative h-auto min-h-screen w-screen bg-gray-50">
      <NavBar />
      <Banner />

      <div>
        {/* 프로젝트 공유 버튼 */}
        <button
          className="fixed bottom-10 right-10 z-10 my-auto mb-2 flex shrink-0 flex-row items-center rounded-full
          bg-graphyblue px-4 py-1 pt-3 pb-3 font-semibold text-slate-50 drop-shadow-md
          sm:invisible"
          onClick={() => toWrite()}
        >
          <img className="mr-2 h-5 w-5" src={WriteIcon} alt="WriteIcon" />
          <span className="shrink-0 font-semibold">프로젝트 공유</span>
        </button>

        <div className="mx-10 border-b-2 border-b-neutral-300 pt-0 font-ng-b text-2xl sm:mx-28 sm:mb-5 sm:pt-5">
          {/* All */}
        </div>

        {/* 프로젝트 카드 리스트 */}
        <div className="">
          {searchText == '' ? (
            <div className="relative mx-8 flex flex-wrap justify-center pt-6 sm:pt-8">
              {' '}
              {data.map((item) => (
                <div className="mx-8 mb-10" key={item.id}>
                  <ProjectCard items={item} />
                </div>
              ))}{' '}
            </div>
          ) : (
            <div className="ml-0 flex flex-wrap justify-center min-[680px]:ml-10 min-[680px]:justify-start">
              {' '}
              {data
                .filter((x) => x.projectName.includes(searchText))
                .map((item, num: number) => (
                  <div
                    className="mx-3 mt-9 min-[680px]:mx-0 min-[680px]:ml-16 "
                    key={item.id}
                  >
                    <ProjectCard items={item} />
                  </div>
                ))}{' '}
            </div>
          )}
        </div>
      </div>
    </div>
  );
}

export default MainPage;
