import axios from 'axios';
import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { useRecoilValue } from 'recoil';
import { v4 as uuidv4 } from 'uuid';

import WriteIcon from '../assets/image/pencil-square.svg';
import ProfileIcon from '../assets/image/profileIcon.svg';
import Banner from '../components/Banner';
import NavBar from '../components/NavBar';
import { persistTokenState } from '../Recoil';

type DataObject = {
  nickname: string;
  email: string;
};

function SearchUserPage() {
  const [data, setData] = useState<DataObject[]>([]); // 데이터를 담을 state 선언
  const params = useParams(); // react-router-dom useParams 사용 선언
  const accessToken = sessionStorage.getItem('accessToken');
  const persistToken = useRecoilValue(persistTokenState);
  const [hoveredEmail, setHoveredEmail] = useState('');

  function handleMouseEnter(email: string) {
    setHoveredEmail(email);
  }

  function handleMouseLeave() {
    setHoveredEmail('');
  }

  const navigate = useNavigate(); // react-router-dom useNavigate 사용 선언

  function toWrite() {
    // react-router-dom을 이용한 글쓰기 페이지로 이동 함수
    navigate('/write');
  }

  async function getData() {
    try {
      const res = await axios.get('http://localhost:8080/api/v1/members', {
        params: { nickname: params.userName },
        headers: {
          Authorization: `Bearer ${accessToken || persistToken}`,
        },
      });
      if (res.data.data.length === 0) {
        setData([{ nickname: '검색 결과가 없습니다.', email: '' }]);
      } else {
        setData(res.data.data);
      }
    } catch (error) {
      console.log(error);
      alert('검색 결과가 없습니다.');
      navigate('/');
    }
  }

  useEffect(() => {
    getData();
  }, [params]);

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
          aria-label="toWritePage"
          type="button"
        >
          <img className="mr-2 h-5 w-5" src={WriteIcon} alt="WriteIcon" />
          <span className="shrink-0 font-semibold">프로젝트 공유</span>
        </button>

        <div className="mx-10 border-b-2 border-b-neutral-300 pt-0 font-ng-b text-2xl sm:mx-28 sm:mb-5 sm:pt-5">
          {/* All */}
        </div>
        <div>
          <div className="ml-0 flex flex-wrap justify-center min-[680px]:ml-10 min-[680px]:justify-start">
            {data.map((item) => (
              <div key={uuidv4()}>
                {item.nickname === '검색 결과가 없습니다.' ? (
                  <div className="mx-3 mt-9 flex min-[680px]:mx-0 min-[680px]:ml-16 ">
                    <span>{item.nickname}</span>
                  </div>
                ) : (
                  <div
                    className="mx-3 mt-9 flex min-[680px]:mx-0 min-[680px]:ml-16 "
                    onMouseEnter={() => handleMouseEnter(item.email)}
                    onMouseLeave={handleMouseLeave}
                  >
                    <img
                      className="mr-2 h-6 w-6"
                      src={ProfileIcon}
                      alt="ProfileIcon"
                    />
                    <span className="relative">
                      {item.nickname}
                      {hoveredEmail === item.email && (
                        <span className="absolute -top-5 -left-0.5 bg-gray-200 px-1 text-xs">
                          {item.email}
                        </span>
                      )}
                    </span>
                  </div>
                )}
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}

export default SearchUserPage;
