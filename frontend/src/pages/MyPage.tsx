import axios from 'axios';
import { useEffect, useState, useCallback } from 'react';
import { useNavigate } from 'react-router-dom';
import { useRecoilState, useRecoilValue } from 'recoil';

import Like from '../assets/image/Like.svg';
import myProfile from '../assets/image/myProfile.png';
import WriteIcon from '../assets/image/pencil-square.svg';
import FollowModal from '../components/FollowModal';
import NavBar from '../components/NavBar';
import PostModal from '../components/PostModal';
import {
  refreshState,
  persistTokenState,
  nicknameState,
  // projectDataState,
} from '../Recoil';

// interface ReadReplyObject {
//   commentId: number;
//   childCount: number;
//   content: string;
//   createdAt: string;
// }

function MyPage() {
  const refresh = useRecoilValue(refreshState);
  const accessToken = sessionStorage.getItem('accessToken');
  const persistToken = useRecoilValue(persistTokenState);

  const [nickname, setNickname] = useRecoilState(nicknameState);
  const [introduction, setIntroduction] = useState<string>('');
  const [followerCount, setFollowerCount] = useState<number>(0);
  const [followingCount, setFollowingCount] = useState<number>(0);
  // const [projectData, setProjectData] = useRecoilState(projectDataState);

  const [isOpenModal, setOpenModal] = useState(false);
  const [isFollowing, setIsFollowing] = useState(0);
  const [isPostOpenModal, setPostOpenModal] = useState<boolean>(() => false);

  const onClickToggleModal = useCallback(() => {
    setOpenModal(!isOpenModal);
    console.log(isOpenModal);
  }, [isOpenModal]);

  const onClickPostToggleModal = useCallback(() => {
    setPostOpenModal(!isPostOpenModal);
  }, [isPostOpenModal]);

  const onClickFollower = () => {
    setIsFollowing(0);
    onClickToggleModal();
  };

  const onClickFollowing = () => {
    setIsFollowing(1);
    onClickToggleModal();
  };

  const onClickLike = () => {
    setIsFollowing(2);
    onClickToggleModal();
  };

  const onClickPost = () => {
    onClickPostToggleModal();
  };

  const navigate = useNavigate(); // react-router-dom useNavigate 사용 선언

  function toWrite() {
    // react-router-dom을 이용한 글쓰기 페이지로 이동 함수
    navigate('/write');
  }

  // GET요청 보내서 데이터 가져오고 받은 데이터 변수에 넣어주는 함수
  async function getMyData() {
    try {
      const res = await axios.get(
        'http://localhost:8080/api/v1/members/myPage',
        {
          headers: {
            Authorization: `Bearer ${accessToken || persistToken}`,
          },
        },
      );
      setNickname(res.data.data.nickname);
      setIntroduction(res.data.data.introduction);
      setFollowerCount(res.data.data.followerCount);
      setFollowingCount(res.data.data.followingCount);
      // setProjectData(res.data.data.getProjectInfoResponseList);
    } catch (error) {
      console.error(error);
    }
  }

  useEffect(() => {
    getMyData();
  }, [refresh]);

  return (
    <div className="relative h-auto min-h-screen w-screen bg-graphybg">
      <NavBar />
      <div className="w-screen px-8">
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

        <div className="flex flex-col lg:flex-row">
          <div className="min-w-64 mx-auto mt-28 flex h-[220px] w-auto items-center justify-center rounded-[25px] bg-white px-7 lg:mt-36 lg:mr-8 lg:h-[320px] lg:flex-col lg:px-12">
            {/* 프로필카드 아이콘 */}
            <img className="mx-3 h-24 " src={myProfile} alt="myProfile" />
            <div>
              <div className="flex items-center lg:flex-col">
                <div className="mr-3 text-center font-lato text-[23px] font-semibold text-graphyblue lg:mx-auto lg:mt-3">
                  {nickname}
                </div>

                <div className="flex flex-row text-center lg:mt-2">
                  <button
                    className="whitespace-nowrap font-lato text-[18px] font-semibold "
                    type="button"
                    onClick={onClickFollower}
                  >
                    팔로워 {followerCount}
                  </button>

                  <button
                    className="ml-6 mr-3 whitespace-nowrap font-lato text-[18px] font-semibold lg:mr-0"
                    type="button"
                    onClick={onClickFollowing}
                  >
                    팔로잉 {followingCount}
                  </button>

                  {isOpenModal ? (
                    <FollowModal
                      onClickToggleModal={onClickToggleModal}
                      isFollowing={isFollowing}
                    />
                  ) : null}
                </div>
              </div>

              <div className="mt-4 whitespace-nowrap text-center font-lato text-[15px] text-stone-500">
                {introduction}
              </div>
            </div>
          </div>

          {/* ----------------------------- */}
          <div className="mx-auto">
            <div className="mt-10 font-lato text-[24px] font-bold lg:mt-32">
              나의 작성 포스트
            </div>

            {/* 구분선 */}
            <div className="mt-3 border-b-2 border-b-neutral-200 " />

            {/* 내가 쓴 글 리스트 */}
            <div className="relative mt-8 ml-8 h-[230px] w-auto rounded-[25px] bg-white pt-6 drop-shadow-md">
              <button
                className="flex flex-col "
                type="button"
                onClick={onClickPost}
              >
                {/* 제목 */}
                <div className="ml-10 font-lato text-[20px] font-bold text-zinc-700">
                  {/* {projectData[0].projectName} */}
                  Graphy
                </div>
                {/* 본문 미리보기 */}
                <div className="mx-12 mt-3 h-[100px] max-w-[750px] overflow-hidden text-ellipsis font-lato text-[16px] font-normal text-zinc-700 ">
                  개발자를 준비하다 보면, 좋은 프로젝트에 대한 고민이 많아진다.
                  하지만 처음부터 좋은 프로젝트를 개발하는 것은 어렵기에 대부분
                  프로젝트 레퍼런스를 참고하거나, 주변에 평가를 받아 개선하기도
                  한다. Graphy는 이를 도와줄 수 있는 프로젝트 공유 플랫폼이다.
                  개발자로 취업 준비 중인 사용자를 타겟팅한 포트폴리오 기록
                  사이트로, 사람들이 보다 좋은 프로젝트를 개발할 수 있도록
                  도와주는 것을 목표로 하고 있다. 개발자를 준비하다 보면, 좋은
                  프로젝트에 대한 고민이 많아진다. 하지만 처음부터 좋은
                  프로젝트를 개발하는 것은 어렵기에 대부분 프로젝트 레퍼런스를
                  참고하거나, 주변에 평가를 받아 개선하기도 한다. Graphy는 이를
                  도와줄 수 있는 프로젝트 공유 플랫폼이다. 개발자로 취업 준비
                  중인 사용자를 타겟팅한 포트폴리오 기록 사이트로, 사람들이 보다
                  좋은 프로젝트를 개발할 수 있도록 도와주는 것을 목표로 하고
                  있다.
                </div>
                <div className="absolute left-11 bottom-16 m-auto h-12 w-full max-w-[750px] whitespace-nowrap bg-gradient-to-b from-transparent to-white" />
                {/* 좋아요 */}
                <button
                  className="ml-auto mr-8 mt-3 flex items-center"
                  type="button"
                  onClick={onClickLike}
                >
                  <img src={Like} alt="Like" />
                  14
                </button>
              </button>
            </div>
          </div>
          {isPostOpenModal ? (
            <PostModal onClickPostToggleModal={onClickPostToggleModal} />
          ) : null}
        </div>
      </div>
    </div>
  );
}

export default MyPage;
