import React, { useEffect } from 'react'
import { Viewer } from '@toast-ui/react-editor'
import '@toast-ui/editor/dist/toastui-editor-viewer.css'
import 'prismjs/themes/prism.css'
import Prism from 'prismjs'
import codeSyntaxHighlight from '@toast-ui/editor-plugin-code-syntax-highlight'

import { useRecoilState } from 'recoil'
import { contentsState } from '../../../utils/atoms'

export default function ToastViewer() {
  const [contents, setContents] = useRecoilState(contentsState)

  useEffect(() => {
    if (contents) {
      setContents(contents)
    }
  }, [contents])

  return (
    <div className="min-h-[300px] pointer-events-none mt-4 px-4 h-auto border text-2xl">
      {contents ? (
        <Viewer
          initialValue={contents}
          plugins={[[codeSyntaxHighlight, { highlighter: Prism }]]}
        />
      ) : (
        <Viewer
          initialValue="게시글을 불러오는 중입니다..."
          plugins={[[codeSyntaxHighlight, { highlighter: Prism }]]}
        />
      )}
    </div>
  )
}
