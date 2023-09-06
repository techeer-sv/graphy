import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useRecoilState, useRecoilValue } from 'recoil';

import { tokenApi } from '../../api/axios';
import NavBar from '../../components/general/NavBar';
import ImageUploader from '../../components/submit/ImageUploader';
import QuillEditor from '../../components/submit/QuillEditor';
import TechStackSelection from '../../components/submit/TechStackSelection';
import {
  contentsState,
  projectIdState,
  selectedStackState,
  thumbnailUrlState,
  titleState,
  tldrState,
} from '../../Recoil';

function ModifyingPage() {
  const [title, setTitle] = useRecoilState(titleState);
  const [tldr, setTldr] = useRecoilState(tldrState);
  const contents = useRecoilValue(contentsState);
  const selectedStack = useRecoilValue(selectedStackState);
  const [thumbnailUrl, setThumbnailUrl] = useRecoilState(thumbnailUrlState);

  const accessToken = sessionStorage.getItem('accessToken');
  const persistToken = localStorage.getItem('persistToken');

  const projectId = useRecoilValue(projectIdState);
  const navigate = useNavigate();

  const handleTitleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const inputValue = e.target.value;
    if (inputValue.length > 255) {
      setTitle(inputValue.substring(0, 255));
      alert('대댓글은 255자까지 입력하실 수 있습니다.');
      return;
    }
    setTitle(inputValue);
  };

  const handleTldrChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const inputValue = e.target.value;
    if (inputValue.length > 255) {
      setTldr(inputValue.substring(0, 255));
      alert('대댓글은 255자까지 입력하실 수 있습니다.');
      return;
    }
    setTldr(inputValue);
  };

  async function putData() {
    const data = {
      projectName: title,
      content: contents,
      description: tldr,
      techTags: selectedStack,
      thumbNail: thumbnailUrl,
    };
    try {
      const response = await tokenApi.put(`/projects/${projectId}`, data, {
        headers: {
          Authorization: `Bearer ${accessToken || persistToken}`,
        },
      });
      console.log(response.data);
      navigate(`/read/${projectId}`);
      setThumbnailUrl('');
    } catch (error) {
      if (!navigator.onLine) {
        alert('오프라인 상태입니다. 네트워크 연결을 확인해주세요.');
      } else if (title.trim().length === 0) {
        alert('제목을 입력해주세요.');
      } else if (tldr.trim().length === 0) {
        alert('한줄 소개를 입력해주세요.');
      } else if (contents.trim().length === 0) {
        alert('내용을 입력해주세요.');
      } else {
        console.log(error);
        alert('네트워크 오류');
      }
    }
  }

  function cancelButton() {
    navigate(`/read/${projectId}`);
  }

  useEffect(() => {
    if (!navigator.onLine) {
      alert('오프라인 상태입니다. 네트워크 연결을 확인해주세요.');
      navigate(`/read/${projectId}`);
    }
    if (!(accessToken || persistToken)) {
      alert('로그인시 이용하실 수 있습니다.');
      navigate('/');
    }
  }, []);

  return (
    <div className="mt-0 flex h-auto w-screen justify-center bg-[#F9F8F8] pb-10">
      <NavBar />
      {/* 젤 큰 박스 */}
      <div className="mt-16 w-11/12 max-w-1100 border border-black px-2 sm:flex sm:h-5/6 sm:flex-col">
        {/* 서식 구역 */}
        <div className="mt-2 flex h-228 flex-col justify-center sm:flex-row">
          {/* 텍스트 구역 */}
          <div className="mr-2 mt-64 mb-2 w-full overflow-visible sm:mt-0 sm:w-10/12">
            {/* 제목 상자 */}
            <input
              className="focus:shadow-outline m-0 mb-2.5 h-49 w-full appearance-none rounded border bg-[#F9F8F8] px-3 font-ng leading-tight text-gray-700 shadow focus:outline-none"
              id="title"
              type="text"
              placeholder="제목을 입력해주세요."
              value={title}
              onChange={handleTitleChange}
            />
            {/* 한줄 소개 상자 */}
            <input
              className="focus:shadow-outline m-0 mb-2.5 h-49 w-full appearance-none rounded border bg-[#F9F8F8] px-3 font-ng leading-tight text-gray-700 shadow focus:outline-none sm:h-49"
              id="tldr"
              type="text"
              placeholder="한 줄 소개를 입력해주세요."
              value={tldr}
              onChange={handleTldrChange}
            />
            {/* 사용 기술 상자 */}
            <div className="relative z-10 h-110 w-full bg-[#F9F8F8] font-ng">
              <TechStackSelection />
            </div>
          </div>
          {/* 사진 드롭박스 */}
          <ImageUploader />
        </div>
        {/* 글쓰기 구역 */}
        <div className="relative z-0 mt-60 sm:mt-2">
          <QuillEditor />
        </div>
        {/* 버튼 구역 */}
        <div className="mt-20 mb-4 flex justify-end sm:mt-20 lg:mt-12">
          <button
            className="focus:shadow-outline mr-2 h-12 w-24 appearance-none rounded-sm border bg-gray-500 font-ng text-white hover:bg-gray-700"
            onClick={() => cancelButton()}
            type="button"
          >
            취소
          </button>
          <button
            className="focus:shadow-outline h-12 w-24 appearance-none rounded-sm bg-graphyblue font-ng text-white hover:bg-blue-800"
            onClick={() => putData()}
            type="submit"
          >
            수정
          </button>
        </div>
      </div>
    </div>
  );
}

export default ModifyingPage;
