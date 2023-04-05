import React, { useEffect } from 'react';
import { useRecoilState } from 'recoil';
import QuillWrtten from '../components/QuillWritten';
import {
  contentsState,
  selectedStackState,
  titleState,
  tldrState,
  projectIdState,
} from '../Recoil';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import NavBar from '../components/NavBar';
import AllStacks from '../Stack';

function ReadingPage() {
  const [title, setTitle] = useRecoilState(titleState);
  const [tldr, setTldr] = useRecoilState(tldrState);
  const [selectedStack, setSelectedStack] = useRecoilState(selectedStackState);
  const [contents, setContents] = useRecoilState(contentsState);
  const [projectId, setProjectId] = useRecoilState(projectIdState);
  const navigate = useNavigate();

  function toWrite() {
    // react-router-dom을 이용한 글 쓰기 페이지로 이동 함수
    navigate('/write');
  }

  function toModify() {
    // react-router-dom을 이용한 글 수정 페이지로 이동 함수
    navigate('/modify');
  }
  //GET요청 보내서 데이터 가져오고 받은 데이터 변수에 넣어주는 함수
  const getData = async () => {
    try {
      const res = await axios.get(
        `http://localhost:8080/api/v1/projects/${projectId}`,
      );
      console.log(res.data);
      setTitle(res.data.data.projectName);
      setTldr(res.data.data.description);
      setSelectedStack(res.data.data.techTags);
      setContents(res.data.data.content);
    } catch (error) {
      console.error(error);
    }
  };
  //DELETE 요청 보내는 함수
  const deleteData = async () => {
    try {
      const res = await axios.delete(
        `http://localhost:8080/api/v1/projects/${projectId}`,
      );
      console.log(res.data);
      navigate('/');
    } catch (error) {
      console.error(error);
    }
  };
  //제목 변경시 재 렌더링
  useEffect(() => {
    if (title) {
      setTitle(title);
    }
  }, [title]);
  //소개 변경시 재 렌더링
  useEffect(() => {
    if (tldr) {
      setTldr(tldr);
    }
  }, [tldr]);
  //선택 스택 변경시 재 렌더링
  useEffect(() => {
    if (selectedStack.length !== 0) {
      setSelectedStack(selectedStack);
    }
  }, [selectedStack]);
  //데이터 가져올때 재 렌더링
  useEffect(() => {
    getData();
  }, []);

  //이미지 찾는 함수
  function findimg(s: string) {
    return AllStacks[AllStacks.map((x) => x.name).findIndex((x) => x == s)]
      .image;
  }

  return (
    <div className="mt-0 flex h-auto w-screen justify-center overflow-y-auto overflow-x-hidden bg-[#F9F8F8] pb-10">
      <NavBar />
      {/**전체 컨텐츠 영역**/}
      <div className="mt-16 w-11/12 max-w-1100 px-2 sm:flex sm:h-5/6 sm:flex-col">
        {/**텍스트 영역**/}
        <div className="h-auto border-b border-black">
          {/**제목**/}
          <div className=" mt-10 mb-4 text-center font-ng-eb text-4xl">
            {title}
          </div>
          <div className="mb-2 flex flex-row overflow-hidden hover:overflow-x-auto">
            <div className=" mb-2 mr-3 shrink-0 font-ng-b text-2xl text-zinc-500 ">
              한줄 소개
            </div>
            {/**한줄소개**/}
            <div className="mb-2 font-ng-b text-2xl">{tldr}</div>
          </div>
          {/**사용기술**/}
          {selectedStack.length !== 0 ? (
            <div className="flex flex-row items-center overflow-hidden hover:overflow-x-auto">
              <div className=" mb-2 mr-3 shrink-0 font-ng-b text-2xl text-zinc-500">
                기술 스택
              </div>
              <>
                {selectedStack.map((x: string) => (
                  <div
                    key={x}
                    className="mr-2 mb-2 flex h-auto shrink-0 flex-row items-center rounded-full border py-1 pr-3"
                  >
                    <img className="mx-3 my-1 h-8 w-8" src={findimg(x)} />
                    <p className="shrink-0 font-ng-b">{x}</p>
                  </div>
                ))}
              </>
            </div>
          ) : (
            <div className="flex flex-col sm:flex-row">
              <div className=" mb-2 mr-2 font-ng-b text-xl text-zinc-500">
                기술 스택
              </div>
              <div className="mb-2  font-ng-b text-xl">사용 기술</div>
            </div>
          )}
        </div>
        {/**글 영역**/}
        <div>
          <QuillWrtten />
        </div>
        {/**버튼 영역**/}
        <div>
          <div className="mt-20 mb-4 flex justify-end sm:mt-20 lg:mt-12">
            <button
              className="focus:shadow-outline mr-2 h-12 w-24 appearance-none rounded-sm border bg-gray-500 font-ng text-white hover:bg-gray-700"
              onClick={() => toModify()}
            >
              수정
            </button>
            <button
              className="focus:shadow-outline mr-2 h-12 w-24 appearance-none rounded-sm border bg-gray-500 font-ng text-white hover:bg-gray-700"
              onClick={() => deleteData()}
            >
              삭제
            </button>
            <button
              className="focus:shadow-outline h-12 w-24 appearance-none rounded-sm bg-blue-500 font-ng text-white hover:bg-blue-700"
              onClick={() => toWrite()}
            >
              글작성
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default ReadingPage;
