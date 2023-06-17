import { useInfiniteQuery } from '@tanstack/react-query';
import axios from 'axios';
import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useRecoilValue } from 'recoil';

import WriteIcon from '../assets/image/pencil-square.svg';
import Banner from '../components/Banner';
import NavBar from '../components/NavBar';
import ProjectCard from '../components/ProjectCard';
import { searchTextState } from '../Recoil';

interface DataObject {
  id: number;
  createdAt: string;
  projectName: string;
  description: string;
  techTags: string[];
  thumbNail: string;
}

function MainPage() {
  const searchText = useRecoilValue(searchTextState);

  const navigate = useNavigate(); // react-router-dom useNavigate 사용 선언

  function toWrite() {
    // react-router-dom을 이용한 글쓰기 페이지로 이동 함수
    navigate('/write');
  }

  async function getData({ pageParam = 1 }) {
    const params = searchText
      ? { projectName: searchText }
      : { page: pageParam, size: 12 };
    const res = await axios.get(
      'http://localhost:8080/api/v1/projects/search',
      {
        params,
      },
    );
    console.log(res.data);
    return res.data.data;
  }

  const { data, fetchNextPage, hasNextPage, isFetchingNextPage, status } =
    useInfiniteQuery(['projects'], getData, {
      getNextPageParam: (lastPage, pages) =>
        lastPage.length < 12 ? undefined : pages.length + 1,
    });

  // Trigger the next page load when the user scrolls to the bottom of the page.
  useEffect(() => {
    if (!isFetchingNextPage) {
      const handleScroll = () => {
        if (
          document.documentElement.scrollHeight -
            document.documentElement.scrollTop ===
          document.documentElement.clientHeight
        ) {
          // eslint-disable-next-line consistent-return
          fetchNextPage();
        }
      };
      window.addEventListener('scroll', handleScroll);
    }
  }, [fetchNextPage, isFetchingNextPage]);

  if (status === 'loading') {
    return <span>Loading...</span>;
  }

  if (status === 'error') {
    return <span>Error fetching data</span>;
  }

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
          {searchText === '' ? (
            <>
              {data.pages.map((group, i) => (
                <div
                  className="relative mx-8 flex flex-wrap justify-center pt-6 sm:pt-8"
                  key={group[i]?.id}
                >
                  {group.map((item: any) => (
                    <div className="mx-8 mb-10" key={item.id}>
                      <ProjectCard items={item} />
                    </div>
                  ))}
                </div>
              ))}
              {hasNextPage && isFetchingNextPage && (
                <span>Loading more...</span>
              )}
            </>
          ) : (
            <div className="ml-0 flex flex-wrap justify-center min-[680px]:ml-10 min-[680px]:justify-start">
              {data.pages
                .flat()
                .filter((x) => x.projectName.includes(searchText))
                .map((item) => (
                  <div
                    className="mx-3 mt-9 min-[680px]:mx-0 min-[680px]:ml-16"
                    key={item.id}
                  >
                    <ProjectCard items={item} />
                  </div>
                ))}
            </div>
          )}
        </div>
      </div>
    </div>
  );
}

export default MainPage;
