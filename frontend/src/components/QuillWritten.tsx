import { useEffect } from 'react';
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.bubble.css';
import { useRecoilState } from 'recoil';
import { readContentsState } from '../Recoil';

function QuillWrtten() {
  const [readContents, setReadContents] = useRecoilState(readContentsState);

  useEffect(() => {
    if (readContents) {
      setReadContents(readContents);
    }
  }, [readContents]);

  return (
    <div className=" pointer-events-none mt-4 h-auto min-h-96 border text-2xl">
      {readContents ? (
        <ReactQuill value={readContents} readOnly={true} theme="bubble" />
      ) : (
        <ReactQuill
          value="게시글을 불러오는 중입니다..."
          readOnly={true}
          theme="bubble"
        />
      )}
    </div>
  );
}

export default QuillWrtten;
