import { ChangeEvent, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useRecoilState } from 'recoil';

import WriteIcon from '../assets/image/pencil-square.svg';
import ProfileIcon from '../assets/image/ProfileIcon.svg';
import { persistTokenState, refreshState, searchTextState } from '../Recoil';

function NavBar() {
  const accessToken = sessionStorage.getItem('accessToken');
  const [refresh, setRefresh] = useRecoilState(refreshState);
  const [persistToken, setPersistToken] = useRecoilState(persistTokenState);
  const [searchText, SetSearchText] = useRecoilState(searchTextState);

  const navigate = useNavigate();

  function toNavigate(path: string) {
    navigate(path);
  }

  const getSearchData = (e: ChangeEvent<HTMLInputElement>) => {
    SetSearchText(e.target.value);
  };

  function signOut() {
    if (accessToken) {
      sessionStorage.removeItem('accessToken');
      setRefresh(!refresh);
    } else {
      setPersistToken(null);
    }
  }

  useEffect(() => {
    if (accessToken) {
      setRefresh(!refresh);
    }
  }, []);

  return (
    <div className="fixed z-20 mb-5 flex w-screen flex-row content-center overflow-hidden border-b border-zinc-400 bg-white pt-3 pb-3 align-middle font-ng-eb">
      {/* 로고 */}
      <button
        onClick={() => toNavigate('/')}
        className="ml-8 hidden font-lato-b text-4xl text-graphyblue sm:block"
        type="button"
      >
        Graphy
      </button>
      <button
        onClick={() => toNavigate('/')}
        className="ml-8 font-lato-b text-4xl text-graphyblue sm:hidden"
        type="button"
      >
        G
      </button>

      {/* 검색창 */}
      <input
        value={searchText}
        onChange={getSearchData}
        type="text"
        alt="search"
        placeholder="search"
        className=" mx-4 h-auto w-full appearance-none rounded-xl border pl-2 sm:w-full"
      />
      {accessToken || persistToken ? (
        <button
          className=" mr-4 whitespace-nowrap rounded-full bg-graphyblue px-4 text-white"
          type="button"
          onClick={() => signOut()}
        >
          로그아웃
        </button>
      ) : (
        <button
          className=" mr-4 whitespace-nowrap rounded-full bg-graphyblue px-4 text-white"
          type="button"
          onClick={() => toNavigate('/signin')}
        >
          로그인
        </button>
      )}

      {/* 프로젝트 작성 버튼 */}
      <button
        className="invisible mx-auto mr-4 flex h-0 w-0 shrink-0 flex-row flex-nowrap items-center rounded-full bg-graphyblue text-white sm:visible sm:mr-5
        sm:h-auto sm:w-auto sm:px-4 sm:py-1"
        onClick={() => toNavigate('/write')}
        aria-label="toWritePage"
        type="button"
      >
        <img className="mr-2 h-5 w-5" src={WriteIcon} alt="WriteIcon" />
        <span className="font-semibold">프로젝트 공유</span>
      </button>

      {/* 마이페이지 아이콘 */}
      <button className="mr-12" type="button" onClick={() => toNavigate('/my')}>
        <img
          className="fixed top-4 right-4 h-8 w-8 appearance-none"
          src={ProfileIcon}
          alt="ProfileIcon"
        />
      </button>
    </div>
  );
}

export default NavBar;
