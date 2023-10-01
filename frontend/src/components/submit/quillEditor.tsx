'use client'

import hljs from 'highlight.js'
import { useRef, useMemo } from 'react'
import ReactS3Client from 'react-aws-s3-typescript'
import 'react-quill/dist/quill.snow.css'
import 'highlight.js/styles/monokai-sublime.css'
import { useRecoilState } from 'recoil'
import dynamic from 'next/dynamic'

import { contentsState } from '../../utils/atoms'
import s3config from '../../utils/s3config'

const ReactQuill = dynamic(
  async () => {
    const { default: RQ } = await import('react-quill')
    return function comp({ forwardedRef, ...props }) {
      return <RQ ref={forwardedRef} />
    }
  },
  { ssr: false },
)

// 코드 하이라이트 설정
hljs.configure({
  languages: ['javascript', 'ruby', 'python', 'java', 'cpp', 'kotlin', 'sql'],
})

const s3 = new ReactS3Client(s3config)

function QuillEditor() {
  const quillRef = useRef()
  const [contents, setContents] = useRecoilState(contentsState)

  const imageHandler = () => {
    // 파일을 업로드 하기 위한 input 태그 생성
    const input = document.createElement('input')
    const formData = new FormData()

    input.setAttribute('type', 'file')
    input.setAttribute('accept', 'image/*')
    input.click()

    // 파일이 input 태그에 담기면 실행 될 함수
    input.onchange = async () => {
      const file = input.files
      if (file !== null) {
        formData.append('image', file[0])

        try {
          const res = await s3.uploadFile(file[0])
          const range = quillRef.current?.getEditor().getSelection()?.index
          if (range !== null && range !== undefined) {
            const quill = quillRef.current?.getEditor()

            quill?.setSelection(range, 1)

            quill?.clipboard.dangerouslyPasteHTML(
              range,
              `<img src=${res.location} alt="image" />`,
            )
          }
        } catch (error) {
          if (!navigator.onLine) {
            throw new Error(
              '오프라인 상태입니다. 네트워크 연결을 확인해주세요.',
            )
          } else {
            // eslint-disable-next-line no-console
            console.log(error)
            throw new Error('이미지 업로드 실패')
          }
        }
      }
    }
  }

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
  )

  return (
    <ReactQuill
      forwardedRef={quillRef}
      className="font-ng"
      value={contents}
      onChange={setContents}
      modules={modules}
      theme="snow"
    />
  )
}

export default QuillEditor
