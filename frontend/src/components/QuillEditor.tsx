import React, { useRef, useState, useMemo } from 'react';
import ReactQuill, { Quill } from 'react-quill';
import hljs from 'highlight.js';

import 'react-quill/dist/quill.snow.css';
import 'highlight.js/styles/monokai-sublime.css';

hljs.configure({
  languages: ['javascript', 'ruby', 'python', 'java', 'cpp', 'kotlin', 'sql'],
});

const QuillEditor = () => {
  const QuillRef = useRef<ReactQuill>();
  const [contents, setContents] = useState('');

  // 이미지를 업로드 하기 위한 함수
  const imageHandler = () => {
    // 파일을 업로드 하기 위한 input 태그 생성
    const input = document.createElement('input');
    input.setAttribute('type', 'file');
    input.setAttribute('accept', 'image/*');
    input.click();

    // 파일이 input 태그에 담기면 실행 될 함수
    input.onchange = () => {
      const file = input.files;
      if (file !== null) {
        const reader = new FileReader();

        // 파일 읽기가 완료되면 실행 될 함수
        reader.onload = () => {
          // 커서의 위치를 알고 해당 위치에 이미지 태그를 넣어주는 코드
          // 해당 DOM의 데이터가 필요하기에 useRef를 사용
          const range = QuillRef.current?.getEditor().getSelection()?.index;
          if (range !== null && range !== undefined) {
            let quill = QuillRef.current?.getEditor();

            quill?.setSelection(range, 1);

            quill?.clipboard.dangerouslyPasteHTML(
              range,
              `<img src=${reader.result} alt="이미지 태그가 삽입됩니다." />`,
            );
          }
        };

        reader.readAsDataURL(file[0]);
      }
    };
  };

  const modules = useMemo(
    () => ({
      syntax: {
        highlight: (text: string) => hljs.highlightAuto(text).value,
      },
      toolbar: {
        container: [
          [{ font: [] }],
          [{ align: [] }],
          ['bold', 'italic', 'underline', 'blockquote', 'code-block'],
          [{ size: ['small', false, 'large', 'huge'] }, { color: [] }],
          [
            { list: 'ordered' },
            { list: 'bullet' },
            { indent: '-1' },
            { indent: '+1' },
            { align: [] },
            'link',
          ],
          ['image', 'video'],
        ],
        handlers: {
          image: imageHandler,
        },
      },
    }),
    [],
  );

  return (
    <>
      <ReactQuill
        ref={(element) => {
          if (element !== null) {
            QuillRef.current = element;
          }
        }}
        className="h-20 font-ng sm:h-56 "
        value={contents}
        onChange={setContents}
        modules={modules}
        theme="snow"
      />
    </>
  );
};

export default QuillEditor;
