import { useEffect, useRef, useState } from 'react';
import { useRecoilState, useRecoilValue } from 'recoil';
import { projectIdState, refreshState } from '../Recoil';
import axios from 'axios';

function PutReply(props: any) {
  const textAreaRef = useRef<HTMLTextAreaElement>(null);
  const [value, setValue] = useState<string>(props.contents.content);
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
    if (textAreaRef.current) {
      textAreaRef.current.scrollIntoView({
        behavior: 'smooth',
        block: 'center',
      });
    }
  }, []);

  async function putData() {
    const url = `http://localhost:8080/api/v1/comments/${props.contents.commentId}`;
    const data = {
      content: value,
    };

    try {
      const res = await axios.put(url, data);
      console.log(res.data);
      setrefresh(!refresh);
      props.hasOwnProperty('childCount') ? null : props.changeCommentRef();
      setValue('');
    } catch (error) {
      console.error(error);
      if (value.length === 0) {
        alert('수정할 댓글을 입력해주세요.');
      } else {
        alert('네트워크 오류');
      }
    }
  }

  function put() {
    putData();
    props.changePutVis();
    props.setSelectedValue('regist_order');
  }

  return (
    <div>
      {/*대댓글 입력창*/}
      <div className="relative">
        <div className="mt-3 flex h-auto flex-col rounded-xl border-2 border-gray-400">
          <textarea
            className="min-h-24 h-auto w-full resize-none appearance-none rounded-xl bg-graphybg py-2 px-3 font-ng leading-tight text-gray-700 focus:outline-none"
            id="reply"
            placeholder="수정할 댓글을 입력하세요."
            ref={textAreaRef}
            value={value}
            onChange={handleChange}
          />
          <button
            className="focus:shadow-outline m-auto my-2 mr-2 h-8 w-16 appearance-none place-items-end rounded-lg border-2 border-gray-400 bg-graphybg font-ng hover:bg-gray-200"
            onClick={() => put()}
          >
            수정
          </button>
        </div>
      </div>
    </div>
  );
}

export default PutReply;
