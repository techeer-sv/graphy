import React, { useRef, useMemo } from 'react';
import ReactQuill from 'react-quill';
import hljs from 'highlight.js';
import ReactS3Client from 'react-aws-s3-typescript';

import 'react-quill/dist/quill.snow.css';
import 'highlight.js/styles/monokai-sublime.css';
import { contentsState } from '../Recoil';
import { useRecoilState } from 'recoil';
import s3config from '../s3config';

hljs.configure({
  languages: ['javascript', 'ruby', 'python', 'java', 'cpp', 'kotlin', 'sql'],
});

const s3 = new ReactS3Client(s3config);

function QuillEditor() {
  const QuillRef = useRef<ReactQuill>();
  const [contents, setContents] = useRecoilState(contentsState);

  // 이미지를 업로드 하기 위한 함수
  const imageHandler = () => {
    // 파일을 업로드 하기 위한 input 태그 생성
    const input = document.createElement('input');
    const formData = new FormData();

    input.setAttribute('type', 'file');
    input.setAttribute('accept', 'image/*');
    input.click();

    // 파일이 input 태그에 담기면 실행 될 함수
    input.onchange = async () => {
      const file = input.files;
      if (file !== null) {
        formData.append('image', file[0]);

        try {
          const res = await s3.uploadFile(file[0]);
          console.log(res);
          const range = QuillRef.current?.getEditor().getSelection()?.index;
          if (range !== null && range !== undefined) {
            let quill = QuillRef.current?.getEditor();

            quill?.setSelection(range, 1);

            quill?.clipboard.dangerouslyPasteHTML(
              range,
              `<img src=${res.location} alt="이미지" />`,
            );
          }
        } catch (error) {
          console.log(error);
        }
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
        className=" h-96 font-ng"
        value={contents}
        onChange={setContents}
        modules={modules}
        theme="snow"
      />
    </>
  );
}

export default QuillEditor;
