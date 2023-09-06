import { act } from '@testing-library/react';
import { isAxiosError } from 'axios';
import { useEffect, useState, useCallback } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { useRecoilState, useRecoilValue } from 'recoil';

import { generalApi, tokenApi } from '../../api/axios';
import gptIcon from '../../assets/image/gptIcon.svg';
import ProfileIcon from '../../assets/image/profileIcon.svg';
import NavBar from '../../components/general/NavBar';
import GptModal from '../../components/read/ReadingPage/GptModal';
import QuillWrtten from '../../components/read/ReadingPage/QuillWritten';
import Reply from '../../components/read/ReadingPage/Reply';
import {
  contentsState,
  selectedStackState,
  titleState,
  tldrState,
  refreshState,
} from '../../Recoil';
import AllStacks from '../../Stack';

type ReadReplyObject = {
  commentId: number;
  childCount: number;
  content: string;
  createdAt: string;
};

function ReadingPage() {
  const [title, setTitle] = useRecoilState(titleState);
  const [tldr, setTldr] = useRecoilState(tldrState);
  const [selectedStack, setSelectedStack] = useRecoilState(selectedStackState);
  const [, setContents] = useRecoilState(contentsState);
  const [readReply, setReadReply] = useState<ReadReplyObject[]>([]);
  const refresh = useRecoilValue(refreshState);
  const accessToken = sessionStorage.getItem('accessToken');
  const persistToken = localStorage.getItem('persistToken');
  const navigate = useNavigate();
  const params = useParams();

  const [isOpenModal, setOpenModal] = useState<boolean>(false);
  const [nickname, setNickname] = useState<string>('');
  const [createdAt, setCreatedAt] = useState<string>('');

  const onClickToggleModal = useCallback(() => {
    setOpenModal(!isOpenModal);
  }, [isOpenModal]);

  function toWrite() {
    navigate('/write');
  }

  function toModify() {
    navigate('/modify');
  }
  // GET요청 보내서 데이터 가져오고 받은 데이터 변수에 넣어주는 함수
  async function getData() {
    try {
      const res = await generalApi.get(`/projects/${params.id}`);
      setTitle(res.data.data.projectName);
      setTldr(res.data.data.description);
      setSelectedStack(res.data.data.techTags);
      setContents(res.data.data.content);
      setReadReply(res.data.data.commentsList);
      setNickname(res.data.data.member.nickname);
      setCreatedAt(res.data.data.createdAt);
    } catch (error) {
      if (isAxiosError(error)) {
        if (
          error.response?.data.message ===
          '이미 삭제되거나 존재하지 않는 프로젝트'
        ) {
          alert('이미 삭제되거나 존재하지 않는 프로젝트입니다.');
        } else {
          alert('프로젝트 상세 조회 실패');
          console.error(error);
        }
        navigate('/');
      }
    }
  }
  // DELETE 요청 보내는 함수
  async function deleteData() {
    if (!navigator.onLine) {
      alert('오프라인 상태입니다. 네트워크 연결을 확인해주세요.');
    } else {
      try {
        await tokenApi.delete(`/projects/${params.id}`, {
          headers: {
            Authorization: `Bearer ${accessToken || persistToken}`,
          },
        });
        act(() => {
          navigate('/');
        });
      } catch (error) {
        if (isAxiosError(error)) {
          if (
            error.response?.data.message ===
            '이미 삭제되거나 존재하지 않는 프로젝트'
          ) {
            alert('이미 삭제되거나 존재하지 않는 프로젝트입니다.');
          } else {
            alert('네트워크 오류');
          }
        }
      }
    }
  }
  // 렌더링할때 데이터 가져옴
  useEffect(() => {
    getData();
  }, [refresh]);
  // 제목 변경시 재 렌더링
  useEffect(() => {
    if (title) {
      setTitle(title);
    }
  }, [title]);
  // 소개 변경시 재 렌더링
  useEffect(() => {
    if (tldr) {
      setTldr(tldr);
    }
  }, [tldr]);
  // 선택 스택 변경시 재 렌더링
  useEffect(() => {
    if (selectedStack.length !== 0) {
      setSelectedStack(selectedStack);
    }
  }, [selectedStack]);

  // 이미지 찾는 함수
  function findImage(tag: string) {
    return AllStacks.map((x) => x.image)[
      AllStacks.map((x) => x.name).findIndex((x) => x === tag)
    ];
  }

  return (
    <div className="mt-0 flex h-auto w-screen justify-center bg-graphybg pb-10">
      <NavBar />
      {/** 전체 컨텐츠 영역* */}
      <div className="mt-16 w-11/12 max-w-1100 px-2 sm:flex sm:h-5/6 sm:flex-col">
        {/* AI 고도화 버튼 */}
        {accessToken || persistToken ? (
          <button
            className="fixed bottom-10 right-10 z-10 my-auto mb-2 flex shrink-0 flex-row items-center rounded-full  bg-graphyblue
          px-4 py-1 pt-3 pb-3 font-semibold text-slate-50 drop-shadow-md hover:bg-button"
            onClick={onClickToggleModal}
            type="button"
          >
            <img className="mr-2 h-5 w-5" src={gptIcon} alt="gptIcon" />
            <span className="shrink-0 font-semibold">AI 고도화 추천</span>
          </button>
        ) : null}

        {isOpenModal ? (
          <GptModal onClickToggleModal={onClickToggleModal} />
        ) : null}
        {/** 텍스트 영역* */}
        <div className="h-auto border-b-2 border-graphyblue pb-2">
          {/** 제목* */}
          <div className="mt-10 mb-4 text-center font-ng-eb text-4xl">
            {title}
          </div>
          <div className="mb-4 flex items-center border-b-2 border-graphyblue pb-2">
            <img className="h-6 w-6" src={ProfileIcon} alt="ProfileIcon" />
            <span className="ml-1 font-ng-b text-lg">{nickname}</span>
            <span className="text-ml font-ng text-lg text-gray-500">
              &nbsp; |&nbsp; {createdAt.slice(0, 10)}
            </span>
          </div>
          <div className="mb-2 flex flex-row overflow-hidden hover:overflow-x-auto">
            <div className=" mb-2 mr-3 shrink-0 font-ng-b text-xl text-zinc-500 sm:text-2xl ">
              한줄 소개
            </div>
            {/** 한줄소개* */}
            <div className="mb-2 font-ng-b text-xl sm:text-2xl">{tldr}</div>
          </div>
          {/** 사용기술* */}
          {selectedStack.length !== 0 ? (
            <div className="flex flex-row items-center overflow-hidden hover:overflow-x-auto">
              <div className="mb-2 mr-3 shrink-0 font-ng-b text-xl text-zinc-500 sm:text-2xl">
                기술 스택
              </div>
              {selectedStack.map((x: string) => (
                <div
                  key={x}
                  className="mr-2 mb-2 flex h-auto shrink-0 flex-row items-center rounded-full border py-1 pr-3"
                >
                  <img
                    className="mx-3 my-1 h-8 w-8"
                    src={findImage(x)}
                    alt="Stack"
                  />
                  <p className="shrink-0 font-ng-b">{x}</p>
                </div>
              ))}
            </div>
          ) : (
            <div className="flex flex-row">
              <div className="mb-2 mr-2 font-ng-b text-xl text-zinc-500 sm:text-2xl">
                기술 스택
              </div>
              <div className="mb-2 font-ng-b text-xl sm:text-2xl">없음</div>
            </div>
          )}
        </div>
        {/** 글 영역* */}
        <QuillWrtten />
        {/** 버튼 영역* */}
        {accessToken || persistToken ? (
          <div className="mt-20 flex justify-end pb-4 sm:mt-20 lg:mt-12">
            <button
              className="focus:shadow-outline mr-2 h-12 w-24 appearance-none rounded-sm border bg-gray-500 font-ng text-white hover:bg-gray-700"
              onClick={() => toModify()}
              type="submit"
            >
              수정
            </button>
            <button
              className="focus:shadow-outline mr-2 h-12 w-24 appearance-none rounded-sm border bg-gray-500 font-ng text-white hover:bg-gray-700"
              onClick={() => deleteData()}
              type="button"
            >
              삭제
            </button>
            <button
              className="focus:shadow-outline h-12 w-24 appearance-none rounded-sm bg-graphyblue font-ng text-white hover:bg-blue-800"
              onClick={() => toWrite()}
              type="submit"
            >
              글작성
            </button>
          </div>
        ) : null}
        <div className="mt-4">
          <Reply contents={readReply} setReadReply={setReadReply} />
        </div>
      </div>
    </div>
  );
}

export default ReadingPage;
