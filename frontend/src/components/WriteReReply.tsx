import { useRef } from 'react';
import reply_icon from '../assets/image/reply_icon.svg';
import { useRecoilState } from 'recoil';
import { reReplyState } from '../Recoil';

function WriteReReply() {
  const textAreaRef = useRef<HTMLTextAreaElement>(null);
  const [value, setValue] = useRecoilState(reReplyState);

  const handleChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
    const inputValue = event.target.value;
    if (textAreaRef.current) {
      textAreaRef.current.style.height = 'auto';
      textAreaRef.current.style.height = `${textAreaRef.current.scrollHeight}px`;
    }
    if (inputValue.length > 256) {
      setValue(inputValue.substring(0, 256));
      return;
    }
    setValue(event.target.value);
  };

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
            onClick={() => console.log('등록 버튼 클릭')}
          >
            등록
          </button>
        </div>
      </div>
    </div>
  );
}

export default WriteReReply;
