import { act } from '@testing-library/react';
import { useEffect, useRef, useState } from 'react';
import { useRecoilState, useRecoilValue } from 'recoil';

import { tokenApi } from '../../../../../api/axios';
import reply_icon from '../../../../../assets/image/reply_icon.svg';
import { projectIdState, refreshState } from '../../../../../Recoil';

interface WriteReReplyProps {
  contents: {
    commentId: number;
    childCount: number;
    content: string;
    createdAt: string;
  };
  changeWriteVis: () => void;
  setSelectedValue: React.Dispatch<React.SetStateAction<string>>;
}

function WriteReReply({
  contents: { commentId },
  changeWriteVis,
  setSelectedValue,
}: WriteReReplyProps) {
  const textAreaRef = useRef<HTMLTextAreaElement>(null);
  const [value, setValue] = useState('');
  const projectId = useRecoilValue(projectIdState);
  const [refresh, setrefresh] = useRecoilState(refreshState);

  const accessToken = sessionStorage.getItem('accessToken');
  const persistToken = localStorage.getItem('accessToken');

  const parentId = commentId;

  const handleChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    const inputValue = e.target.value;
    if (textAreaRef.current) {
      textAreaRef.current.style.height = 'auto';
      textAreaRef.current.style.height = `${textAreaRef.current.scrollHeight}px`;
    }
    if (inputValue.length > 255) {
      setValue(inputValue.substring(0, 255));
      alert('대댓글은 255자까지 입력하실 수 있습니다.');
      return;
    }
    setValue(inputValue);
  };

  async function postData() {
    const data = {
      content: value,
      projectId,
      parentId,
    };

    try {
      await tokenApi.post('/comments', data, {
        headers: {
          Authorization: `Bearer ${accessToken || persistToken}`,
        },
      });
      act(() => {
        setrefresh(!refresh);
        setValue('');
      });
    } catch (error) {
      if (!navigator.onLine) {
        alert('오프라인 상태입니다. 네트워크 연결을 확인해주세요.');
      } else if (value.trim().length === 0) {
        alert('답글을 입력해주세요.');
      } else {
        console.log(error);
        alert('네트워크 오류');
      }
    }
  }

  function post() {
    postData();
    changeWriteVis();
    setSelectedValue('regist_order');
  }

  useEffect(() => {
    if (textAreaRef.current) {
      textAreaRef.current.scrollIntoView({
        behavior: 'smooth',
        block: 'center',
      });
    }
  }, []);

  return (
    <div>
      {/* 대댓글 입력창 */}
      <div className="relative">
        <img
          src={reply_icon}
          className="absolute ml-2 mt-1 h-5"
          alt="reply icon"
        />
        <div className="mt-3 ml-8 flex h-auto flex-col rounded-xl border-2 border-gray-400">
          <textarea
            className="min-h-24 h-auto w-full resize-none appearance-none rounded-xl bg-graphybg py-2 px-3 font-ng leading-tight text-gray-700 focus:outline-none"
            id="reply"
            placeholder="답글을 입력하세요."
            ref={textAreaRef}
            value={value}
            onChange={handleChange}
          />
          <button
            className="focus:shadow-outline m-auto my-2 mr-2 h-8 w-16 appearance-none place-items-end rounded-lg border-2 border-gray-400 bg-graphybg font-ng hover:bg-gray-200"
            onClick={() => post()}
            type="submit"
          >
            등록
          </button>
        </div>
      </div>
    </div>
  );
}

export default WriteReReply;
