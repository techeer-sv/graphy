import NavBar from '../components/NavBar';
import Banner from '../components/Banner';
import ProjectCard from '../components/ProjectCard';
import WriteIcon from '../assets/image/pencil-square.svg';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { useEffect, useState } from 'react';
import { searchTextState } from '../Recoil';
import { useRecoilValue } from 'recoil';

function MainPage() {
  const [data, setData] = useState<any[]>([]);
  const searchText = useRecoilValue(searchTextState);

  const navigate = useNavigate(); // react-router-dom useNavigate 사용 선언

  function toWrite() {
    // react-router-dom을 이용한 글쓰기 페이지로 이동 함수
    navigate('/write');
  }

  async function postCard() {
    const url = 'http://localhost:8080/api/v1/projects/search';
    const params = {
      projectName: searchText,
    };
    try {
      const res = await axios.post(url, null, { params });
      setData(res.data.data);
      console.log(res.data.data);
    } catch (error) {
      console.log(error);
      alert('프로젝트 조회 실패');
    }
  }

  useEffect(() => {
    postCard(); //랜더링이 될 때 실행되는 함수
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
          <img className="mr-2 h-5 w-5" src={WriteIcon} />
          <span className="shrink-0 font-semibold">프로젝트 공유</span>
        </button>

        {/* 카테고리 */}
        {/* <div className="ml-10 mb-5 pt-20 font-ng-b text-2xl">All</div> */}

        {/* 프로젝트 카드 리스트 */}
        <div className="relative mx-4 flex flex-wrap justify-center">
          {searchText == ''
            ? data.map((item) => (
                <div className="mx-8 mb-10" key={item.id}>
                  <ProjectCard items={item} />
                </div>
              ))
            : data
              .filter((x) => x.projectName.includes(searchText))
                .map((item, num: number) => (
                  <div className=" mx-auto ml-16">
                    <ProjectCard key={num} items={item} />
                  </div>
                ))}
        </div>
      </div>
    </div>
  );
}

export default MainPage;
