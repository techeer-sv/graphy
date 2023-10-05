'use client'

import { useEffect } from 'react'
import ReactQuill from 'react-quill'
import 'react-quill/dist/quill.bubble.css'
import { useRecoilState } from 'recoil'

import { contentsState } from '../../utils/atoms'

function QuillWrtten() {
  const [contents, setContents] = useRecoilState(contentsState)

  useEffect(() => {
    if (contents) {
      setContents(contents)
    }
  }, [contents])

  return (
    <div className="pointer-events-none mt-4 h-auto border text-2xl">
      {contents ? (
        <ReactQuill value={contents} readOnly theme="bubble" />
      ) : (
        <ReactQuill
          value="게시글을 불러오는 중입니다..."
          readOnly
          theme="bubble"
        />
      )}
    </div>
  )
}

export default QuillWrtten
