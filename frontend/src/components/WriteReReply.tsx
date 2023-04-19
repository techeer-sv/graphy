import { useEffect, useRef, useState } from 'react';
import reply_icon from '../assets/image/reply_icon.svg';
import { useRecoilState } from 'recoil';
import { projectIdState, refreshState } from '../Recoil';
import axios from 'axios';

function WriteReReply(props: any) {
  const textAreaRef = useRef<HTMLTextAreaElement>(null);
  const [value, setValue] = useState('');
  const [projectId, setProjectId] = useRecoilState(projectIdState);
  const [refresh, setrefresh] = useRecoilState(refreshState);

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

  useEffect(() => {
    console.log(props);
  }, []);

  async function postData() {
    const url = 'http://localhost:8080/api/v1/comments';
    const data = {
      content: value,
      projectId: projectId,
      parentId: props.contents.commentId,
    };

    try {
      const res = await axios.post(url, data);
      console.log(res.data);
      setrefresh(!refresh);
      setValue('');
    } catch (error) {
      console.error(error);
      if (value.length === 0) {
        alert('대댓글을 입력해주세요.');
      } else {
        alert('네트워크 오류');
      }
    }
  }

  return (
    <div>
      {/*대댓글 입력창*/}
      <div className="relative">
        <img src={reply_icon} className="absolute ml-2 mt-1 h-5" />
        <div className=" mt-3 ml-8 flex h-auto flex-col rounded-xl border-2 border-gray-400">
          <textarea
            className="min-h-24 h-auto w-full resize-none appearance-none rounded-xl bg-graphybg py-2 px-3 font-ng leading-tight text-gray-700 focus:outline-none"
            id="reply"
            placeholder="대댓글을 입력하세요."
            ref={textAreaRef}
            value={value}
            onChange={handleChange}
          />
          <button
            className="focus:shadow-outline m-auto my-2 mr-2 h-8 w-16 appearance-none place-items-end rounded-lg border-2 border-gray-400 bg-graphybg font-ng hover:bg-gray-200"
            onClick={() => postData()}
          >
            등록
          </button>
        </div>
      </div>
    </div>
  );
}

export default WriteReReply;
